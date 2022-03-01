package com.imooc.user.controller;

import com.imooc.api.controller.BaseController;
import com.imooc.api.controller.user.PassportControllerApi;
import com.imooc.bo.RegisterLoginBO;
import com.imooc.enums.ResponseStatus;
import com.imooc.enums.UserStatus;
import com.imooc.pojo.AppUser;
import com.imooc.result.JsonResult;
import com.imooc.user.service.UserService;
import com.imooc.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Objects;

/**
 * @Author Dooby Kim
 * @Date 2021/12/22 3:26 下午
 * @Version 1.0
 */
@RestController
@Slf4j
public class PassportController extends BaseController implements PassportControllerApi {

    @Resource
    private SMSUtils smsUtils;

    @Resource
    private RedisOperator redisOperator;

    @Resource
    private UserService userService;

    @Value("${website.domain-name}")
    public String domainName;

    @Override
    public JsonResult getSMSCode(String mobile, HttpServletRequest request) {
        // HttpServletRequest 可以获取到用户的 ip；根据用户的 ip 地址进行限制，限制用户在 60 秒内只能获取一次验证码
        String userIp = IPUtils.getIPAddress(request);
        // 通过 Redis 存储用户的 ip；限制用户在 60 秒内只能获取一次验证码
        redisOperator.setnx(RedisKeyUtils.userIpKey(userIp), userIp, 60);
        // 随机生成六位数字的验证码
        String code = smsUtils.generateSMSCode();
        // 通过 Redis 存储用户的验证码，存储时长为 30 min
        redisOperator.set(RedisKeyUtils.userCodeKey(code), code, 30 * 60);

        Map<String, String> result = smsUtils.sendSMS("15526787357", code);
        if (result != null
                && result.get("code").equals("Ok")
                && result.get("msg").equals("send success")) {
            return JsonResult.ok();
        } else {
            return JsonResult.error();
        }
    }

    /**
     * 一键登录与注册
     * 流程：
     * 1. 首先进行 @Valid 校验；判断 BindingResult 中是否绑定了错误的验证信息，即：@NotBlank(message = "手机号不能为空") 与 @NotNull(message = "短信验证码不能为空")，如果有则返回
     * 2. 校验用户输入的验证码与存储在 Redis 中的验证码是否匹配，如果不匹配则返回
     * 3. 查询数据库，判断该用户是否注册
     * ---- 1. 如果查询到用户已注册，且状态为冻结，则返回提示用户
     * ---- 2. 如果在数据库中未查询到用户的信息，则进行用户注册
     * 4. 生成用户的 token 信息，存储到 Redis 分布式缓存中
     * 5. 将用户的 id 与 token 保存到 Cookie 中，设置有效期为 1 个月
     * 6. 返回用户的状态信息给前端
     *
     * @param registerLoginBO
     * @param result
     * @param request
     * @param response
     * @return
     */
    @Override
    public JsonResult doLogin(@Valid RegisterLoginBO registerLoginBO, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        // 判断 BindingResult 中是否绑定了错误的验证信息(@Valid 校验)，如果有则返回
        if (result.hasErrors()) {
            Map<String, String> errors = getErrors(result);
            return new JsonResult(ResponseStatus.FAILED, errors);
        }
        // 校验验证码是否匹配
        String smsCode = registerLoginBO.getSmsCode();
        String redis_smsCode = redisOperator.get(RedisKeyUtils.userCodeKey(smsCode));
        if (StringUtils.isEmpty(redis_smsCode) || !StringUtils.equals(smsCode, redis_smsCode)) {
            return new JsonResult(ResponseStatus.SMS_CODE_ERROR);
        }
        // 查询数据库，该用户是否注册
        String mobile = registerLoginBO.getMobile();
        AppUser appUser = userService.queryMobileExist(mobile);
        // 查询到用户，并且用户的状态为冻结
        if (!Objects.isNull(appUser) && appUser.getActiveStatus().equals(UserStatus.FROZEN.getType())) {
            return new JsonResult(ResponseStatus.USER_STATUS_FROZEN_ERROR);
        } else if (Objects.isNull(appUser)) {
            // 如果未查询到用户，则进行用户注册
            appUser = userService.createUser(mobile);
        }

        // 生成用户 Token，保存到分布式 Redis 集群中（分布式会话）
        String utoken = TokenUtils.generateToken();
        redisOperator.set(RedisKeyUtils.userTokenKey(appUser.getId()), utoken);
        // 保存用户 id 与 token 到 Cookie 中
        try {
            // 设置 Cookie 保存时间为一个月
            CookieUtils.setCookie(request, response, domainName, "utoken", URLEncoder.encode(utoken, "utf-8"), 30 * 24 * 60 * 60);
            CookieUtils.setCookie(request, response, domainName, "uid", URLEncoder.encode(appUser.getId(), "utf-8"), 30 * 24 * 60 * 60);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 用户登录或注册成功后，需要删除 Redis 中的短信验证码，验证码只能使用一次
        redisOperator.delete(RedisKeyUtils.userCodeKey(smsCode));

        // 返回用户的状态(与前端的约定)
        return new JsonResult(ResponseStatus.SUCCESS, appUser.getActiveStatus());
    }

    /**
     * 退出登录
     * <p>
     * 执行逻辑：
     * 1. 清除 Redis 中存储用户 Token 的 Key
     * 2. 设置 Cookie(uid,utoken) 过期时间为 0 ，这样就相当于清除 Cookie
     *
     * @param userId
     * @param request
     * @param response
     * @return
     */
    @Override
    public JsonResult logout(String userId, HttpServletRequest request, HttpServletResponse response) {
        redisOperator.delete(RedisKeyUtils.userTokenKey(userId));
        CookieUtils.setCookie(request, response, domainName, "uid", "", 0);
        CookieUtils.setCookie(request, response, domainName, "utoken", "", 0);
        return JsonResult.ok();
    }
}

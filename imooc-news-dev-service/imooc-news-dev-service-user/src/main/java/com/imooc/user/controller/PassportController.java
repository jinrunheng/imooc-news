package com.imooc.user.controller;

import com.imooc.api.controller.user.PassportControllerApi;
import com.imooc.bo.RegisterLoginBO;
import com.imooc.enums.ResponseStatus;
import com.imooc.enums.UserStatus;
import com.imooc.pojo.AppUser;
import com.imooc.result.JsonResult;
import com.imooc.user.service.UserService;
import com.imooc.utils.IPUtils;
import com.imooc.utils.RedisKeyUtils;
import com.imooc.utils.RedisOperator;
import com.imooc.utils.SMSUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @Author Dooby Kim
 * @Date 2021/12/22 3:26 下午
 * @Version 1.0
 */
@RestController
@Slf4j
public class PassportController implements PassportControllerApi {

    @Resource
    private SMSUtils smsUtils;

    @Resource
    private RedisOperator redisOperator;

    @Resource
    private UserService userService;

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
        String utoken = generateToken();
        redisOperator.set(RedisKeyUtils.userTokenKey(appUser.getId()), utoken);
        // 保存用户 ID 与 Token 到 Cookie 中
        try {
            // 设置 Cookie 保存时间为一个月
            setCookie(request, response, "utoken", URLEncoder.encode(utoken, "utf-8"), 30 * 24 * 60 * 60);
            setCookie(request, response, "uid", URLEncoder.encode(appUser.getId(), "utf-8"), 30 * 24 * 60 * 60);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 用户登录或注册成功后，需要删除 Redis 中的短信验证码，验证码只能使用一次
        redisOperator.delete(RedisKeyUtils.userCodeKey(smsCode));

        // 返回用户的状态(与前端的约定)
        return new JsonResult(ResponseStatus.SUCCESS, appUser.getActiveStatus());
    }

    /**
     * 设置 Cookie
     *
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param maxAge
     */
    private void setCookie(HttpServletRequest request,
                           HttpServletResponse response,
                           String cookieName,
                           String cookieValue,
                           Integer maxAge) {

        Cookie cookie = new Cookie(cookieName, cookieValue);

        cookie.setDomain("imoocnews.com");
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 生成用户 Token
     *
     * @return
     */
    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取 BO 中的错误信息
     *
     * @param result
     */
    private Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>();
        result.getFieldErrors().forEach(error -> map.put(error.getField(), error.getDefaultMessage()));
        return map;
    }
}

package com.imooc.user.controller;

import com.imooc.api.controller.user.PassportControllerApi;
import com.imooc.bo.RegisterLoginBO;
import com.imooc.result.JsonResult;
import com.imooc.result.ResponseStatus;
import com.imooc.utils.IPUtils;
import com.imooc.utils.RedisKeyUtils;
import com.imooc.utils.RedisOperator;
import com.imooc.utils.SMSUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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

    @Override
    public JsonResult getSMSCode(String phone, HttpServletRequest request) {
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
    public JsonResult doLogin(@Valid RegisterLoginBO registerLoginBO, BindingResult result) {
        // 判断 BindingResult 中是否绑定了错误的验证信息，如果有则返回
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
        return JsonResult.ok();
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

package com.imooc.user.controller;

import com.imooc.api.controller.user.PassportControllerApi;
import com.imooc.result.JsonResult;
import com.imooc.utils.IPUtils;
import com.imooc.utils.RedisKeyUtils;
import com.imooc.utils.RedisOperator;
import com.imooc.utils.SMSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
        // 随机生成四位数字的验证码
        String code = smsUtils.generateSMSCode();
        // 通过 Redis 存储用户的验证码，存储时长为 30 min
        redisOperator.set(RedisKeyUtils.userCodeKey(code), code, 30 * 60);

        Map<String, String> result = smsUtils.sendSMS(phone, code);
        if (result != null
                && result.get("code").equals("Ok")
                && result.get("msg").equals("send success")) {
            return JsonResult.ok();
        } else {
            return JsonResult.error();
        }
    }
}

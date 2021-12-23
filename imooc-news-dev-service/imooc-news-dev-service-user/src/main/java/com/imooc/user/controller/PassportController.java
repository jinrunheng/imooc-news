package com.imooc.user.controller;

import com.imooc.api.controller.user.PassportControllerApi;
import com.imooc.result.JsonResult;
import com.imooc.utils.RedisOperator;
import com.imooc.utils.SMSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("passport")
public class PassportController implements PassportControllerApi {

    @Resource
    private SMSUtils smsUtils;

    @Resource
    private RedisOperator redisOperator;

    @Override
    public JsonResult getSMSCode(String phone, HttpServletRequest request) {
        // HttpServletRequest 可以获取到用户的 ip；根据用户的 ip 地址进行限制，限制用户在 60 秒内只能获取一次验证码
        // 随机生成四位数字的验证码
        String code = smsUtils.generateSMSCode();
        Map<String, String> result = smsUtils.sendSMS("15526787357", code);
        if (result != null
                && result.get("code").equals("Ok")
                && result.get("msg").equals("send success")) {
            return JsonResult.ok();
        } else {
            return JsonResult.error();
        }
    }
}

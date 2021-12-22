package com.imooc.user.controller;

import com.imooc.api.controller.user.HelloControllerApi;
import com.imooc.result.JsonResult;
import com.imooc.result.ResponseStatus;
import com.imooc.utils.RedisOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class HelloController implements HelloControllerApi {

    @Resource
    private RedisOperator redisOperator;

    public JsonResult hello() {
        log.info("Test Lombok");
        return JsonResult.ok();
    }


    @GetMapping("/redis")
    public JsonResult testRedis() {
        redisOperator.set("name", "kim");
        return new JsonResult(ResponseStatus.SUCCESS, redisOperator.get("name"));
    }
}

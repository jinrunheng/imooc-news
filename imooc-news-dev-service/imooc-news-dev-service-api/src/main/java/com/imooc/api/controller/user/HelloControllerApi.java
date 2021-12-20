package com.imooc.api.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

@Api(value = "HelloController")
public interface HelloControllerApi {

    @ApiOperation(value = "hello 方法", httpMethod = "GET")
    @GetMapping("/hello")
    Object hello();
}

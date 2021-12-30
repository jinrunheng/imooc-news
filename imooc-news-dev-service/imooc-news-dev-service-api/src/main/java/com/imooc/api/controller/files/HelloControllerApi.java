package com.imooc.api.controller.files;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author Dooby Kim
 * @Date 2021/12/30 6:37 下午
 * @Version 1.0
 */
@Api(value = "HelloController", tags = {"HelloController 用于测试"})
public interface HelloControllerApi {

    @ApiOperation(value = "hello 方法，用于测试", notes = "hello 方法，用于测试", httpMethod = "GET")
    @GetMapping("/hello")
    Object hello();
}

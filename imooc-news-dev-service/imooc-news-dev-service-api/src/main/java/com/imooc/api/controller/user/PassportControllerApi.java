package com.imooc.api.controller.user;

import com.imooc.bo.RegisterLoginBO;
import com.imooc.result.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Author Dooby Kim
 * @Date 2021/12/22 3:18 下午
 * @Version 1.0
 * @Description 用户通行证 Controller 接口
 */
@Api(value = "PassportController", tags = {"用户注册登录通行证"})
@RequestMapping("passport")
public interface PassportControllerApi {

    @ApiOperation(value = "获取短信验证码", notes = "获取短信验证码", httpMethod = "GET")
    @GetMapping("/getSMSCode")
    JsonResult getSMSCode(String phone, HttpServletRequest request);

    @ApiOperation(value = "一键注册登录", notes = "一键注册登录", httpMethod = "POST")
    @PostMapping("/doLogin")
    JsonResult doLogin(@RequestBody @Valid RegisterLoginBO registerLoginBO, BindingResult result);
}

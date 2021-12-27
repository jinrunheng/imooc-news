package com.imooc.api.controller.user;

import com.imooc.bo.UpdateUserInfoBO;
import com.imooc.result.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * @Author Dooby Kim
 * @Date 2021/12/26 7:51 下午
 * @Version 1.0
 * @Description 用户基本信息 Controller 接口
 */
@Api(value = "UserInfoController", tags = {"用户信息相关"})
@RequestMapping("user")
public interface UserInfoControllerApi {

    @ApiOperation(value = "获取用户基本信息", notes = "获取用户基本信息", httpMethod = "POST")
    @PostMapping("/getUserInfo")
    JsonResult getUserInfo(@RequestParam String userId);

    @ApiOperation(value = "获取用户账号信息", notes = "获取用户账号信息", httpMethod = "POST")
    @PostMapping("/getAccountInfo")
    JsonResult getAccountInfo(@RequestParam String userId);

    @ApiOperation(value = "修改用户账号基本信息", notes = "修改用户账户基本信息", httpMethod = "POST")
    @PostMapping("/updateUserInfo")
    JsonResult updateUserInfo(@RequestBody @Valid UpdateUserInfoBO userInfoBO, BindingResult result);
}

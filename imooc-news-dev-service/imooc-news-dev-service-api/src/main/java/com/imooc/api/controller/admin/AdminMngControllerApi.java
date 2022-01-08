package com.imooc.api.controller.admin;

import com.imooc.bo.AdminLoginBO;
import com.imooc.result.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Dooby Kim
 * @Date 2022/1/8 11:05 下午
 * @Version 1.0
 */
@Api(value = "AdminMngController", tags = {"用于管理员 Admin 的登录"})
@RequestMapping("adminMng")
public interface AdminMngControllerApi {

    @ApiOperation(value = "admin 用户名密码登录", notes = "admin 用户名密码登录", httpMethod = "POST")
    @PostMapping("/adminLogin")
    JsonResult adminLogin(@RequestBody AdminLoginBO adminLoginBO, HttpServletRequest request, HttpServletResponse response);

}

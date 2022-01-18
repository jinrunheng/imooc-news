package com.imooc.api.controller.admin;

import com.imooc.bo.AddNewAdminBO;
import com.imooc.bo.AdminLoginBO;
import com.imooc.result.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @ApiOperation(value = "对应到前端的键盘事件，查询 admin 用户名是否存在", notes = "对应到前端的键盘事件，查询 admin 用户名是否存在", httpMethod = "POST")
    @PostMapping("/adminIsExist")
    JsonResult adminIsExist(@RequestParam String username);

    @ApiOperation(value = "创建 admin 用户", notes = "创建 admin 用户", httpMethod = "POST")
    @PostMapping("/addNewAdmin")
    JsonResult addNewAdmin(@RequestBody AddNewAdminBO addNewAdminBO, HttpServletRequest request, HttpServletResponse response);

    @ApiOperation(value = "查询 admin 用户列表", notes = "查询 admin 用户列表", httpMethod = "POST")
    @PostMapping("/getAdminList")
    JsonResult getAdminList(
            @ApiParam(name = "pageIndex", value = "查询第几页", required = false) @RequestParam Integer pageIndex,
            @ApiParam(name = "pageSize", value = "分页查询每一页显示条数", required = false) @RequestParam Integer pageSize);

    @ApiOperation(value = "admin 退出登录", notes = "admin 退出登录", httpMethod = "POST")
    @PostMapping("/adminLogout")
    JsonResult adminLogout(@RequestParam String adminId, HttpServletRequest request, HttpServletResponse response);
}

package com.imooc.admin.controller;

import com.imooc.admin.service.AdminUserService;
import com.imooc.api.controller.admin.AdminMngControllerApi;
import com.imooc.bo.AdminLoginBO;
import com.imooc.enums.ResponseStatus;
import com.imooc.exception.MyCustomException;
import com.imooc.pojo.AdminUser;
import com.imooc.result.JsonResult;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.RedisKeyUtils;
import com.imooc.utils.RedisOperator;
import com.imooc.utils.TokenUtils;
import com.tencentcloudapi.hcm.v20181106.models.EvaluationRequest;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Dooby Kim
 * @Date 2022/1/8 11:29 下午
 * @Version 1.0
 * @Description Admin 管理员用户相关的 Controller
 */
@Slf4j
@RestController
public class AdminMngController implements AdminMngControllerApi {

    @Resource
    private AdminUserService adminUserService;

    @Resource
    private RedisOperator redisOperator;

    @Value("${website.domain-name}")
    public String domainName;

    /**
     * admin 用户名密码登录
     * <p>
     * 逻辑流程：
     * ---- 1. 首先验证前端表单传给后端封装的对象 adminLoginBO 中，username 与 password 是否为空
     * -------- 如果为空,则直接返回
     * ---- 2. 查询 admin 用户的信息
     * -------- 查询 admin 用户是否存在，如果不存在，则返回管理员不存在或密码错误
     * ---- 3. 如果 admin 用户存在，则验证密码是否匹配
     *
     * @param adminLoginBO
     * @param request
     * @param response
     * @return
     */
    @Override
    public JsonResult adminLogin(AdminLoginBO adminLoginBO, HttpServletRequest request, HttpServletResponse response) {

        String adminLoginBOUsername = adminLoginBO.getUsername();
        String adminLoginBOPassword = adminLoginBO.getPassword();

        if (StringUtils.isBlank(adminLoginBOUsername)) {
            return new JsonResult(ResponseStatus.ADMIN_USERNAME_NULL_ERROR);
        }

        if (StringUtils.isBlank(adminLoginBOPassword)) {
            return new JsonResult(ResponseStatus.ADMIN_PASSWORD_NULL_ERROR);
        }

        AdminUser adminUser = adminUserService.queryAdminByUsername(adminLoginBOUsername);
        if (adminUser == null) {
            return new JsonResult(ResponseStatus.ADMIN_NOT_EXIT_ERROR);
        }

        boolean isPwdMatch = BCrypt.checkpw(adminLoginBOPassword, adminUser.getPassword());

        if (isPwdMatch) {
            setToken(adminUser, request, response);
            return JsonResult.ok();
        } else {
            return new JsonResult(ResponseStatus.ADMIN_NOT_EXIT_ERROR);
        }
    }

    /**
     * 对应到前端的键盘输入事件，校验 admin 用户名是否存在
     *
     * @param username
     * @return
     */
    @Override
    public JsonResult adminIsExist(String username) {
        checkAdminExist(username);
        return JsonResult.ok();
    }

    private void checkAdminExist(String username) {
        AdminUser adminUser = adminUserService.queryAdminByUsername(username);

        if (adminUser != null) {
            MyCustomException.display(ResponseStatus.ADMIN_USERNAME_ALREADY_EXIST_ERROR);
        }
    }


    /**
     * 用于 admin 用户登录后，token 的设置
     * <p>
     * ---- 1. 保存 token 到 redis 中
     * ---- 2. 设置 Cookie
     * -------- 设置 token 前端已经定义好，为 atoken；设置 Cookie maxAge 为一个月
     * -------- 设置 id 前端已经定义好，为 aid；设置 Cookie maxAge 为一个月
     * -------- 设置 name 前端已经定义好，为 aname，设置 Cookie maxAge 为一个月
     *
     * @param adminUser
     * @param request
     * @param response
     */
    private void setToken(AdminUser adminUser, HttpServletRequest request, HttpServletResponse response) {
        String token = TokenUtils.generateToken();
        redisOperator.set(RedisKeyUtils.adminTokenKey(adminUser.getId()), token);

        CookieUtils.setCookie(request, response, domainName, "atoken", token, 30 * 24 * 60 * 60);
        CookieUtils.setCookie(request, response, domainName, "aid", adminUser.getId(), 30 * 24 * 60 * 60);
        CookieUtils.setCookie(request, response, domainName, "aname", adminUser.getAdminName(), 30 * 24 * 60 * 60);
    }
}

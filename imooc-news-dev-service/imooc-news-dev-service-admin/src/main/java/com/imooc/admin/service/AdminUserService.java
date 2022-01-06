package com.imooc.admin.service;

import com.imooc.pojo.AdminUser;

/**
 * @Author Dooby Kim
 * @Date 2022/1/7 12:16 上午
 * @Version 1.0
 */
public interface AdminUserService {

    /**
     * 通过用户名获取管理员用户信息
     *
     * @param username
     * @return
     */
    AdminUser queryAdminByUsername(String username);
}

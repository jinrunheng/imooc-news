package com.imooc.admin.service;

import com.imooc.bo.AddNewAdminBO;
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

    /**
     * 创建新的 Admin 用户
     *
     * @param adminBO
     */
    void createAdminUser(AddNewAdminBO adminBO);

    /**
     * 分页查询 Admin 用户列表
     *
     * @param pageIndex
     * @param pageSize
     */
    void queryAdminList(Integer pageIndex, Integer pageSize);
}

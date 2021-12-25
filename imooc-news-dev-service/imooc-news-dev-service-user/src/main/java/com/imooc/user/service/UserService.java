package com.imooc.user.service;

import com.imooc.pojo.AppUser;

/**
 * @Author Dooby Kim
 * @Date 2021/12/24 1:08 下午
 * @Version 1.0
 */
public interface UserService {

    /**
     * 查询用户的手机号，判断用户是否存在，如果存在返回用户信息
     *
     * @param mobile
     * @return
     */
    AppUser queryMobileExist(String mobile);

    /**
     * 通过手机号，创建新用户
     *
     * @param mobile
     * @return
     */
    AppUser createUser(String mobile);
}
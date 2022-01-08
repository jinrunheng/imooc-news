package com.imooc.admin.service.impl;

import com.imooc.admin.mapper.AdminUserMapper;
import com.imooc.admin.service.AdminUserService;
import com.imooc.pojo.AdminUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * @Author Dooby Kim
 * @Date 2022/1/7 12:19 上午
 * @Version 1.0
 */
@Service
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public AdminUser queryAdminByUsername(String username) {

        Example adminExample = new Example(AdminUser.class);
        Example.Criteria adminCriteria = adminExample.createCriteria();
        adminCriteria.andEqualTo("username", username);
        return adminUserMapper.selectOneByExample(adminExample);
    }
}

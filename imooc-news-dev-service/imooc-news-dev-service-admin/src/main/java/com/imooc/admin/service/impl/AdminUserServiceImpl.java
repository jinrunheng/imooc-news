package com.imooc.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.imooc.admin.mapper.AdminUserMapper;
import com.imooc.admin.service.AdminUserService;
import com.imooc.bo.AddNewAdminBO;
import com.imooc.enums.ResponseStatus;
import com.imooc.exception.MyCustomException;
import com.imooc.pojo.AdminUser;
import com.imooc.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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

    /**
     * 在分布式的微服务应用中，我们往往会考虑可扩展性，随着数据越来越多，我们就要对数据库进行分库分表
     * 分库分表中，自增主键的可维护性差。数据库主键必须保证是全局唯一的。
     * 进而，我们可以使用 idworker
     * 依赖：
     * <dependency>
     * <groupId>com.github.bingoohuang</groupId>
     * <artifactId>idworker-client</artifactId>
     * <version>0.0.7</version>
     * </dependency>
     * idworker 是通过 Snowflake 算法来生成 id 的一种工具，可以保证全局主键唯一
     */
    @Resource
    private Sid sid;

    @Override
    public AdminUser queryAdminByUsername(String username) {

        Example adminExample = new Example(AdminUser.class);
        Example.Criteria adminCriteria = adminExample.createCriteria();
        adminCriteria.andEqualTo("username", username);
        return adminUserMapper.selectOneByExample(adminExample);
    }

    @Transactional
    @Override
    public void createAdminUser(AddNewAdminBO adminBO) {
        String adminId = sid.nextShort();
        AdminUser adminUser = new AdminUser();
        adminUser.setId(adminId);
        adminUser.setUsername(adminBO.getUsername());
        adminUser.setAdminName(adminBO.getAdminName());

        // 如果 adminBO 中，密码不为空，则需要对密码进行加密后存入到数据库中
        if (StringUtils.isNotBlank(adminBO.getPassword())) {
            String pwd = BCrypt.hashpw(adminBO.getPassword(), BCrypt.gensalt());
            adminUser.setPassword(pwd);
        }

        // 如果人脸上传后，则 faceId 不为空，需要和 admin 信息关联存储入库
        if (StringUtils.isNotBlank(adminBO.getFaceId())) {
            adminUser.setFaceId(adminBO.getFaceId());
        }

        adminUser.setCreatedTime(new Date());
        adminUser.setUpdatedTime(new Date());

        int result = adminUserMapper.insert(adminUser);

        if (result != 1) {
            MyCustomException.display(ResponseStatus.ADMIN_CREATE_ERROR);
        }
    }

    @Override
    public PageUtils.PageInfoVO queryAdminList(Integer page, Integer pageSize) {
        Example adminExample = new Example(AdminUser.class);
        adminExample.orderBy("createdTime").desc();

        PageHelper.startPage(page, pageSize);
        List<AdminUser> adminUsers = adminUserMapper.selectByExample(adminExample);

        return PageUtils.setPageInfo(adminUsers, page);
    }
}

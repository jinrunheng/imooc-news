package com.imooc.user.service.impl;

import com.imooc.enums.Sex;
import com.imooc.enums.UserStatus;
import com.imooc.pojo.AppUser;
import com.imooc.user.mapper.AppUserMapper;
import com.imooc.user.service.UserService;
import com.imooc.utils.DesensitizationUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.n3r.idworker.Sid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;

/**
 * @Author Dooby Kim
 * @Date 2021/12/24 1:10 下午
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    private static String defaultUserFace = "https://tva1.sinaimg.cn/large/008i3skNgy1gxowcvgga4j30b40b4dg2.jpg";

    @Resource
    private AppUserMapper userMapper;

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
    public AppUser queryMobileExist(String mobile) {
        Example userExample = new Example(AppUser.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andEqualTo("mobile", mobile);

        AppUser appUser = userMapper.selectOneByExample(userExample);
        return appUser;
    }

    /**
     * 涉及到更新操作，添加事务
     *
     * @param mobile
     * @return
     */
    @Override
    @Transactional
    public AppUser createUser(String mobile) {
        try {
            AppUser user = AppUser.builder()
                    .id(sid.nextShort())
                    .mobile(mobile)
                    .nickname("用户 " + DesensitizationUtils.mobileDesensitization(mobile))
                    .face(defaultUserFace)
                    .birthday(DateUtils.parseDate("1900-01-01", "yyyy-MM-dd"))
                    .sex(Sex.secret.getType())
                    .activeStatus(UserStatus.INACTIVE.getType())
                    .totalIncome(0)
                    .createdTime(new Date())
                    .updatedTime(new Date())
                    .build();

            userMapper.insert(user);

            return user;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}

package com.imooc.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imooc.bo.UpdateUserInfoBO;
import com.imooc.enums.ResponseStatus;
import com.imooc.enums.Sex;
import com.imooc.enums.UserStatus;
import com.imooc.exception.MyCustomException;
import com.imooc.pojo.AppUser;
import com.imooc.user.mapper.AppUserMapper;
import com.imooc.user.service.UserService;
import com.imooc.utils.DateUtils;
import com.imooc.utils.DesensitizationUtils;
import com.imooc.utils.RedisKeyUtils;
import com.imooc.utils.RedisOperator;
import lombok.extern.slf4j.Slf4j;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author Dooby Kim
 * @Date 2021/12/24 1:10 下午
 * @Version 1.0
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    // TODO: 目前用户的默认头像是一个写死的链接
    private static String defaultUserFace = "https://tva1.sinaimg.cn/large/008i3skNgy1gxowcvgga4j30b40b4dg2.jpg";

    @Resource
    private AppUserMapper userMapper;

    @Resource
    private RedisOperator redisOperator;

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
        AppUser user = AppUser.builder()
                .id(sid.nextShort())
                .mobile(mobile)
                .nickname("用户 " + DesensitizationUtils.mobileDesensitization(mobile))
                .face(defaultUserFace)
                .birthday(DateUtils.string2Date("1900-01-01"))
                .sex(Sex.secret.getType())
                .activeStatus(UserStatus.INACTIVE.getType())
                .totalIncome(0)
                .createdTime(new Date())
                .updatedTime(new Date())
                .build();

        userMapper.insert(user);

        return user;
    }

    /**
     * 根据用户 userId，获取用户信息
     * 因为用户信息的更改频率不会很高，为了减轻数据库的查询压力，在这里面使用缓存方案
     * 缓存工具：Redis
     * 逻辑流程：
     * 1. 首先获取用户时，先从 Redis 缓存中获取
     * ----1. 如果获取到，则直接返回
     * ----2. 获取不到，则从数据库查询
     * 2. 返回用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public AppUser getUser(String userId) {

        String redisCacheKey = RedisKeyUtils.userInfoKey(userId);

        if (redisOperator.hasKey(redisCacheKey)) {
            return JSON.parseObject(redisOperator.get(redisCacheKey), AppUser.class);
        }

        AppUser appUser = userMapper.selectByPrimaryKey(userId);
        String appUserJSONString = JSON.toJSONString(appUser);
        redisOperator.set(redisCacheKey, appUserJSONString);
        return appUser;
    }

    /**
     * 更新用户信息（激活）
     * 更新原则：先更新数据库，再更新 Redis 缓存
     * 逻辑流程：
     * 1. 通过前端传递给后端的 BO 对象，后端进行更新（更新账号状态为激活，更新修改时间）
     * 2. 更新 Redis 缓存
     *
     * @param userInfoBO
     */
    @Override
    public void updateUserInfo(UpdateUserInfoBO userInfoBO) {
        AppUser newUserInfo = new AppUser();
        BeanUtils.copyProperties(userInfoBO, newUserInfo);
        newUserInfo.setUpdatedTime(new Date());
        newUserInfo.setActiveStatus(UserStatus.ACTIVE.getType());

        // 更新数据库
        // updateByXXX 与 updateByXXXSelective 的区别为：
        // updateByXXX 会将空的选项进行覆盖
        // updateByXXXSelective 遇到空的字段时，则不会覆盖
        int result = userMapper.updateByPrimaryKeySelective(newUserInfo);
        // 更新操作会返回更新条目的数量，如果结果 result 不为 1，则说明更新失败
        if (result != 1) {
            MyCustomException.display(ResponseStatus.USER_UPDATE_ERROR);
        }

        // 更新缓存
        String userId = userInfoBO.getId();
        String redisCacheKey = RedisKeyUtils.userInfoKey(userId);
        AppUser user = userMapper.selectByPrimaryKey(userId);
        String appUserJSONString = JSON.toJSONString(user);
        redisOperator.set(redisCacheKey, appUserJSONString);
    }
}

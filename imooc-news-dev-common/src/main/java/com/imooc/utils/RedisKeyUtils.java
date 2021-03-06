package com.imooc.utils;

/**
 * @Author Dooby Kim
 * @Date 2021/12/23 1:14 下午
 * @Version 1.0
 * @Description 生成需要的 Redis Key
 */
public class RedisKeyUtils {

    public static final String REDIS_ALL_CATEGORY = "redis_all_category";

    /**
     * Redis 中存储用户 ip 的 Key；限制 60 秒不能重复发送验证码
     *
     * @param userip
     * @return
     */
    public static String userIpKey(String userip) {
        return "user:ip:" + userip;
    }

    /**
     * Redis 中存储用户验证码的 Key
     *
     * @param code
     * @return
     */
    public static String userCodeKey(String code) {
        return "user:code:" + code;
    }

    /**
     * Redis 中存储用户 Token 的 Key
     *
     * @param userId
     * @return
     */
    public static String userTokenKey(String userId) {
        return "user:token:" + userId;
    }

    /**
     * Redis 中存储用户信息的 Key，用作缓存
     *
     * @param userId
     * @return
     */
    public static String userInfoKey(String userId) {
        return "user:info:" + userId;
    }

    /**
     * Redis 中存储 admin 用户 Token 的 Key
     *
     * @param adminId
     * @return
     */
    public static String adminTokenKey(String adminId) {
        return "admin:token:" + adminId;
    }

}

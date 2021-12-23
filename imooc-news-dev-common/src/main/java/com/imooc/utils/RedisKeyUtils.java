package com.imooc.utils;

/**
 * @Author Dooby Kim
 * @Date 2021/12/23 1:14 下午
 * @Version 1.0
 * @Description 生成需要的 Redis Key
 */
public class RedisKeyUtils {

    /**
     * Redis 中存储用户 ip 的 Key；限制 60 秒不能重复发送验证码
     *
     * @param userip
     * @return
     */
    public static String userIpKey(String userip) {
        return "user:ip:" + userip;
    }

    public static String userCodeKey(String code) {
        return "user:code:" + code;
    }

}

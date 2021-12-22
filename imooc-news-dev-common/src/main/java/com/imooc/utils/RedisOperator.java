package com.imooc.utils;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author Dooby Kim
 * @Date 2021/12/22 5:07 下午
 * @Version 1.0
 */
@Component
public class RedisOperator {

    @Resource
    private StringRedisTemplate redisTemplate;

    /**
     * 判断 key 是否存在
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 返回 Key 的剩余生存时间
     *
     * @param key
     * @return
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 设置 Key 的过期时间，单位：秒
     *
     * @param key
     * @param timeout
     */
    public void setExpire(String key, long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }
}

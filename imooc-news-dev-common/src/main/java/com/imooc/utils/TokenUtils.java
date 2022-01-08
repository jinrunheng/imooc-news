package com.imooc.utils;

import java.util.UUID;

/**
 * @Author Dooby Kim
 * @Date 2022/1/9 12:24 上午
 * @Version 1.0
 * @Description 用户生成 Token 的工具类
 */
public class TokenUtils {

    /**
     * 用于生成用户 Token
     *
     * @return
     */
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}

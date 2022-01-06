package com.imooc.admin.controller;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * @Author Dooby Kim
 * @Date 2022/1/6 5:53 下午
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        String admin = BCrypt.hashpw("admin456", BCrypt.gensalt());
        System.out.println(admin);
    }
}

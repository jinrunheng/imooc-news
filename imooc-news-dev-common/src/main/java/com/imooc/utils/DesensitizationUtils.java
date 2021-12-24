package com.imooc.utils;

/**
 * @Author Dooby Kim
 * @Date 2021/12/24 2:31 下午
 * @Version 1.0
 * @Description 数据脱敏处理的工具类
 */
public class DesensitizationUtils {

    /**
     * 手机号脱敏，中间四位替换为 "*"
     *
     * @param mobile
     * @return
     */
    public static String mobileDesensitization(String mobile) {
        return mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());
    }
}

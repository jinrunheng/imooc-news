package com.imooc.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Dooby Kim
 * @Date 2021/12/23 11:14 上午
 * @Version 1.0
 * @Description 获取用户 IP 地址的工具类
 */
public class IPUtils {

    /**
     * 当我们通过 request.getRemoteAddr() 来获取用户 IP 地址，很有可能获取到的是代理服务器的 IP
     * 如果使用了多级反向代理，IP 需要从请求头中获得第一个非 unknown 的 IP 才是用户的有效 IP
     * <p>
     * 请求头：
     * X-Forwarded-For : Squid 服务代理
     * Proxy-Client-IP : apache 服务代理
     * WL-Proxy-Client-IP : weblogic 服务代理
     * HTTP_CLIENT_IP :
     * HTTP_X_FORWARDED_FOR :
     * X-Real-IP : nginx 服务代理
     * </p>
     *
     * @param request
     * @return
     */
    public static String getIPAddress(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}

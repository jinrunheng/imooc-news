package com.imooc.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Dooby Kim
 * @Date 2022/1/9 12:43 上午
 * @Version 1.0
 */
public class CookieUtils {
    /**
     * 设置 Cookie
     *
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param domainName
     * @param maxAge
     */
    public static void setCookie(HttpServletRequest request,
                                 HttpServletResponse response,
                                 String domainName,
                                 String cookieName,
                                 String cookieValue,
                                 Integer maxAge) {

        Cookie cookie = new Cookie(cookieName, cookieValue);

        cookie.setDomain(domainName);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}

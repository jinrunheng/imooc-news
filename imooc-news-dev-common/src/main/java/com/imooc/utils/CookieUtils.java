package com.imooc.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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

    /**
     * 删除 Cookie
     *
     * @param request
     * @param response
     * @param cookieName
     */
    public static void deleteCookie(HttpServletRequest request,
                                    HttpServletResponse response,
                                    String cookieName) {
        try {
            String cookieVal = URLEncoder.encode("", "utf-8");
            setCookie(request, response, "", cookieName, cookieVal, 0);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

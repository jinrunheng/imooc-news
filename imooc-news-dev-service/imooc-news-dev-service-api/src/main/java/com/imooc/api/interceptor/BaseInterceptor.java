package com.imooc.api.interceptor;

import com.imooc.enums.ResponseStatus;
import com.imooc.exception.MyCustomException;
import com.imooc.utils.RedisKeyUtils;
import com.imooc.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author Dooby Kim
 * @Date 2022/6/15 11:03 下午
 * @Version 1.0
 */
public class BaseInterceptor {

    @Autowired
    public RedisOperator redisOperator;

    public static final String REDIS_USER_TOKEN = "redis_user_token";
    public static final String REDIS_USER_INFO = "redis_user_info";
    public static final String REDIS_ADMIN_TOKEN = "redis_admin_token";

    public boolean verifyUserIdToken(String id,
                                     String token) {

        if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(token)) {

            String redisToken = RedisKeyUtils.userTokenKey(id);
            if (StringUtils.isBlank(id)) {
                MyCustomException.display(ResponseStatus.UN_LOGIN_ERROR);
                return false;
            } else {
                if (!redisToken.equalsIgnoreCase(token)) {
                    MyCustomException.display(ResponseStatus.TICKET_INVALID_ERROR);
                    return false;
                }
            }
        } else {
            MyCustomException.display(ResponseStatus.UN_LOGIN_ERROR);
            return false;
        }
        return true;
    }

    // 从cookie中取值
    public String getCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(key)) {
                String value = cookie.getValue();
                return value;
            }
        }
        return null;
    }
}

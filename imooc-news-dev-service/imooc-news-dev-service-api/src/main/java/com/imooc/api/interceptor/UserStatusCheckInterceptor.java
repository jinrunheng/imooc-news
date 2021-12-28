package com.imooc.api.interceptor;

import com.alibaba.fastjson.JSON;
import com.imooc.enums.ResponseStatus;
import com.imooc.enums.UserStatus;
import com.imooc.exception.MyCustomException;
import com.imooc.pojo.AppUser;
import com.imooc.utils.RedisKeyUtils;
import com.imooc.utils.RedisOperator;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Dooby Kim
 * @Date 2021/12/28 4:46 下午
 * @Version 1.0
 * @Description 用户状态检查拦截器
 * <p>
 * 对于一些功能，譬如发表文章，修改文章，删除文章，发评论，查看评论等功能
 * 这些功能需要在用户状态激活后，才能使用；否则需要进行拦截，并提示用户前往[账号设置]进行激活
 */
public class UserStatusCheckInterceptor implements HandlerInterceptor {

    @Resource
    private RedisOperator redisOperator;

    /**
     * 拦截器处理流程：
     * <p>
     * 1. 首先从请求头中获取 "headerUserId"，对应到用户 id
     * 2. 通过用户 id 获取到 Redis 缓存中的用户信息
     * ---- 1. 如果获取不到，则抛出用户未登录异常，并返回 false
     * ---- 2. 如果获取到，则进一步检查用户对应的状态
     * -------- 1. 如果用户状态为未激活，则抛出用户账号未激活异常，并返回 false
     * -------- 2. 如果用户状态为冻结，则抛出用户账号被冻结异常，并返回 false
     * -------- 3. 否则，返回 true
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("headerUserId");
        String userInfoKey = RedisKeyUtils.userInfoKey(userId);
        if (redisOperator.hasKey(userInfoKey)) {
            AppUser appUser = JSON.parseObject(redisOperator.get(userInfoKey), AppUser.class);
            Integer userActiveStatus = appUser.getActiveStatus();
            if (userActiveStatus.equals(UserStatus.INACTIVE.getType())) {
                MyCustomException.display(ResponseStatus.USER_STATUS_INACTIVE_ERROR);
                return false;
            } else if (userActiveStatus.equals(UserStatus.FROZEN.getType())) {
                MyCustomException.display(ResponseStatus.USER_STATUS_FROZEN_ERROR);
                return false;
            } else {
                return true;
            }
        } else {
            MyCustomException.display(ResponseStatus.UN_LOGIN_ERROR);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

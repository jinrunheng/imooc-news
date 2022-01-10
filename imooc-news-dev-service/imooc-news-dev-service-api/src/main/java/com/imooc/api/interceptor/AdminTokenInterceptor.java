package com.imooc.api.interceptor;

import com.imooc.enums.ResponseStatus;
import com.imooc.exception.MyCustomException;
import com.imooc.utils.RedisKeyUtils;
import com.imooc.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Dooby Kim
 * @Date 2022/1/10 10:44 下午
 * @Version 1.0
 * @Description Admin 用户会话拦截器
 * 如果 Admin 用户未登录或 Redis 中存储的用户会话失效，则拦截
 */
public class AdminTokenInterceptor implements HandlerInterceptor {

    @Resource
    private RedisOperator redisOperator;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String adminUserId = request.getHeader("adminUserId");
        String adminUserToken = request.getHeader("adminUserToken");
        return verifyUserIdAndToken(adminUserId, adminUserToken);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    /**
     * 验证 Admin 用户会话状态
     * 执行流程：
     * 1. 从请求头中获取到 adminUserId 与 adminUserToken，分别对应 admin 用户 id 与 token
     * 2. 首先判断 id 与 token 是否均不为空，如果有一个为空，则抛出"用户未登录"异常；返回 false
     * 3. 如果 id 与 token 均不为空
     * ---- 1. 在 Redis 中查询是否有生成 token 时存储的 key，如果没有，则抛出"用户会话失效"异常；返回 false
     * ---- 2. 如果有，则返回 true
     *
     * @param id
     * @param token
     * @return
     */
    private boolean verifyUserIdAndToken(String id, String token) {
        if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(token)) {
            if (!redisOperator.hasKey(RedisKeyUtils.adminTokenKey(id))) {
                MyCustomException.display(ResponseStatus.TICKET_INVALID_ERROR);
                return false;
            } else {
                return true;
            }
        } else {
            MyCustomException.display(ResponseStatus.UN_LOGIN_ERROR);
            return false;
        }
    }
}

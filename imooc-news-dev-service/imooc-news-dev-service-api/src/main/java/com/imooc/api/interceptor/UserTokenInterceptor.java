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
 * @Date 2021/12/28 2:38 下午
 * @Version 1.0
 * @Description 用户会话拦截器
 * 如果用户未登录或 Redis 中存储的用户会话失效，则拦截
 */
public class UserTokenInterceptor implements HandlerInterceptor {

    @Resource
    private RedisOperator redisOperator;

    /**
     * 详细判断逻辑可以参见 verifyUserIdAndToken 方法
     *
     * @param request
     * @param response
     * @param handler
     * @return 如果返回 true 表示并未拦截；如果返回 false 表示被拦截
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在 PassportController 的 doLogin 方法中，如果用户注册登录成功，我们会将 uid 与 utoken 设置到了 Cookie 中
        // 前端会将该信息设置到请求头中
        String userId = request.getHeader("headerUserId");
        String userToken = request.getHeader("headerUserToken");
        return verifyUserIdAndToken(userId, userToken);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    /**
     * 验证用户会话状态
     * 执行流程：
     * 1. 从请求头中获取到 headerUserId 与 headerUserToken，分别对应用户 id 与 token
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
            if (!redisOperator.hasKey(RedisKeyUtils.userTokenKey(id))) {
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

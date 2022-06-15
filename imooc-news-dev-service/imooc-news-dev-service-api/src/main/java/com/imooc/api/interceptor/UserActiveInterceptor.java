package com.imooc.api.interceptor;

import com.imooc.enums.ResponseStatus;
import com.imooc.enums.UserStatus;
import com.imooc.exception.MyCustomException;
import com.imooc.pojo.AppUser;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.RedisKeyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Dooby Kim
 * @Date 2022/6/15 10:59 下午
 * @Version 1.0
 * <p>
 * 用户激活状态检查拦截器
 * <p>
 * 用于 发文章，修改文章，删除文章，发表评论，查看评论等等
 * 这些接口都是需要在用户激活以后，才能进行的，如果用户未激活，则需要提示用户前往[账号设置]去修改信息，完成激活
 */
public class UserActiveInterceptor extends BaseInterceptor implements HandlerInterceptor {
    /**
     * 拦截请求，访问controller之前
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
        if (redisOperator.get(RedisKeyUtils.userTokenKey(userId)) == null) {
            MyCustomException.display(ResponseStatus.UN_LOGIN_ERROR);
            return false;
        }
        String userJson = redisOperator.get(RedisKeyUtils.userInfoKey(userId));
        AppUser user = null;
        if (StringUtils.isNotBlank(userJson)) {
            user = JsonUtils.jsonToPojo(userJson, AppUser.class);
        }

        if (user.getActiveStatus() == null
                || user.getActiveStatus() != UserStatus.ACTIVE.getType()) {
            MyCustomException.display(ResponseStatus.USER_STATUS_INACTIVE_ERROR);
            return false;
        }

        /**
         * false：请求被拦截
         * true：请求通过验证，放行
         */
        return true;
    }


    /**
     * 请求访问到controller之后，渲染视图之前
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 请求访问到controller之后，渲染视图之后
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

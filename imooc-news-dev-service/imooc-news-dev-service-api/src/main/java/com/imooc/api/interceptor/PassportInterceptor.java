package com.imooc.api.interceptor;

import com.imooc.exception.MyCustomException;
import com.imooc.result.ResponseStatus;
import com.imooc.utils.IPUtils;
import com.imooc.utils.RedisKeyUtils;
import com.imooc.utils.RedisOperator;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Dooby Kim
 * @Date 2021/12/23 3:16 下午
 * @Version 1.0
 * @Description 通行证服务相关拦截器
 */
public class PassportInterceptor implements HandlerInterceptor {

    @Resource
    private RedisOperator redisOperator;

    /**
     * 拦截器处理，在访问 Controller 以前
     *
     * @param request
     * @param response
     * @param handler
     * @return 如果返回 true 表示并未拦截；如果返回 false 表示被拦截
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = IPUtils.getIPAddress(request);
        boolean keyIsExist = redisOperator.hasKey(RedisKeyUtils.userIpKey(ip));
        if (keyIsExist) {
            // 如果 key 存在，说明用户获取验证码时间还没有超过 60 秒
            MyCustomException.display(ResponseStatus.SMS_SEND_TOO_FAST_ERROR);
            return false;
        }
        return true;
    }

    /**
     * 拦截起处理，在请求访问到 Controller 以后，渲染视图以前
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
     * 拦截起处理，渲染视图以后
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

package com.imooc.api.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @Author Dooby Kim
 * @Date 2021/12/29 9:20 上午
 * @Version 1.0
 * @Description xxx
 * <p>
 * AOP 通知：
 * 1. 前置通知（Before）：调用方法之前去执行 AOP
 * 2. 正常返回通知（After-returning）：调用方法正常返回则执行 AOP，如果抛出异常，则不会执行
 * 3. 环绕通知（Around）：调用方法之前和之后都可以去执行 AOP
 * 4. 异常通知（After-throwing）：调用方法抛出异常时，执行 AOP
 * 5. 最终返回通知（After）：调用方法后，无论方法是正常执行，还是抛出异常，都会执行 AOP
 */
@Aspect
@Component
@Slf4j
public class ServiceLogAspect {

    @Around("execution(* com.imooc.*.service.impl..*.*(..))")
    public Object recordDurationOfService() {
        // TODO
        return null;
    }
}

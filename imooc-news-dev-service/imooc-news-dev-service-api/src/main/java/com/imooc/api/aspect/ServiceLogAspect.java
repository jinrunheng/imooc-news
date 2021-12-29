package com.imooc.api.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
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

    /**
     * 关于 AOP 的 execution 表达式：
     * ---- 1. execution 为表达式的主体
     * ---- 2. 第一个 "*" 表示返回值类型为任意类型
     * ---- 3. com.imooc.*.servie.impl 为 AOP 切点匹配的包名，这里面是我们进行横切的业务实现类
     * ---- 4. 包名后面的 ".." 表示当前包以及子包
     * ---- 5. 第二个 "*" 表示类名，即所有类
     * ---- 6. ".*(..)" 表示任何方法名，括号表示接受参数，".." 表示任何参数类型
     * <p>
     * 通过 AOP 计算 service 层方法执行时间
     * 1. 如果执行耗时超过 3 秒，则使用 log.error 进行日志打印
     * 2. 如果执行耗时超过 2 秒，则使用 log.warn 进行日志打印
     * 3. 否则，使用 log.info 进行日志打印
     *
     * @return
     */
    @Around("execution(* com.imooc.*.service.impl..*.*(..))")
    public Object recordDurationOfService(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("==== 开始执行 {}.{} ====", joinPoint.getTarget().getClass(), joinPoint.getSignature().getName());

        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();

        long takeTime = end - start;

        if (takeTime > 3000) {
            log.error("当前执行耗时：{}", takeTime);
        } else if (takeTime > 2000) {
            log.warn("当前执行耗时：{}", takeTime);
        } else {
            log.info("当前执行耗时：{}", takeTime);
        }
        return result;
    }
}

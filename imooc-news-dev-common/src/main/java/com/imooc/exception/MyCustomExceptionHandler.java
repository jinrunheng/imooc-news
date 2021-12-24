package com.imooc.exception;

import com.imooc.result.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author Dooby Kim
 * @Date 2021/12/23 10:49 下午
 * @Version 1.0
 * @Description 为了方便对异常统一管理，Spring MVC 提供了 @ControllerAdvice 注解用于捕获自定义异常，对异常进行统一处理。
 */
@ControllerAdvice
@Slf4j
public class MyCustomExceptionHandler {

    @ResponseBody
    @ExceptionHandler(MyCustomException.class)
    public JsonResult returnMyCustomException(MyCustomException exception) {
        log.error(exception.getMessage());
        return new JsonResult(exception.getResponseStatus());
    }
}

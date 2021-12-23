package com.imooc.exception;

import com.imooc.result.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author Dooby Kim
 * @Date 2021/12/23 5:07 下午
 * @Version 1.0
 * @Description 自定义异常，用于统一处理项目中出现的异常
 */
@Getter
@Setter
public class MyCustomException extends RuntimeException {

    private ResponseStatus responseStatus;

    public MyCustomException(ResponseStatus responseStatus) {
        super("异常状态码：" + responseStatus.getStatus() + "；异常信息：" + responseStatus.getMsg());
        this.responseStatus = responseStatus;
    }

    public static void display(ResponseStatus responseStatus) {
        throw new MyCustomException(responseStatus);
    }
}

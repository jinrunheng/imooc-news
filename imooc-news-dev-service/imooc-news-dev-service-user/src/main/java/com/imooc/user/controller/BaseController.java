package com.imooc.user.controller;

import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Dooby Kim
 * @Date 2021/12/26 8:50 下午
 * @Version 1.0
 * @Description 用于封装 Controller 中用到的一些公共方法
 */
public class BaseController {

    /**
     * 获取 BO 中的错误信息
     *
     * @param result
     */
    public Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>();
        result.getFieldErrors().forEach(error -> map.put(error.getField(), error.getDefaultMessage()));
        return map;
    }
}

package com.imooc.article.controller;

import com.imooc.api.controller.admin.HelloControllerApi;
import com.imooc.result.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Dooby Kim
 * @Date 2022/6/15 9:56 下午
 * @Version 1.0
 */
@RestController
@Slf4j
public class HelloController implements HelloControllerApi {

    public JsonResult hello() {
        log.info("Test Hello");
        return JsonResult.ok();
    }
}
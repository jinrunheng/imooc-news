package com.imooc.files.controller;

import com.imooc.api.controller.files.HelloControllerApi;
import com.imooc.result.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Dooby Kim
 * @Date 2021/12/30 6:18 下午
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


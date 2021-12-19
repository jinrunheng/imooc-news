package com.imooc.user.controller;

import com.imooc.api.controller.user.TestControllerApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController implements TestControllerApi {

    public Object test() {
        log.info("Test Lombok");
        return "hello";
    }
}

package com.imooc.user.controller;

import com.imooc.api.controller.user.TestControllerApi;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController implements TestControllerApi {

    public Object test() {
        return "hello";
    }
}

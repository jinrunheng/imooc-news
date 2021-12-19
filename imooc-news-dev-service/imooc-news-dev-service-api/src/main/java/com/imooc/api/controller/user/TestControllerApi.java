package com.imooc.api.controller.user;

import org.springframework.web.bind.annotation.GetMapping;

public interface TestControllerApi {

    @GetMapping("/hello")
    Object test();
}

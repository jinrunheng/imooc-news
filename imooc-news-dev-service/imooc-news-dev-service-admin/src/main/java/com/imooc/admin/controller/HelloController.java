package com.imooc.admin.controller;

<<<<<<< HEAD
import com.imooc.api.controller.user.HelloControllerApi;
import com.imooc.result.JsonResult;
import com.imooc.utils.RedisOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;


=======
>>>>>>> a1757bb749785b0c6dd0197a861723b6712f4cd8
/**
 * @Author Dooby Kim
 * @Date 2022/1/4 5:50 下午
 * @Version 1.0
 */
<<<<<<< HEAD
@RestController
@Slf4j
public class HelloController implements HelloControllerApi {

    public JsonResult hello() {
        log.info("Test Hello");
        return JsonResult.ok();
    }
=======
public class HelloController {
>>>>>>> a1757bb749785b0c6dd0197a861723b6712f4cd8
}

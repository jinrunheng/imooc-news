package com.imooc.api.controller.user;

import com.imooc.result.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @Author Dooby Kim
 * @Date 2022/4/1 9:23 下午
 * @Version 1.0
 */
@Api(value = "AppUserMngController", tags = {"用户管理相关的接口"})
@RequestMapping("appUser")
public interface AppUserMngControllerApi {

    @PostMapping("queryAll")
    @ApiOperation(value = "查询所有用户", notes = "查询所有用户", httpMethod = "POST")
    JsonResult queryAll(@RequestParam String nickname,
                        @RequestParam Integer status,
                        @RequestParam Date startDate,
                        @RequestParam Date endDate,
                        @RequestParam Integer page,
                        @RequestParam Integer pageSize);
}

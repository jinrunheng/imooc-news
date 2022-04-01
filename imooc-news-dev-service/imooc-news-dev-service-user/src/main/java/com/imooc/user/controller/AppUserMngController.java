package com.imooc.user.controller;

import com.imooc.api.controller.BaseController;
import com.imooc.api.controller.user.AppUserMngControllerApi;
import com.imooc.result.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author Dooby Kim
 * @Date 2022/4/1 9:35 下午
 * @Version 1.0
 */
@RestController
@Slf4j
public class AppUserMngController extends BaseController implements AppUserMngControllerApi {
    @Override
    public JsonResult queryAll(String nickName, Integer status, Date startDate, Date endDate, Integer page, Integer pageSize) {
        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }

        return JsonResult.ok();
    }
}

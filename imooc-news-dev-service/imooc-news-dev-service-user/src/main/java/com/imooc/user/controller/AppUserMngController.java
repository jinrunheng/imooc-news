package com.imooc.user.controller;

import com.imooc.api.controller.BaseController;
import com.imooc.api.controller.user.AppUserMngControllerApi;
import com.imooc.enums.ResponseStatus;
import com.imooc.enums.UserStatus;
import com.imooc.exception.MyCustomException;
import com.imooc.result.JsonResult;
import com.imooc.user.service.AppUserMngService;
import com.imooc.user.service.UserService;
import com.imooc.utils.PageUtils;
import com.imooc.utils.RedisKeyUtils;
import com.imooc.utils.RedisOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AppUserMngService appUserMngService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisOperator redisOperator;

    @Override
    public JsonResult queryAll(String nickname,
                               Integer status,
                               Date startDate,
                               Date endDate,
                               Integer page,
                               Integer pageSize) {
        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }

        PageUtils.PageInfoVO pageInfoVO = appUserMngService.queryAllUserList(nickname, status, startDate, endDate, page, pageSize);

        return new JsonResult(ResponseStatus.SUCCESS, pageInfoVO);
    }

    @Override
    public JsonResult userDetail(String userId) {
        return new JsonResult(ResponseStatus.SUCCESS, userService.getUser(userId));
    }

    @Override
    public JsonResult freezeUserOrNot(String userId, Integer doStatus) {

        if (!UserStatus.isUserStatusValid(doStatus)) {
            return new JsonResult(ResponseStatus.USER_STATUS_ERROR);
        }

        appUserMngService.freezeUserOrNot(userId, doStatus);

        // 刷新用户状态，直接删除用户会话
        redisOperator.delete(RedisKeyUtils.userInfoKey(userId));

        return JsonResult.ok();
    }


}

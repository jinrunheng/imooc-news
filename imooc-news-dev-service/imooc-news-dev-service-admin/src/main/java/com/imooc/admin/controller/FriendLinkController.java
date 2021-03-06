package com.imooc.admin.controller;

import com.imooc.admin.service.FriendLinkService;
import com.imooc.api.controller.BaseController;
import com.imooc.api.controller.admin.FriendLinkControllerApi;
import com.imooc.bo.SaveFriendLinkBO;
import com.imooc.enums.ResponseStatus;
import com.imooc.mo.FriendLinkMO;
import com.imooc.result.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.Map;

/**
 * @Author Dooby Kim
 * @Date 2022/3/1 10:36 下午
 * @Version 1.0
 */
@Slf4j
@RestController
public class FriendLinkController extends BaseController implements FriendLinkControllerApi {

    @Resource
    private FriendLinkService friendLinkService;

    @Override
    public JsonResult saveOrUpdateFriendLink(@Valid SaveFriendLinkBO saveFriendLinkBO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = getErrors(result);
            return new JsonResult(ResponseStatus.FAILED, errors);
        }

        FriendLinkMO saveFriendLinkMO = new FriendLinkMO();
        BeanUtils.copyProperties(saveFriendLinkBO, saveFriendLinkMO);
        saveFriendLinkMO.setCreateTime(new Date());
        saveFriendLinkMO.setUpdateTime(new Date());

        // TODO:MongoDB 持久层操作保存记录
        friendLinkService.saveOrUpdateFriendLink(saveFriendLinkMO);

        return JsonResult.ok();
    }

    @Override
    public JsonResult getFriendLinkList() {

        return new JsonResult(ResponseStatus.SUCCESS, friendLinkService.queryAllFriendLinkList());
    }

    @Override
    public JsonResult delete(String linkId) {
        friendLinkService.delete(linkId);
        return JsonResult.ok();
    }
}

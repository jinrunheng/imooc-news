package com.imooc.api.controller.admin;

import com.imooc.bo.SaveFriendLinkBO;
import com.imooc.result.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

/**
 * @Author Dooby Kim
 * @Date 2022/3/1 10:02 下午
 * @Version 1.0
 */
@Api(value = "FriendLinkController", tags = {"用于管理员 Admin 首页友情链接"})
@RequestMapping("friendLinkMng")
public interface FriendLinkControllerApi {

    @ApiOperation(value = "新增或修改友情链接", notes = "新增或修改友情链接", httpMethod = "POST")
    @PostMapping("/saveOrUpdateFriendLink")
    JsonResult saveOrUpdateFriendLink(@RequestBody @Valid SaveFriendLinkBO saveFriendLinkBO, BindingResult result);

    @ApiOperation(value = "查询友情链接", notes = "查询友情链接", httpMethod = "POST")
    @PostMapping("/getFriendLinkList")
    JsonResult getFriendLinkList();
}

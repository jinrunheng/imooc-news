package com.imooc.user.controller;

import com.imooc.api.controller.user.UserInfoControllerApi;
import com.imooc.bo.UpdateUserInfoBO;
import com.imooc.enums.ResponseStatus;
import com.imooc.pojo.AppUser;
import com.imooc.result.JsonResult;
import com.imooc.user.service.UserService;
import com.imooc.vo.UserAccountInfoVO;
import com.imooc.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

/**
 * @Author Dooby Kim
 * @Date 2021/12/26 7:58 下午
 * @Version 1.0
 * @Description 用户账号基本信息 Controller
 */
@RestController
@Slf4j
public class UserInfoController extends BaseController implements UserInfoControllerApi {

    @Resource
    private UserService userService;

    /**
     * 通过 userId 获取用户基本信息
     * <p>
     * 账号信息与用户基本信息不同在于，账号信息是用户账号详情页面所使用的；而一些其他的页面使用到的是用户的基本信息
     * <p>
     * 流程：
     * 1. 判断 userId 是否为空，如果 userId 为空，则直接返回
     * 2. 根据 userId 获取用户
     * 3. 将用户封装成 VO（View Object）
     * 4. 返回 VO 对象
     * <p>
     * 关于 VO/BO：
     * <p>
     * BO 为 Business Object，是前端传递给后端的；VO 为 View Object，是后端传递给前端的
     *
     * @param userId
     * @return
     */
    @Override
    public JsonResult getUserInfo(String userId) {
        if (StringUtils.isBlank(userId)) {
            return new JsonResult(ResponseStatus.UN_LOGIN_ERROR);
        }
        AppUser user = userService.getUser(userId);
        UserInfoVO userInfoVO = UserInfoVO.builder().build();
        BeanUtils.copyProperties(user, userInfoVO);
        return new JsonResult(ResponseStatus.SUCCESS, userInfoVO);
    }

    /**
     * 通过 userId 获取用户账号信息
     * <p>
     * 账号信息与用户基本信息不同在于，账号信息是用户账号详情页面所使用的；而一些其他的页面使用到的是用户的基本信息
     * <p>
     * 流程：
     * 1. 判断 userId 是否为空，如果 userId 为空，则直接返回
     * 2. 根据 userId 获取用户
     * 3. 将用户封装成 VO（View Object）
     * 4. 返回 VO 对象
     * <p>
     * 关于 VO/BO：
     * <p>
     * BO 为 Business Object，是前端传递给后端的；VO 为 View Object，是后端传递给前端的
     *
     * @param userId
     * @return
     */
    @Override
    public JsonResult getAccountInfo(String userId) {
        if (StringUtils.isBlank(userId)) {
            return new JsonResult(ResponseStatus.UN_LOGIN_ERROR);
        }
        AppUser user = userService.getUser(userId);
        UserAccountInfoVO userAccountInfoVO = UserAccountInfoVO.builder().build();
        BeanUtils.copyProperties(user, userAccountInfoVO);
        return new JsonResult(ResponseStatus.SUCCESS, userAccountInfoVO);
    }

    /**
     * 通过前端传递过来的 BO 修改用户信息
     * 流程：
     * 1. 判断 BindingResult 中是否绑定了错误的验证信息(@Valid 校验)，如果有则返回
     * 2. 执行更新操作
     *
     * @param userInfoBO
     * @param result
     * @return
     */
    @Override
    public JsonResult updateUserInfo(@Valid UpdateUserInfoBO userInfoBO, BindingResult result) {
        // 判断 BindingResult 中是否绑定了错误的验证信息(@Valid 校验)，如果有则返回
        if (result.hasErrors()) {
            Map<String, String> errors = getErrors(result);
            return new JsonResult(ResponseStatus.FAILED, errors);
        }
        // 执行更新操作
        userService.updateUserInfo(userInfoBO);
        return JsonResult.ok();
    }
}

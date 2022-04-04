package com.imooc.user.service;

import com.imooc.utils.PageUtils;

import java.util.Date;

/**
 * @Author Dooby Kim
 * @Date 2022/4/1 10:00 下午
 * @Version 1.0
 */
public interface AppUserMngService {

    /**
     * 查询管理员列表
     *
     * @param nickname
     * @param status
     * @param startDate
     * @param endDate
     * @param page
     * @param pageSize
     * @return
     */
    PageUtils.PageInfoVO queryAllUserList(String nickname,
                                          Integer status,
                                          Date startDate,
                                          Date endDate,
                                          Integer page,
                                          Integer pageSize);

    /**
     * 冻结用户账号，或解除冻结状态
     *
     * @param userId
     * @param doStatus
     */
    void freezeUserOrNot(String userId, Integer doStatus);
}

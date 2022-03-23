package com.imooc.admin.service;

import com.imooc.mo.FriendLinkMO;

/**
 * @Author Dooby Kim
 * @Date 2022/3/23 10:16 下午
 * @Version 1.0
 */
public interface FriendLinkService {
    /**
     * 新增或更新友情链接
     */
    void saveOrUpdateFriendLink(FriendLinkMO friendLinkMO);
}

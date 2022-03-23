package com.imooc.admin.service;

import com.imooc.mo.FriendLinkMO;

import java.util.List;

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

    /**
     * 查询所有友情链接
     *
     * @return
     */
    List<FriendLinkMO> queryAllFriendLinkList();

    /**
     * 删除友情链接
     *
     * @param linkId
     */
    void delete(String linkId);
}

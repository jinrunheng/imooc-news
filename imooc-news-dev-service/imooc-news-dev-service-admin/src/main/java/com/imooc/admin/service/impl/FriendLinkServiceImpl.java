package com.imooc.admin.service.impl;

import com.imooc.admin.repository.FriendLinkRepository;
import com.imooc.admin.service.FriendLinkService;
import com.imooc.mo.FriendLinkMO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author Dooby Kim
 * @Date 2022/3/23 10:18 下午
 * @Version 1.0
 */
@Service
@Slf4j
public class FriendLinkServiceImpl implements FriendLinkService {

    @Resource
    private FriendLinkRepository friendLinkRepository;

    @Override
    public void saveOrUpdateFriendLink(FriendLinkMO friendLinkMO) {
        friendLinkRepository.save(friendLinkMO);
    }
}

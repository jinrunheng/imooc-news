package com.imooc.admin.repository;

import com.imooc.mo.FriendLinkMO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Dooby Kim
 * @Date 2022/3/23 10:08 下午
 * @Version 1.0
 */
@Repository
public interface FriendLinkRepository extends MongoRepository<FriendLinkMO, String> {
}

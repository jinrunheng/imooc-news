package com.imooc.mo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @Author Dooby Kim
 * @Date 2022/3/1 10:51 下午
 * @Version 1.0
 * @Description MO 为 MongoDB Object
 */
@Getter
@Setter
@ToString
@Document("FriendLink")
public class FriendLinkMO {
    @Id
    private String id;
    @Field("link_name")
    private String linkName;
    @Field("link_url")
    private String linkUrl;
    @Field("link_delete")
    private Integer isDelete;
    @Field("create_time")
    private Date createTime;
    @Field("update_time")
    private Date updateTime;
}

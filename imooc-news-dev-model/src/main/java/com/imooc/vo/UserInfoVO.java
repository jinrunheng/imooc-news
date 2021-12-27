package com.imooc.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author Dooby Kim
 * @Date 2021/12/27 11:15 上午
 * @Version 1.0
 * @Description 关于 VO 的理解：VO（View Object）代表视图对象的意思，VO 就是将对象封装为一个可以返回到前端的对象。
 */
@Getter
@Setter
@Builder
public class UserInfoVO {
    private String id;
    private String nickname;
    private String face;
    private Integer activeStatus;
}

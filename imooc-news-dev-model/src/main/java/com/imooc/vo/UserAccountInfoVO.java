package com.imooc.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author Dooby Kim
 * @Date 2021/12/26 8:16 下午
 * @Version 1.0
 * @Description 关于 VO 的理解：VO（View Object）代表视图对象的意思，VO 就是将对象封装为一个可以返回到前端的对象。
 */
@Getter
@Setter
@Builder
public class UserAccountInfoVO {
    private String id;
    private String mobile;
    private String nickname;
    private String face;
    private String realname;
    private String email;
    private Integer sex;
    private Date birthday;
    private String province;
    private String city;
    private String district;
}

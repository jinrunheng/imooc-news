package com.imooc.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author Dooby Kim
 * @Date 2022/1/10 11:02 下午
 * @Version 1.0
 * @Description 添加管理人员的 BO
 * <p>
 * 关于 BO 的理解：BO（Business Object）代表业务对象的意思，BO 就是将业务逻辑封装为一个对象（注意是业务逻辑）; 前端 -> 后端
 */
@Getter
@Setter
@ToString
public class AddNewAdminBO {
    private String username;
    private String adminName;
    private String password;
    private String confirmPassword;
    private String img64;
    private String faceId;
}

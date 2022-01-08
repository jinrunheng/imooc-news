package com.imooc.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author Dooby Kim
 * @Date 2022/1/8 11:23 下午
 * @Version 1.0
 * @Description 关于 BO 的理解：BO（Business Object）代表业务对象的意思，BO 就是将业务逻辑封装为一个对象（注意是业务逻辑）; 前端 -> 后端
 * <p>
 * AdminLoginBO 为 前端表单传递给后端 admin 管理员用户输入的用户名以及密码封装的对象
 */
@Getter
@Setter
@ToString
public class AdminLoginBO {

    private String username;
    private String password;
    private String img64;
}

package com.imooc.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author Dooby Kim
 * @Date 2021/12/24 10:51 上午
 * @Version 1.0
 * @Description 关于 BO 的理解：BO（Business Object）代表业务对象的意思，BO 就是将业务逻辑封装为一个对象（注意是业务逻辑）。
 */
@Getter
@Setter
@ToString
public class RegisterLoginBO {
    @NotBlank(message = "手机号不能为空")
    private String mobile;
    @NotNull(message = "短信验证码不能为空")
    private String smsCode;
}

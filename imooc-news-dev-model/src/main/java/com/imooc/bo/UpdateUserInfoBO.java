package com.imooc.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * @Author Dooby Kim
 * @Date 2021/12/26 8:33 下午
 * @Version 1.0
 * @Description 关于 BO 的理解：BO（Business Object）代表业务对象的意思，BO 就是将业务逻辑封装为一个对象（注意是业务逻辑）; 前端 -> 后端
 */
@Getter
@Setter
@ToString
public class UpdateUserInfoBO {
    @NotBlank(message = "用户 ID 不能为空")
    private String id;

    @NotBlank(message = "用户昵称不能为空")
    @Length(max = 12, message = "用户昵称长度不能超过 12 位")
    private String nickname;

    @NotBlank(message = "用户头像不能为空")
    private String face;

    @NotBlank(message = "用户真实姓名不能为空")
    private String realname;

    @Email
    @NotBlank(message = "用户邮件不能为空")
    private String email;

    @NotNull(message = "请选择一个性别")
    @Min(value = 0, message = "性别选择不正确")
    @Max(value = 1, message = "性别选择不正确")
    private Integer sex;

    @NotNull(message = "请选择生日日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date birthday;

    @NotBlank(message = "请选择所在省份")
    private String province;

    @NotBlank(message = "请选择所在城市")
    private String city;

    @NotBlank(message = "请选择所在区域")
    private String district;
}

package com.imooc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Dooby Kim
 * @Date 2021/12/19 3:02 下午
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum ResponseStatus {

    SUCCESS(200, true, "操作成功"),
    FAILED(500, false, "操作失败"),

    // 短信发送频率过快
    SMS_SEND_TOO_FAST_ERROR(505, false, "短信发送频率过快，请稍后再试"),
    // 验证码过期或不匹配
    SMS_CODE_ERROR(506, false, "验证码过期或不匹配，请稍后再试"),
    // 用户账号被冻结，禁止登录
    USER_STATUS_FROZEN_ERROR(507, false, "用户账号已被冻结，请联系管理员"),
    // 用户账号未激活
    USER_STATUS_INACTIVE_ERROR(508, false, "您的账号未激活，请前往[账号设置]进行激活");
    private Integer status;
    private Boolean success;
    private String msg;
}
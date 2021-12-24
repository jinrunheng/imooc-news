package com.imooc.result;

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
    SMS_CODE_ERROR(506, false, "验证码过期或不匹配，请稍后再试");

    private Integer status;
    private Boolean success;
    private String msg;
}

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

    // 用户未登录
    UN_LOGIN_ERROR(501, false, "请登录后再操作"),
    // 用户会话失效
    TICKET_INVALID_ERROR(502, false, "会话失效，请重新登录"),
    // 短信发送频率过快
    SMS_SEND_TOO_FAST_ERROR(505, false, "短信发送频率过快，请稍后再试"),
    // 验证码过期或不匹配
    SMS_CODE_ERROR(506, false, "验证码过期或不匹配，请稍后再试"),
    // 用户账号被冻结，禁止登录
    USER_STATUS_FROZEN_ERROR(507, false, "用户账号已被冻结，请联系管理员"),
    // 用户信息更新失败
    USER_UPDATE_ERROR(508, false, "用户信息更新失败，请联系管理员"),
    // 用户账号未激活
    USER_STATUS_INACTIVE_ERROR(509, false, "您的账号未激活，请前往[账号设置]进行激活"),

    // 文件上传：文件不能为空
    FILE_UPLOAD_EMPTY_ERROR(510, false, "文件不能为空，请选择一个文件"),
    // 文件上传：文件格式错误
    FILE_FORMAT_ERROR(512, false, "图片格式不支持，仅支持 PNG，JPG，JPEG");

    private Integer status;
    private Boolean success;
    private String msg;
}

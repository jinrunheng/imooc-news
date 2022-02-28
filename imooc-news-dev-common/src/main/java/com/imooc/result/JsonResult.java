package com.imooc.result;

import com.imooc.enums.ResponseStatus;
import lombok.*;

/**
 * @Author Dooby Kim
 * @Date 2021/12/19 2:53 下午
 * @Version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult {
    // 响应状态码
    private Integer status;
    // 响应消息
    private String msg;
    // 响应是否成功
    private Boolean success;
    // 响应数据
    private Object data;

    public JsonResult(ResponseStatus responseStatus) {
        this.status = responseStatus.getStatus();
        this.msg = responseStatus.getMsg();
        this.success = responseStatus.getSuccess();
    }

    public JsonResult(ResponseStatus responseStatus, Object data) {
        this(responseStatus);
        this.data = data;
    }

    public static JsonResult ok() {
        return new JsonResult(ResponseStatus.SUCCESS);
    }

    public static JsonResult error() {
        return new JsonResult(ResponseStatus.FAILED);
    }
}

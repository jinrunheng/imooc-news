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
    FAILED(500, false, "操作失败");

    private Integer status;
    private Boolean success;
    private String msg;
}

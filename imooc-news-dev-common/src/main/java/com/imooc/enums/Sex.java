package com.imooc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Dooby Kim
 * @Date 2021/12/24 2:39 下午
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum Sex {
    woman(0, "女"),
    man(1, "男"),
    secret(2, "保密");

    private Integer type;
    private String value;
}

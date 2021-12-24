package com.imooc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Dooby Kim
 * @Date 2021/12/24 2:45 下午
 * @Version 1.0
 * @Description 用户状态的枚举类，激活，未激活，冻结
 */
@Getter
@AllArgsConstructor
public enum UserStatus {

    INACTIVE(0, "未激活"),
    ACTIVE(1, "已激活"),
    FROZEN(2, "已冻结");

    private Integer type;
    private String value;
}

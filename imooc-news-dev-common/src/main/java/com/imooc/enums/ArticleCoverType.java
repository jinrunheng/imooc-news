package com.imooc.enums;

/**
 * @Author Dooby Kim
 * @Date 2022/6/16 10:42 下午
 * @Version 1.0
 * 文章封面类型
 */
public enum ArticleCoverType {
    ONE_IMAGE(1, "单图"),
    WORDS(2, "纯文字");

    public final Integer type;
    public final String value;

    ArticleCoverType(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}

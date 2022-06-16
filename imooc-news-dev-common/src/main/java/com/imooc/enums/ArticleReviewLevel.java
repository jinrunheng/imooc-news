package com.imooc.enums;

/**
 * @Author Dooby Kim
 * @Date 2022/6/16 10:43 下午
 * @Version 1.0
 * 文章自动审核结果
 */
public enum ArticleReviewLevel {
    PASS("pass", "自动审核通过"),
    BLOCK("block", "自动审核不通过"),
    REVIEW("review", "建议人工复审");

    public final String type;
    public final String value;

    ArticleReviewLevel(String type, String value) {
        this.type = type;
        this.value = value;
    }
}

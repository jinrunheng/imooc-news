package com.imooc.article.service;

import com.imooc.bo.NewArticleBO;
import com.imooc.pojo.Category;

/**
 * @Author Dooby Kim
 * @Date 2022/6/22 8:20 下午
 * @Version 1.0
 */
public interface ArticleService {

    /**
     * 创建文章
     *
     * @param newArticleBO
     * @param category
     */
    void createArticle(NewArticleBO newArticleBO, Category category);

    /**
     * 更新定时发布为即时发布
     */
    void updateAppointToPublish();
}

package com.imooc.article.service;

import com.imooc.bo.NewArticleBO;
import com.imooc.pojo.Category;

/**
 * @Author Dooby Kim
 * @Date 2022/6/22 8:20 下午
 * @Version 1.0
 */
public interface ArticleService {

    void createArticle(NewArticleBO newArticleBO, Category category);
}

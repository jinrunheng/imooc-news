package com.imooc.article.mapper;

import com.imooc.api.mapper.MyMapper;
import com.imooc.pojo.Article;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleMapperCustom extends MyMapper<Article> {

    void updateAppointToPublish();
}
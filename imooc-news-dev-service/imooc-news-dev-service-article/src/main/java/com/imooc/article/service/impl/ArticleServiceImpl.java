package com.imooc.article.service.impl;

import com.imooc.article.mapper.ArticleMapper;
import com.imooc.article.service.ArticleService;
import com.imooc.bo.NewArticleBO;
import com.imooc.enums.ArticleAppointType;
import com.imooc.enums.ArticleReviewStatus;
import com.imooc.enums.ResponseStatus;
import com.imooc.exception.MyCustomException;
import com.imooc.pojo.Article;
import com.imooc.pojo.Category;
import com.imooc.utils.RedisOperator;
import lombok.extern.slf4j.Slf4j;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author Dooby Kim
 * @Date 2022/6/22 8:23 下午
 * @Version 1.0
 */
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private Sid sid;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    public RedisOperator redisOperator;

    @Override
    @Transactional
    public void createArticle(NewArticleBO newArticleBO, Category category) {
        String articleId = sid.nextShort();

        Article article = new Article();
        BeanUtils.copyProperties(newArticleBO, article);

        article.setId(articleId);
        article.setCategoryId(category.getId());
        article.setArticleStatus(ArticleReviewStatus.REVIEWING.type);
        article.setCommentCounts(0);
        article.setReadCounts(0);
        // 0 表示否，1 表示是
        article.setIsDelete(0);
        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());
        // 如果是定时发布
        if (article.getIsAppoint() == ArticleAppointType.TIMING.type) {
            article.setPublishTime(newArticleBO.getPublishTime());
        }
        // 否则，如果是立即发布文章
        else if (article.getIsAppoint() == ArticleAppointType.IMMEDIATELY.type) {
            article.setPublishTime(new Date());
        }

        int res = articleMapper.insert(article);
        if (res != 1) {
            MyCustomException.display(ResponseStatus.ARTICLE_CREATE_ERROR);
        }
    }
}

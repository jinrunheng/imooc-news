package com.imooc.article.controller;

import com.imooc.api.controller.BaseController;
import com.imooc.api.controller.article.ArticleControllerApi;
import com.imooc.bo.NewArticleBO;
import com.imooc.enums.ArticleCoverType;
import com.imooc.enums.ResponseStatus;
import com.imooc.pojo.Category;
import com.imooc.result.JsonResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.RedisKeyUtils;
import com.imooc.utils.RedisOperator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author Dooby Kim
 * @Date 2022/6/16 10:33 下午
 * @Version 1.0
 */
@RestController
@Slf4j
public class ArticleController extends BaseController implements ArticleControllerApi {

    @Resource
    private RedisOperator redisOperator;

    /**
     * 逻辑流程：
     * <p>
     * 1. 首先判断 BindingResult 中是否有错误的验证信息，如果有则直接返回
     * 2. 判断文章封面类型
     * ---- 1. 如果文章封面类型为单图，则 newArticleBO.getArticleCover() 不能为空，应为必填项；如果为空则抛出错误
     * ---- 2. 如果文章封面类型为纯文字，则将 articleCover 设置为空
     * 3. 判断文章分类 id 是否存在
     * ---- 1. 从 redis 中获取文章分类
     * ---- 2. 将 redis 中获取的 json 转换为 List<Category> catList
     * ---- 3. 通过遍历 catList，获取每个 Category 对应的 id，将其与 newArticleBO.getCategoryId() 进行比较；如果都不相等，则抛出错误
     *
     * @param newArticleBO
     * @param result
     * @return
     */
    @Override
    public JsonResult createArticle(@Valid NewArticleBO newArticleBO, BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errors = getErrors(result);
            return new JsonResult(ResponseStatus.FAILED, errors);
        }

        // 判断文章封面类型
        if (newArticleBO.getArticleType() == ArticleCoverType.ONE_IMAGE.type) {
            if (StringUtils.isBlank(newArticleBO.getArticleCover())) {
                return new JsonResult(ResponseStatus.ARTICLE_COVER_NOT_EXIST_ERROR);
            }
        } else if (newArticleBO.getArticleType() == ArticleCoverType.WORDS.type) {
            newArticleBO.setArticleCover("");
        }

        // 判断文章分类 id 是否存在
        String allCatJson = redisOperator.get(RedisKeyUtils.REDIS_ALL_CATEGORY);
        if (StringUtils.isBlank(allCatJson)) {
            return new JsonResult(ResponseStatus.SYSTEM_OPERATION_ERROR);
        } else {
            List<Category> catList = JsonUtils.jsonToList(allCatJson, Category.class);

            Category category = null;
            for (Category c : catList) {
                if (c.getId() == newArticleBO.getCategoryId()) {
                    category = c;
                    break;
                }
            }

            if (category == null) {
                return new JsonResult(ResponseStatus.ARTICLE_CATEGORY_NOT_EXIST_ERROR);
            }

            System.out.println(newArticleBO.toString());

            return JsonResult.ok();
        }
    }
}

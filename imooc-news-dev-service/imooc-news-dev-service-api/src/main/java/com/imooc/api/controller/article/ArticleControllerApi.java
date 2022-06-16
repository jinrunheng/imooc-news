package com.imooc.api.controller.article;

import com.imooc.bo.NewArticleBO;
import com.imooc.bo.SaveCategoryBO;
import com.imooc.result.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * @Author Dooby Kim
 * @Date 2022/6/16 10:19 下午
 * @Version 1.0
 */
@Api(value = "ArticleController", tags = {"文章业务的 Controller"})
@RequestMapping("article")
public interface ArticleControllerApi {

    @PostMapping("createArticle")
    @ApiOperation(value = "用户发文", notes = "用户发文", httpMethod = "POST")
    JsonResult createArticle(@RequestBody @Valid NewArticleBO newArticleBO, BindingResult result);


}

package com.imooc.api.controller.admin;

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
 * @Date 2022/3/30 9:43 下午
 * @Version 1.0
 */
@Api(value = "CategoryMngController", tags = {"文章分类维护的 Controller"})
@RequestMapping("categoryMng")
public interface CategoryMngControllerApi {

    @PostMapping("saveOrUpdateCategory")
    @ApiOperation(value = "新增或修改分类", notes = "新增或修改分类", httpMethod = "POST")
    JsonResult saveOrUpdateCategory(@RequestBody @Valid SaveCategoryBO newCategoryBo, BindingResult result);

    @PostMapping("getCatList")
    @ApiOperation(value = "查询分类列表", notes = "查询分类列表", httpMethod = "POST")
    JsonResult getCatList();

    @GetMapping("getCats")
    @ApiOperation(value = "用户端查询分类列表", notes = "用户端查询分类列表", httpMethod = "GET")
    JsonResult getCats();

}

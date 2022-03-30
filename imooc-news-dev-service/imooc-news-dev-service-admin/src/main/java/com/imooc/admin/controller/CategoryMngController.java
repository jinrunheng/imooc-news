package com.imooc.admin.controller;

import com.imooc.admin.service.CategoryService;
import com.imooc.api.controller.BaseController;
import com.imooc.api.controller.admin.CategoryMngControllerApi;
import com.imooc.bo.SaveCategoryBO;
import com.imooc.enums.ResponseStatus;
import com.imooc.pojo.Category;
import com.imooc.result.JsonResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.RedisKeyUtils;
import com.imooc.utils.RedisOperator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author Dooby Kim
 * @Date 2022/3/30 9:41 下午
 * @Version 1.0
 */
@Slf4j
@RestController
public class CategoryMngController extends BaseController implements CategoryMngControllerApi {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisOperator redisOperator;

    /**
     * 流程：
     * <p>
     * 1. 首先判断 BindingResult 中是否有错误的验证信息，如果有则直接返回
     * 2. 判断新增的文章分类 id 是否为空，如果为空则表示新增；不为空则表示更新
     * 3. 新增
     * ---- 1. 查询新增的分类名称是否重复存在
     * ---- 2. 如果不存在则新增到数据库
     * ---- 3. 返回错误
     * 4. 更新
     * ---- 1. 查新新增的分类名称是否重复存在
     * ---- 2. 如果不存在则更新到数据库
     * ---- 3. 返回错误
     *
     * @param newCategoryBo
     * @param result
     * @return
     */
    @Override
    public JsonResult saveOrUpdateCategory(@Valid SaveCategoryBO newCategoryBo, BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errors = getErrors(result);
            return new JsonResult(ResponseStatus.FAILED, errors);
        }

        Category newCategory = new Category();
        BeanUtils.copyProperties(newCategoryBo, newCategory);

        if (newCategoryBo.getId() == null) {
            boolean isExist = categoryService.queryCatIsExist(newCategory.getName(), null);
            if (!isExist) {
                categoryService.createCategory(newCategory);
            } else {
                return new JsonResult(ResponseStatus.CATEGORY_EXIST_ERROR);
            }
        } else {
            boolean isExist = categoryService.queryCatIsExist(newCategory.getName(), newCategoryBo.getOldName());
            if (!isExist) {
                categoryService.modifyCategory(newCategory);
            } else {
                return new JsonResult(ResponseStatus.CATEGORY_EXIST_ERROR);
            }
        }
        return null;
    }

    @Override
    public JsonResult getCatList() {
        List<Category> categories = categoryService.queryCategoryList();
        return new JsonResult(ResponseStatus.SUCCESS, categories);
    }

    /**
     * 流程：
     * <p>
     * 1. 从 Redis 中查询
     * ---- 1. 如果有，则直接返回
     * ---- 2. 如果没有，则直接查询数据库，然后再放入缓存并返回
     *
     * @return
     */
    @Override
    public JsonResult getCats() {

        String allCatJson = redisOperator.get(RedisKeyUtils.REDIS_ALL_CATEGORY);

        List<Category> categoryList = null;

        if (StringUtils.isBlank(allCatJson)) {
            categoryList = categoryService.queryCategoryList();
            redisOperator.set(RedisKeyUtils.REDIS_ALL_CATEGORY, JsonUtils.objectToJson(categoryList));
        } else {
            categoryList = JsonUtils.jsonToList(allCatJson, Category.class);
        }

        return new JsonResult(ResponseStatus.SUCCESS, categoryList);

    }
}

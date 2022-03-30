package com.imooc.admin.service;

import com.imooc.pojo.Category;

import java.util.List;

/**
 * @Author Dooby Kim
 * @Date 2022/3/30 9:54 下午
 * @Version 1.0
 */
public interface CategoryService {

    void createCategory(Category category);

    void modifyCategory(Category category);

    boolean queryCatIsExist(String cateName, String oldCatName);

    List<Category> queryCategoryList();
}

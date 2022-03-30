package com.imooc.admin.service.impl;

import com.imooc.admin.mapper.CategoryMapper;
import com.imooc.admin.service.CategoryService;
import com.imooc.enums.ResponseStatus;
import com.imooc.exception.MyCustomException;
import com.imooc.pojo.Category;
import com.imooc.utils.RedisKeyUtils;
import com.imooc.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import tk.mybatis.mapper.entity.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Dooby Kim
 * @Date 2022/3/30 9:59 下午
 * @Version 1.0
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    public CategoryMapper categoryMapper;

    @Autowired
    public RedisOperator redisOperator;

    @Override
    @Transactional
    public void createCategory(Category category) {
        int result = categoryMapper.insert(category);
        if (result != 1) {
            MyCustomException.display(ResponseStatus.SYSTEM_OPERATION_ERROR);
        }

        // 直接使用 redis 删除缓存，用户端在查询时会直接查询数据哭，再将最新的数据放进 Redis 缓存
        redisOperator.delete(RedisKeyUtils.REDIS_ALL_CATEGORY);
    }

    @Override
    @Transactional
    public void modifyCategory(Category category) {
        int result = categoryMapper.updateByPrimaryKey(category);
        if (result != 1) {
            MyCustomException.display(ResponseStatus.SYSTEM_OPERATION_ERROR);
        }
        // 直接使用 redis 删除缓存，用户端在查询时会直接查询数据哭，再将最新的数据放进 Redis 缓存
        redisOperator.delete(RedisKeyUtils.REDIS_ALL_CATEGORY);
    }

    @Override
    public boolean queryCatIsExist(String cateName, String oldCatName) {
        Example example = new Example(Category.class);
        Example.Criteria catCriteria = example.createCriteria();
        catCriteria.andEqualTo("name", cateName);
        if (StringUtils.isNotBlank(oldCatName)) {
            catCriteria.andNotEqualTo("name", oldCatName);
        }

        List<Category> catList = categoryMapper.selectByExample(example);

        boolean isExist = false;
        if (catList != null && !catList.isEmpty()) {
            isExist = true;
        }

        return isExist;
    }

    @Override
    public List<Category> queryCategoryList() {
        return categoryMapper.selectAll();
    }


}

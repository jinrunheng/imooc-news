package com.imooc.mapper.my;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;


/**
 * @Author Dooby Kim
 * @Date 2021/12/20 1:23 下午
 * @Version 1.0
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}

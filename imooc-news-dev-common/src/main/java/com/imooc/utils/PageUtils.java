package com.imooc.utils;

import com.github.pagehelper.PageInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author Dooby Kim
 * @Date 2022/1/16 10:23 下午
 * @Version 1.0
 */
public class PageUtils {

    /**
     * 用于返回给前端分页的数据格式
     *
     * @Author Dooby Kim
     * @Date 2022/1/16 10:21 下午
     * @Version 1.0
     */
    @Getter
    @Setter
    @Builder
    public static class PageInfoVO {
        private int page; // 当前页数
        private long total; // 总的页数
        private long records; // 总记录数
        private List<?> rows; // 每行显示的内容
    }


    public static PageInfoVO setPageInfo(List<?> list, Integer page) {
        PageInfo<?> pageInfo = new PageInfo<>(list);

        return PageInfoVO.builder()
                .page(page)
                .rows(list)
                .records(pageInfo.getPages())
                .total(pageInfo.getTotal())
                .build();
    }
}

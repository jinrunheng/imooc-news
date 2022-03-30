package com.imooc.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Id;

/**
 * @Author Dooby Kim
 * @Date 2022/3/30 9:56 下午
 * @Version 1.0
 */
@Table(name = "category")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {
    @Id
    private Integer id;

    private String name;

    @Column(name = "tag_color")
    private String tagColor;

}

package com.imooc.bo;

import com.imooc.annotation.CheckUrl;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author Dooby Kim
 * @Date 2022/3/1 10:08 下午
 * @Version 1.0
 * <p>
 * 新增或修改 Admin 管理员首页友情链接的 BO
 * <p>
 * 关于 BO 的理解：BO（Business Object）代表业务对象的意思，BO 就是将业务逻辑封装为一个对象（注意是业务逻辑）; 前端 -> 后端
 */
@Getter
@Setter
@ToString
public class SaveFriendLinkBO {

    private String id;
    @NotBlank(message = "友情链接名不能为空")
    private String linkName;
    @NotBlank(message = "友情链接地址不能为空")
    @CheckUrl
    private String linkUrl;
    @NotNull(message = "请选择保留或删除")
    private Integer isDelete;
}

package com.imooc.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.imooc.enums.UserStatus;
import com.imooc.pojo.AppUser;
import com.imooc.user.mapper.AppUserMapper;
import com.imooc.user.service.AppUserMngService;
import com.imooc.utils.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author Dooby Kim
 * @Date 2022/4/1 10:04 下午
 * @Version 1.0
 */
@Service
public class AppUserMngServiceImpl implements AppUserMngService {

    @Resource
    private AppUserMapper appUserMapper;

    @Override
    public PageUtils.PageInfoVO queryAllUserList(String nickname, Integer status, Date startDate, Date endDate, Integer page, Integer pageSize) {

        Example example = new Example(AppUser.class);
        example.orderBy("createdTime").desc();
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(nickname)) {
            criteria.andLike("nickname", "%" + nickname + "%");
        }

        if (UserStatus.isUserStatusValid(status)) {
            criteria.andEqualTo("activeStatus", status);
        }

        if (startDate != null) {
            criteria.andGreaterThanOrEqualTo("createdTime", startDate);
        }

        if (endDate != null) {
            criteria.andLessThanOrEqualTo("createdTime", endDate);
        }

        PageHelper.startPage(page, pageSize);

        List<AppUser> list = appUserMapper.selectByExample(example);

        return PageUtils.setPageInfo(list, page);
    }
}

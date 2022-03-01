package com.imooc.utils;

import com.imooc.annotation.CheckUrl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author Dooby Kim
 * @Date 2022/3/1 10:20 下午
 * @Version 1.0
 */
public class CheckUrlValidate implements ConstraintValidator<CheckUrl, String> {

    @Override
    public boolean isValid(String url, ConstraintValidatorContext constraintValidatorContext) {
        return UrlUtils.verifyUrl(url.trim());
    }
}

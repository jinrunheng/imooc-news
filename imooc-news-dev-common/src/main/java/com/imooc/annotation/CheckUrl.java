package com.imooc.annotation;

import com.imooc.utils.CheckUrlValidate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Dooby Kim
 * @Date 2022/3/1 10:19 下午
 * @Version 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckUrlValidate.class)
public @interface CheckUrl {

    String message() default "Url 不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

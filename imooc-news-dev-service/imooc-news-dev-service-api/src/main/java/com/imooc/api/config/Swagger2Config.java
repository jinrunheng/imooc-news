package com.imooc.api.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author Dooby Kim
 * @Date 2021/12/20 3:08 下午
 * @Version 1.0
 * <p>
 * Swagger2 文档路径地址：配置项目路径/doc.html
 * 例如：http://user.imoocnews.com:8003/doc.html
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {

        Predicate<RequestHandler> userPredicate = RequestHandlerSelectors.basePackage("com.imooc.user.controller");
        Predicate<RequestHandler> filesPredicate = RequestHandlerSelectors.basePackage("com.imooc.files.controller");
        Predicate<RequestHandler> adminPredicate = RequestHandlerSelectors.basePackage("com.imooc.admin.controller");

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(Predicates.or(userPredicate, filesPredicate, adminPredicate))
                .paths(PathSelectors.any())
                .build();
    }


    /**
     * 配置在线文档的基本信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("imooc-news 接口 api")
                .contact(new Contact("Dooby Kim",
                        "https://github.com/jinrunheng",
                        "jinrunheng@foxmail.com"))
                .description("imooc-news 接口 api")
                .version("1.0")
                .build();
    }
}
package com.imooc.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @Author Dooby Kim
 * @Date 2021/12/23 2:17 下午
 * @Version 1.0
 * @Description 用于解决前端跨域问题
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        // 1. 添加 Cors 配置信息
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        // 设置是否发送 Cookie 信息
        configuration.setAllowCredentials(true);
        // 设置允许请求的方式
        configuration.addAllowedMethod("*");
        // 设置允许的 Header
        configuration.addAllowedHeader("*");
        // 2. 为 url 添加映射的路径
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**", configuration);
        // 3. 返回重新定义好的 corsSource
        return new CorsFilter(corsSource);
    }
}

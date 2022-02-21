package com.imooc.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @Author Dooby Kim
 * @Date 2022/2/21 9:15 下午
 * @Version 1.0
 */
@Configuration
public class SpringCloudConfig {

    /**
     * 基于 OKHttp3 的来配置 RestTemplate
     * @return
     */
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
}

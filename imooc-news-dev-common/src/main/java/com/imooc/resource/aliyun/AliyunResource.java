package com.imooc.resource.aliyun;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author Dooby Kim
 * @Date 2021/12/20 8:56 下午
 * @Version 1.0
 */
@Component
@PropertySource("classpath:aliyun.properties")
@ConfigurationProperties(prefix = "aliyun")
@Getter
@Setter
public class AliyunResource {
    private String accessKeyId;
    private String accessKeySecret;
}

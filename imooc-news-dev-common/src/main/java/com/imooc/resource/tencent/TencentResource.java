package com.imooc.resource.tencent;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author Dooby Kim
 * @Date 2021/12/22 10:40 上午
 * @Version 1.0
 * @Description 腾讯云相关配置
 * <p>
 * 可以通过配置文件来配置访问腾讯云资源所需要的密钥；
 * 因为该项目会上传至 Github，所以我将密钥信息写入到了本地环境变量，并从环境变量中读取，该类暂不会使用
 */

@Deprecated
@Component
@PropertySource("classpath:tencent.properties")
@ConfigurationProperties(prefix = "tencent")
@Getter
@Setter
public class TencentResource {
    private String secretId;
    private String secretKey;
}

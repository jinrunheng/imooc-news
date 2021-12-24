package com.imooc.config;

import org.n3r.idworker.Sid;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Dooby Kim
 * @Date 2021/12/24 3:31 下午
 * @Version 1.0
 * <p>
 * 在分布式的微服务应用中，我们往往会考虑可扩展性，随着数据越来越多，我们就要对数据库进行分库分表
 * 分库分表中，自增主键的可维护性差。数据库主键必须保证是全局唯一的。
 * 进而，我们可以使用 idworker
 * 依赖：
 * <dependency>
 * <groupId>com.github.bingoohuang</groupId>
 * <artifactId>idworker-client</artifactId>
 * <version>0.0.7</version>
 * </dependency>
 * idworker 是通过 Snowflake 算法来生成 id 的一种工具，可以保证全局主键唯一
 * <p>
 * 为了让容器获取到 Sid，所以，我使用 Java Config 的方式，将 Bean 注册到 Spring 上下文中
 */

@Configuration
public class SidConfiguration {

    @Bean
    public Sid sid() {
        return new Sid();
    }
}

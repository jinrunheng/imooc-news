package com.imooc.files;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Author Dooby Kim
 * @Date 2021/12/30 6:16 下午
 * @Version 1.0
 * <p>
 * 在 @SpringBootApplication 注解中使用 exclude 可以排除掉 XXXAutoConfiguration 自动装配的类
 * 因为在项目中，我们已经引入了数据源驱动的依赖（common 模块下），files 模块继承了 common 模块，但是 files 模块并没有使用数据源
 * 所以，我们需要使用 exclude 将 DataSourceAutoConfiguration.class 这个数据源自动装配类排除
 * 否则会报错：Failed to configure a DataSource
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class FilesApplication {
    public static void main(String[] args) {
        SpringApplication.run(FilesApplication.class, args);
    }
}

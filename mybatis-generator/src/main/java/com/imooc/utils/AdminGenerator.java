package com.imooc.utils;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Dooby Kim
 * @Date 2022/1/6 11:37 下午
 * @Version 1.0
 */
@Slf4j
public class AdminGenerator {

    private void generator() throws Exception {

        List<String> warnings = new ArrayList<>();
        File configFile = new File("mybatis-generator" + File.separator + "generatorConfig-admin.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }

    public static void main(String[] args) {
        try {
            new AdminGenerator().generator();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

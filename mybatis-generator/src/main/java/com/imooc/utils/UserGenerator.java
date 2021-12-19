package com.imooc.utils;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author Dooby Kim
 * @Date 2021/12/19 8:43 下午
 * @Version 1.0
 */
@Slf4j
public class UserGenerator {

    private void generator() throws Exception {

        List<String> warnings = new ArrayList<String>();
        File configFile = new File("mybatis-generator" + File.separator + "generatorConfig-user.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }

    public static void main(String[] args) {
        try {
            new UserGenerator().generator();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

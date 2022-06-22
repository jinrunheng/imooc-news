package com.imooc.article.task;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * @Author Dooby Kim
 * @Date 2022/6/22 11:20 下午
 * @Version 1.0
 * 定时发布文章（定时任务）
 */
@Configuration
@EnableScheduling
public class TaskPublishArticles {

    /**
     * 添加定时任务，
     */
    @Scheduled(cron = "0/3****?")
    private void publishArticles() {
        System.out.println("执行定时任务: " + LocalDateTime.now());
        // TODO:调用文章 service，将当前时间应该发布的定时文章的状态改为即时
    }
}

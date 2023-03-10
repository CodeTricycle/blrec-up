package com.tricycle.up;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(scanBasePackages = {"com.tricycle.up"})
@EnableScheduling //开启定时任务
public class BiliApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(BiliApplication.class, args);
    }
}

package com.wangqifan.toutiao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ToutiaoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToutiaoApplication.class, args);
    }
}

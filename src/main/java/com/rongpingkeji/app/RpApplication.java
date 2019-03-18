package com.rongpingkeji.app;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableScheduling
@EnableAsync
@Slf4j
@EnableTransactionManagement
@MapperScan("com.rongpingkeji.data.dao")
@ComponentScan("com.rongpingkeji")
public class RpApplication {
    public static void main(String[] args) {
        SpringApplication.run(RpApplication.class, args);
    }
}

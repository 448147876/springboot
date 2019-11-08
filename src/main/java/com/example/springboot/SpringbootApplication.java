package com.example.springboot;

import com.example.springboot.utils.SpringContextUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.example.springboot.mapper*")
@EnableScheduling
public class SpringbootApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpringbootApplication.class, args);
        SpringContextUtil.setApplicationContext(run);

    }

}

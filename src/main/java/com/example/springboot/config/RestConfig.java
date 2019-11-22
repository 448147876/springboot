package com.example.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    @Autowired
    RestTemplateBuilder restTemplateBuilider;

    @Bean
    public RestTemplate restTemplate() {
        //使用build()方法进行获取
        return restTemplateBuilider.build();
    }

}

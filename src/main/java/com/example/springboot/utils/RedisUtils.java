package com.example.springboot.utils;


import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.ContextLoader;

/**
 * @version :
 * @Description: redis工具类
 * @Auther: 童志杰
 * @Date: 2019/3/20
 */

public class RedisUtils {

    static RedisTemplate redisTemplate = null;

    static{
        ApplicationContext act = ContextLoader.getCurrentWebApplicationContext();
        redisTemplate =(RedisTemplate) act.getBean("redisTemplate");
    }

    public static<T> T get(String key){
        T t = (T) redisTemplate.opsForValue().get(key);
        return t;
    }




}

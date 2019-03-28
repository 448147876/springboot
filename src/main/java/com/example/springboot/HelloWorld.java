package com.example.springboot;


import com.example.springboot.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HelloWorld {

    @Autowired
    RedisTemplate redisTemplate;

//    @Autowired
//    StringRedisTemplate stringRedisTemplate;


    @RequestMapping("/hello")
    public String helloWorld() {
        return "hello world";
    }

    @RequestMapping("/redisget")
    public String redisGet() {
        return "";
    }

    @RequestMapping("/redisset")
    public String redisSet(String key, String value) {
//        Entity entity = new Entity("",1,null);
//        Map<String,String> map = new HashMap<>();
//        map.put("aaa","bbbb");
//        redisTemplate.opsForValue().set(key+")",map);
//        Object o = redisTemplate.opsForValue().get(key+")");
        Map o = RedisUtils.get(key);
        System.out.printf("sss");
//        stringRedisTemplate.opsForValue().set(key,value);
//        String s = stringRedisTemplate.opsForValue().get(key);
        return "sss";
    }

    @RequestMapping("/mybatis")
    public String mybatis(String key, String value) {

        return "sss";
    }
}

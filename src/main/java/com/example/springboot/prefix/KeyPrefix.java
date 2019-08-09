package com.example.springboot.prefix;

public interface KeyPrefix {

    /*
        获取存活时间
         */
    int expireSeconds();

    /*
    获取前缀
     */
    String getPrefix();


}

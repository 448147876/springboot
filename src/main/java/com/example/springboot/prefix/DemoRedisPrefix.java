package com.example.springboot.prefix;

public class DemoRedisPrefix extends BasePrefix {
    public DemoRedisPrefix(String prefix) {
        super(prefix);
    }

    public DemoRedisPrefix(String prefix, int expireSeconds) {
        super(prefix, expireSeconds);
    }


    public static BasePrefix demoById = new DemoRedisPrefix("demoKeyId",100);
    public static BasePrefix demoByName = new DemoRedisPrefix("demoKeyName");

}

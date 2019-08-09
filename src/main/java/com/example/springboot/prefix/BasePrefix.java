package com.example.springboot.prefix;

import com.example.springboot.utils.Constants;

public abstract class  BasePrefix implements KeyPrefix  {
    private int expireSeconds;
    private String prefix;

    public BasePrefix(String prefix) {
        this.expireSeconds=0;
        this.prefix = prefix;
    }

    public BasePrefix(String prefix,int expireSeconds) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        //对应不同的对象key前缀，比如要以用户ID标识为前缀 为key 前缀为UserKey:id+指定的key
        //这样针对不同的模块例如部门也想要以部门ID标识为前缀为key 前缀为DeptKey:id+指定的key
        //避免全部都是id+key这种形式
        String className = getClass().getSimpleName();
        return Constants.PROJECT_NAME+":"+className+":"+prefix;
    }



}

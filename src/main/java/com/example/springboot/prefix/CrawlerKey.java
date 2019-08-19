package com.example.springboot.prefix;

public class CrawlerKey extends BasePrefix   {


    public CrawlerKey(String prefix, int expireSeconds) {
        super(prefix, expireSeconds);
    }
    public CrawlerKey(String prefix) {
        super(prefix);
    }

    public static CrawlerKey orgCode = new CrawlerKey("orgCode");




}

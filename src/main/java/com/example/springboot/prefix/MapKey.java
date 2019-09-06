package com.example.springboot.prefix;

public class MapKey extends BasePrefix   {


    public MapKey(String prefix, int expireSeconds) {
        super(prefix, expireSeconds);
    }
    public MapKey(String prefix) {
        super(prefix);
    }

    public static MapKey latlngByAreaCode = new MapKey("latlngByAreaCode");





}

package com.example.springboot.prefix;

public class UserKey extends BasePrefix   {


    public UserKey(String prefix, int expireSeconds) {
        super(prefix, expireSeconds);
    }
    public UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getByID = new UserKey("id");
    public static UserKey getByINameTime= new UserKey("name",100);




}

package com.example.springboot.prefix;

public class CartKey extends BasePrefix   {


    public CartKey(String prefix, int expireSeconds) {
        super(prefix, expireSeconds);
    }
    public CartKey(String prefix) {
        super(prefix);
    }

    public static CartKey getByID = new CartKey("id");
    public static CartKey getByINameTime= new CartKey("name",100);




}

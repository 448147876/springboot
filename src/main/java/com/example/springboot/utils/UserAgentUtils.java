package com.example.springboot.utils;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

public class UserAgentUtils {

    private static List<String> list = Lists.newLinkedList();

    static {
        list.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3956.0 Safari/537.36 Edg/80.0.328.4");
        list.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36");
        list.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.87 Safari/537.36");
        list.add("Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko");
        list.add("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36");
    }

    private static Random random = new Random();

    public static String getUserAgen() {
        {
            return list.get(random.nextInt(list.size()));
        }
    }


}

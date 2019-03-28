package com.example.springboot.utils;


/**
 * @version :
 * @Description: 常量类
 * @author: 童志杰
 * @Date: 2019/3/28
 */

public class Constants {

    public static enum status{
        SUCCESS(0,"成功"),
        ERROR(-1,"失败");

        status(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        private int code;
        private String msg;
        public int getCode() {
            return code;
        }
        public String getMsg() {
            return msg;
        }
    }

}

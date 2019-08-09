package com.example.springboot.utils;


/**
 * @version :
 * @Description: 常量类
 * @author: 童志杰
 * @Date: 2019/3/28
 */

public class Constants {

    public static final String PROJECT_NAME = "yycrm";
    public static final String NUll_CHAR = "";

//    public static enum status{
//        SUCCESS(0,"成功"),
//        ERROR(-1,"失败");
//
//        status(int code, String msg) {
//            this.code = code;
//            this.msg = msg;
//        }
//
//        private int code;
//        private String msg;
//        public int getCode() {
//            return code;
//        }
//        public String getMsg() {
//            return msg;
//        }
//    }

    /**
     * http请求 状态码
     */
    public static enum httpState {
        SUCCESS(0, "成功"),
        ERROR(-1, "失败"),
        OK(200, "请求成功"),
        CREATED(201, "创建成功"),
        ACCEPTED(202, "更新成功"),
        INTERNALSERVERERROR(500, "内部错误");

        httpState(int code, String msg) {
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

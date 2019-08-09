package com.example.springboot.utils.exception;

import com.example.springboot.utils.Constants;

/**
 * @version :
 * @Description: 自定义异常处理类
 * @author: 童志杰
 * @Date: 2019/3/28
 */

public class MyRunTimeException extends RuntimeException {

    private int code;

    private String msg;

    public MyRunTimeException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public MyRunTimeException(int code) {
        this.code = code;

    }

    public MyRunTimeException(String msg) {
        this.msg = msg;
    }

    public MyRunTimeException() {
        super();
    }

    public MyRunTimeException(Constants.httpState httpState) {
        super();
        this.code = httpState.getCode();
        this.msg = httpState.getMsg();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

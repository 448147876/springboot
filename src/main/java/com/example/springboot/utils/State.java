package com.example.springboot.utils;


import java.io.Serializable;

/**
 * @version :
 * @Description: 内部处理传递类
 * @author: 童志杰
 * @Date: 2019/3/28
 */

public class State<T> implements Serializable {


    private final static State state = new State();

    private static int SUCCESS_CODE = Constants.status.SUCCESS.getCode();
    private static int ERROR_CODE = Constants.status.ERROR.getCode();

    public int code;
    public String msg;
    public T data;


    public static State SUCCESS() {
        state.setCode(SUCCESS_CODE);
        return state;
    }

    public static State SUCCESS(String msg) {
        state.setCode(SUCCESS_CODE);
        state.setMsg(msg);
        return state;
    }

    public static <T> State SUCCESS(T t) {
        state.setCode(SUCCESS_CODE);
        state.setData(t);
        return state;
    }

    public static <T> State SUCCESS(T t, String msg) {
        state.setCode(SUCCESS_CODE);
        state.setMsg(msg);
        state.setData(t);
        return state;
    }

    public static State ERROR() {
        state.setCode(ERROR_CODE);
        return state;
    }

    public static State ERROR(String msg) {
        state.setCode(ERROR_CODE);
        state.setMsg(msg);
        return state;
    }

    public static <T> State ERROR(T t, String msg) {
        state.setCode(ERROR_CODE);
        state.setMsg(msg);
        state.setData(t);
        return state;
    }

    public static State ERROR(String msg, int code) {
        state.setCode(code);
        state.setMsg(msg);
        return state;
    }

    public static State ERROR(int code) {
        state.setCode(code);
        return state;
    }

    public static State ERROR(int code, String msg) {
        state.setCode(code);
        state.setMsg(msg);
        return state;
    }

    public static <T> State ERROR(int code, T t, String msg) {
        state.setCode(code);
        state.setMsg(msg);
        state.setData(t);
        return state;
    }

    public boolean isSuccess() {
        if (code == SUCCESS_CODE) {
            return true;
        } else {
            return false;
        }
    }


    public int getCode() {
        return code;
    }

    private void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    private void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    private void setData(T data) {
        this.data = data;
    }
}

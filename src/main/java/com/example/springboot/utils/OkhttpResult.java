package com.example.springboot.utils;

public class OkhttpResult<T> {
    private int status;
    private String msg;
    private T resultObject;

    public OkhttpResult() {
    }

    public OkhttpResult(int status, String msg, T resultObject) {
        this.status = status;
        this.msg = msg;
        this.resultObject = resultObject;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResult() {
        return msg;
    }

    public void setResult(String msg) {
        this.msg = msg;
    }

    public T getResultObject() {
        return resultObject;
    }

    public void setResultObject(T resultObject) {
        this.resultObject = resultObject;
    }
}

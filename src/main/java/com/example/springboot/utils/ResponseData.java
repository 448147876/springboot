package com.example.springboot.utils;

import java.io.Serializable;

/**
  * @Description: 前端数据类
  * @author: 童志杰
  * @Date: 2019/3/28
  * @version :
  */

public class ResponseData<T> implements Serializable {

    private final static ResponseData responseData = new ResponseData();

    private static int SUCCESS_CODE= Constants.status.SUCCESS.getCode();
    private static int ERROR_CODE = Constants.status.ERROR.getCode();

    public int code;
    public String msg;
    public T data;


    public static ResponseData SUCCESS(){
        responseData.setCode(SUCCESS_CODE);
        return responseData;
    }
    public static ResponseData SUCCESS(String msg){
        responseData.setCode(SUCCESS_CODE);
        responseData.setMsg(msg);
        return responseData;
    }
    public static<T> ResponseData SUCCESS(T data){
        responseData.setCode(SUCCESS_CODE);
        responseData.setData(data);
        return responseData;
    }
    public static<T> ResponseData SUCCESS(T t,String msg){
        responseData.setCode(SUCCESS_CODE);
        responseData.setMsg(msg);
        responseData.setData(t);
        return responseData;
    }
    public static ResponseData ERROR(){
        responseData.setCode(ERROR_CODE);
        return responseData;
    }
    public static ResponseData ERROR(String msg){
        responseData.setCode(ERROR_CODE);
        responseData.setMsg(msg);
        return responseData;
    }
    public static<T> ResponseData ERROR(T t,String msg){
        responseData.setCode(ERROR_CODE);
        responseData.setMsg(msg);
        responseData.setData(t);
        return responseData;
    }
    public static ResponseData ERROR(String msg,int code){
        responseData.setCode(code);
        responseData.setMsg(msg);
        return responseData;
    }
    public static ResponseData ERROR(int code){
        responseData.setCode(code);
        return responseData;
    }
    public static ResponseData ERROR(int code,String msg){
        responseData.setCode(code);
        responseData.setMsg(msg);
        return responseData;
    }
    public static<T> ResponseData ERROR(int code,T t,String msg){
        responseData.setCode(code);
        responseData.setMsg(msg);
        responseData.setData(t);
        return responseData;
    }

    public  boolean isSuccess(){
        if(code == SUCCESS_CODE){
            return true;
        }else{
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

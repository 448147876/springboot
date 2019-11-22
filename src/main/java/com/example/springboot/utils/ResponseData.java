package com.example.springboot.utils;

import lombok.ToString;

import java.io.Serializable;

/**
  * @Description: 前端数据类
  * @author: 童志杰
  * @Date: 2019/3/28
  * @version :
  */
@ToString
public class ResponseData<T> implements Serializable {


    public static int SUCCESS_CODE= Constants.httpState.SUCCESS.getCode();
    public static int ERROR_CODE = Constants.httpState.ERROR.getCode();

    public int code;
    public String msg;
    public T data;


    public static ResponseData RESPONSE(Constants.httpState menuState){
        ResponseData responseData = new ResponseData();
        responseData.setCode(menuState.getCode());
        responseData.setMsg(menuState.getMsg());
        return responseData;
    }
    public static<T> ResponseData RESPONSE(Constants.httpState menuState,T t){
        ResponseData responseData = new ResponseData();
        responseData.setCode(menuState.getCode());
        responseData.setMsg(menuState.getMsg());
        responseData.setData(t);
        return responseData;
    }


    public static ResponseData SUCCESS(){
        ResponseData responseData = new ResponseData();
        responseData.setCode(SUCCESS_CODE);
        return responseData;
    }
    public static ResponseData SUCCESSMSG(String msg){
        ResponseData responseData = new ResponseData();
        responseData.setCode(SUCCESS_CODE);
        responseData.setMsg(msg);
        return responseData;
    }
    public static<T> ResponseData SUCCESS(T data){
        ResponseData responseData = new ResponseData();
        responseData.setCode(SUCCESS_CODE);
        responseData.setData(data);
        return responseData;
    }
    public static<T> ResponseData SUCCESS(T t, String msg){
        ResponseData responseData = new ResponseData();
        responseData.setCode(SUCCESS_CODE);
        responseData.setMsg(msg);
        responseData.setData(t);
        return responseData;
    }
    public static ResponseData ERROR(){
        ResponseData responseData = new ResponseData();
        responseData.setCode(ERROR_CODE);
        return responseData;
    }
    public static ResponseData ERRORMSG(String msg){
        ResponseData responseData = new ResponseData();
        responseData.setCode(ERROR_CODE);
        responseData.setMsg(msg);
        return responseData;
    }
    public static<T> ResponseData ERROR(T t,String msg){
        ResponseData responseData = new ResponseData();
        responseData.setCode(ERROR_CODE);
        responseData.setMsg(msg);
        responseData.setData(t);
        return responseData;
    }
    public static ResponseData ERROR(String msg,int code){
        ResponseData responseData = new ResponseData();
        responseData.setCode(code);
        responseData.setMsg(msg);
        return responseData;
    }
    public static ResponseData ERROR(int code){
        ResponseData responseData = new ResponseData();
        responseData.setCode(code);
        return responseData;
    }
    public static ResponseData ERROR(int code,String msg){
        ResponseData responseData = new ResponseData();
        responseData.setCode(code);
        responseData.setMsg(msg);
        return responseData;
    }
    public static<T> ResponseData ERROR(int code,T t,String msg){
        ResponseData responseData = new ResponseData();
        responseData.setCode(code);
        responseData.setMsg(msg);
        responseData.setData(t);
        return responseData;
    }

    public  boolean yesOrNoSuccess(){
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

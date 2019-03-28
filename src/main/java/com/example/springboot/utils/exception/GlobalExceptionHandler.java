package com.example.springboot.utils.exception;


import com.example.springboot.utils.ResponseData;
import com.example.springboot.utils.Constants;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseData exceptionHandler(Exception ex) {
        return ResponseData.ERROR(Constants.status.ERROR.getCode(), ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = MyRunTimeException.class)
    public ResponseData myExceptionHandler(MyRunTimeException ex) {
        return ResponseData.ERROR(ex.getCode(), ex.getMsg());
    }


}

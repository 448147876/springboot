package com.example.springboot.utils.exception;


import com.example.springboot.utils.Constants;
import com.example.springboot.utils.ResponseData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @version :
 * @Description: 全局异常处理类，避免错误到前端
 * @author: 童志杰
 * @Date: 2019/3/28
 */

@ControllerAdvice
public class GlobalExceptionHandler {


    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseData exceptionHandler(Exception ex) {
        return ResponseData.ERROR(Constants.httpState.ERROR.getCode(), ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = MyRunTimeException.class)
    public ResponseData myExceptionHandler(MyRunTimeException ex) {
        return ResponseData.ERROR(ex.getCode(), ex.getMsg());
    }


}

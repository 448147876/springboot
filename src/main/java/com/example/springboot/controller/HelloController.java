package com.example.springboot.controller;


import com.example.springboot.utils.ResponseData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping("/value")
    public ResponseData hello(String value){

        return  ResponseData.SUCCESS(value);
    }
}

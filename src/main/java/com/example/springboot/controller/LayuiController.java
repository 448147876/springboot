package com.example.springboot.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/layui")
public class LayuiController {




    @GetMapping("/demo1")
    public String demo1(){
        return "/layuidemo1";
    }



}

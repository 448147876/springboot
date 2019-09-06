package com.example.springboot.controller;


import com.example.springboot.service.IAreainfoService;
import com.example.springboot.utils.Constants;
import com.example.springboot.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 区域信息 前端控制器
 * </p>
 *
 * @author tzj
 * @since 2019-08-19
 */
@RestController
@RequestMapping("/areainfo")
public class AreainfoController {

    @Autowired
    IAreainfoService areainfoService;




}


package com.example.springboot.controller;


import com.example.springboot.entity.UserInfo;
import com.example.springboot.service.impl.UserInfoServiceImpl;
import com.example.springboot.utils.Constants;
import com.example.springboot.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tzj
 * @since 2019-08-09
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Autowired
    UserInfoServiceImpl userInfoService;

    @GetMapping("{id}")
    public ResponseData<UserInfo> get(@PathVariable Integer id){
        Constants.httpState success = Constants.httpState.SUCCESS;
        UserInfo byId = userInfoService.getById(id);
        return ResponseData.SUCCESS(byId);
    }



}


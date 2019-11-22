package com.example.springboot.controller;

import com.example.springboot.entity.Customer;
import com.example.springboot.service.impl.DataFromBaiduServiceImpl;
import com.example.springboot.utils.ResponseData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: 从百度获取数据
 * @Description:
 * @author: tongzhijie
 * @date: 2019/11/21
 */
@RestController
@RequestMapping("/baidu/")
public class DataFromBaiduController {

    @Autowired
    DataFromBaiduServiceImpl dataFromBaiduService;

    @RequestMapping("entInfo")
    public ResponseData<Customer> getEntInfo(String name){
        if(StringUtils.isBlank(name)){
            return ResponseData.ERRORMSG("企业名字不能为空");
        }
        ResponseData<Customer> responseData =  dataFromBaiduService.getEntInfo(name);
        return responseData;
    }



}

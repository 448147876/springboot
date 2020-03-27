package com.example.springboot.controller;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.springboot.entity.CustomerPool;
import com.example.springboot.entity.Customeruser;
import com.example.springboot.entity.CustomeruserPool;
import com.example.springboot.service.ICustomerPoolService;
import com.example.springboot.service.ICustomeruserPoolService;
import com.example.springboot.service.ICustomeruserService;
import com.example.springboot.utils.JsonUtils;
import com.example.springboot.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import com.baomidou.dynamic.datasource.annotation.DS;

/**
 * <p>
 * 客户信息 前端控制器
 * </p>
 *
 * @author tzj
 * @since 2020-03-18
 */
@RestController
@RequestMapping("/customerPool/")
public class CustomerPoolController {

    @Autowired
    ICustomerPoolService customerPoolService;

    @Autowired
    ICustomeruserService customeruserService;

    @Autowired
    ICustomeruserPoolService customeruserPoolService;


    @GetMapping("test")
    public ResponseData test1() {
        List<CustomerPool> list = customerPoolService.list();
        List<Customeruser> list1 = customeruserService.list();
        List<CustomeruserPool> list2 = customeruserPoolService.list();
        return ResponseData.SUCCESS(list2);
    }


}


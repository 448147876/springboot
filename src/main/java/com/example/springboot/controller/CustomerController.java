package com.example.springboot.controller;


import com.example.springboot.entity.CustomerImportQs;
import com.example.springboot.service.ICustomerImportQsService;
import com.example.springboot.service.ICustomerService;
import com.example.springboot.utils.ResponseData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 客户信息 前端控制器
 * </p>
 *
 * @author tzj
 * @since 2019-10-29
 */
@RestController
@RequestMapping("/customer/")
public class CustomerController {

    @Autowired
    ICustomerService customerService;

    @Autowired
    ICustomerImportQsService customerImportQsService;


    //企查查和顺企网数据导入
    @GetMapping("qs")
    public ResponseData qccAndSqwDataImport(){
        int count = 0;
        int pageSize = 100;
        boolean flag = true;
        Integer jobId = (int) (Math.random()*100*10000);
        //先处理顺企网，再处理企查查
        while (flag){
            count++;
            PageHelper.startPage(count,pageSize);
            List<CustomerImportQs> list = customerImportQsService.list();
            PageInfo<CustomerImportQs> pageInfo = new PageInfo<>(list);
            if(!pageInfo.isHasNextPage()){
                flag = false;
            }
            //获取客户列表
            List<CustomerImportQs> customerImportQsList = pageInfo.getList();
            for (CustomerImportQs customerImportQs:customerImportQsList){
                if(StringUtils.equals(customerImportQs.getSourceType(),"顺企网")){
                    customerService.handleCustomerSqw(customerImportQs,jobId);
                }else{
                    customerService.handleCustomerQcc(customerImportQs,jobId);
                }

            }
        }

        return null;
    }





}


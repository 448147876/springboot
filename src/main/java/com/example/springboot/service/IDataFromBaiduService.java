package com.example.springboot.service;

import com.example.springboot.entity.Customer;
import com.example.springboot.utils.ResponseData;

public interface IDataFromBaiduService {
    ResponseData<Customer> getEntInfo(String name);
}

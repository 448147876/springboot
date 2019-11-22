package com.example.springboot.service;

import com.example.springboot.entity.Customer;
import com.example.springboot.utils.ResponseData;

public interface IDataFromAlibabaService {
    ResponseData<Customer> getEntInfo(String name);
}

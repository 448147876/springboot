package com.example.springboot.service;

import com.example.springboot.entity.Customer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.entity.CustomerImportQs;

import java.util.List;

/**
 * <p>
 * 客户信息 服务类
 * </p>
 *
 * @author tzj
 * @since 2019-10-29
 */
public interface ICustomerService extends IService<Customer> {

    void handleCustomerSqw(CustomerImportQs customerImportQs,Integer jobId);

    void handleCustomerQcc(CustomerImportQs customerImportQs, Integer jobId);

    List<Customer> listSideNull();
}

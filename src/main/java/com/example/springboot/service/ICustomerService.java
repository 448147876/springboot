package com.example.springboot.service;

import com.example.springboot.entity.Customer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.entity.CustomerImportQs;
import com.example.springboot.utils.ResponseData;

import java.util.List;

/**
 * <p>
 * 客户信息 服务类
 * </p>
 *
 * @author tzj
 * @since 2019-11-06
 */
public interface ICustomerService extends IService<Customer> {

    List<Customer> findCustomerNotHandle();

    void getBussinessInfo(Customer customerOld, CustomerImportQs customerImportQsEach,Integer jobId);

    List<String> listOne(String sourceName);

    ResponseData<Customer> handleDataByQcc(String htmlStr,String name);

    ResponseData<Customer> saveInfoToDb(Customer customer);
}

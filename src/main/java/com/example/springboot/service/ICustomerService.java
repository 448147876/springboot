package com.example.springboot.service;

import com.example.springboot.entity.Customer;
import com.baomidou.mybatisplus.extension.service.IService;

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
}

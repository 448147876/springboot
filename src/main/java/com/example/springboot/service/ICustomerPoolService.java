package com.example.springboot.service;

import com.example.springboot.entity.CustomerPool;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 客户信息 服务类
 * </p>
 *
 * @author tzj
 * @since 2020-03-18
 */
public interface ICustomerPoolService extends IService<CustomerPool> {

    List<CustomerPool> selectAllPreforCustomer();

}

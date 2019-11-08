package com.example.springboot.mapper;

import com.example.springboot.entity.Customer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 客户信息 Mapper 接口
 * </p>
 *
 * @author tzj
 * @since 2019-11-06
 */
public interface CustomerMapper extends BaseMapper<Customer> {

    List<Customer> findCustomerNotHandle();
}

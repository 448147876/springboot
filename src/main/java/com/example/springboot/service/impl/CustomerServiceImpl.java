package com.example.springboot.service.impl;

import com.example.springboot.entity.Customer;
import com.example.springboot.mapper.CustomerMapper;
import com.example.springboot.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 客户信息 服务实现类
 * </p>
 *
 * @author tzj
 * @since 2019-11-06
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

    /**
     * 查询没有完善的客户
     * @return
     */
    @Override
    public List<Customer> findCustomerNotHandle() {
        List<Customer> list =  getBaseMapper().findCustomerNotHandle();
        return list;
    }
}

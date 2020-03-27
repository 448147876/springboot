package com.example.springboot.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.springboot.entity.CustomerPool;
import com.example.springboot.mapper.CustomerPoolMapper;
import com.example.springboot.service.ICustomerPoolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 客户信息 服务实现类
 * </p>
 *
 * @author tzj
 * @since 2020-03-18
 */
@Service
@DS("slave")
public class CustomerPoolServiceImpl extends ServiceImpl<CustomerPoolMapper, CustomerPool> implements ICustomerPoolService {

    @Override
    public List<CustomerPool> selectAllPreforCustomer() {
        List<CustomerPool> listCustomerName = getBaseMapper().selectAllPreforCustomer();
        return listCustomerName;
    }
}

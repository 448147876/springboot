package com.example.springboot.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.CustomeruserPool;
import com.example.springboot.mapper.CustomeruserPoolMapper;
import com.example.springboot.service.ICustomeruserPoolService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tzj
 * @since 2020-03-19
 */
@Service
@DS(value = "slave")
public class CustomeruserPoolServiceImpl extends ServiceImpl<CustomeruserPoolMapper, CustomeruserPool> implements ICustomeruserPoolService {

}

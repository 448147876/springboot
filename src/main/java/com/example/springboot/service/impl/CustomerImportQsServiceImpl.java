package com.example.springboot.service.impl;

import com.example.springboot.entity.CustomerImportQs;
import com.example.springboot.mapper.CustomerImportQsMapper;
import com.example.springboot.service.ICustomerImportQsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 企查查和顺企网爬取数据 服务实现类
 * </p>
 *
 * @author tzj
 * @since 2019-11-06
 */
@Service
public class CustomerImportQsServiceImpl extends ServiceImpl<CustomerImportQsMapper, CustomerImportQs> implements ICustomerImportQsService {

}

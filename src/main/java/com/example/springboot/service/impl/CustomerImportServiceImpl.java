package com.example.springboot.service.impl;

import com.example.springboot.entity.CustomerImport;
import com.example.springboot.mapper.CustomerImportMapper;
import com.example.springboot.service.ICustomerImportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tzj
 * @since 2019-11-06
 */
@Service
public class CustomerImportServiceImpl extends ServiceImpl<CustomerImportMapper, CustomerImport> implements ICustomerImportService {

}

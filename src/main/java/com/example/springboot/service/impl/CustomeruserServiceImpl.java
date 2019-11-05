package com.example.springboot.service.impl;

import com.example.springboot.entity.Customeruser;
import com.example.springboot.mapper.CustomeruserMapper;
import com.example.springboot.service.ICustomeruserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tzj
 * @since 2019-10-29
 */
@Service
public class CustomeruserServiceImpl extends ServiceImpl<CustomeruserMapper, Customeruser> implements ICustomeruserService {

}

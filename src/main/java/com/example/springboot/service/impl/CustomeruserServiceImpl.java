package com.example.springboot.service.impl;

import com.example.springboot.entity.Customeruser;
import com.example.springboot.mapper.CustomeruserMapper;
import com.example.springboot.service.ICustomeruserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tzj
 * @since 2019-11-06
 */
@Service
public class CustomeruserServiceImpl extends ServiceImpl<CustomeruserMapper, Customeruser> implements ICustomeruserService {

    @Override
    public List<Customeruser> listNotContent(Integer customerId,String realName, String mobilePhone, String phone) {
        List<Customeruser>  customeruserList= getBaseMapper().listNotContent(customerId,realName, mobilePhone, phone);
        return customeruserList;
    }
}

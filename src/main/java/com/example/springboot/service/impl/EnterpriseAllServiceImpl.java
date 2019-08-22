package com.example.springboot.service.impl;

import com.example.springboot.entity.EnterpriseAll;
import com.example.springboot.mapper.EnterpriseAllMapper;
import com.example.springboot.service.IEnterpriseAllService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 企业表 服务实现类
 * </p>
 *
 * @author tzj
 * @since 2019-08-20
 */
@Service
public class EnterpriseAllServiceImpl extends ServiceImpl<EnterpriseAllMapper, EnterpriseAll> implements IEnterpriseAllService {


    @Autowired
    EnterpriseAllMapper enterpriseAllMapper;
    @Override
    public void deleteReadyEnt() {
        enterpriseAllMapper.deleteReadyEnt();

    }

    @Override
    public void insertEntName() {
        enterpriseAllMapper.insertEntName();
    }
}

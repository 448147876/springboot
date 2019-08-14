package com.example.springboot.service.impl;

import com.example.springboot.entity.EnterpriseInfo;
import com.example.springboot.mapper.EnterpriseInfoMapper;
import com.example.springboot.service.IEnterpriseInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 企业表 服务实现类
 * </p>
 *
 * @author tzj
 * @since 2019-08-13
 */
@Service
public class EnterpriseInfoServiceImpl extends ServiceImpl<EnterpriseInfoMapper, EnterpriseInfo> implements IEnterpriseInfoService {

}

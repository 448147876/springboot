package com.example.springboot.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Logisticcompany;
import com.example.springboot.mapper.LogisticcompanyMapper;
import com.example.springboot.service.ILogisticcompanyService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 物流公司 服务实现类
 * </p>
 *
 * @author tzj
 * @since 2020-03-19
 */
@Service
@DS("slave")
public class LogisticcompanyPoolServiceImpl extends ServiceImpl<LogisticcompanyMapper, Logisticcompany> implements ILogisticcompanyService {





}

package com.example.springboot.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.springboot.entity.Logisticcompany;
import com.example.springboot.mapper.LogisticcompanyMapper;
import com.example.springboot.service.ILogisticcompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class LogisticcompanyServiceImpl extends ServiceImpl<LogisticcompanyMapper, Logisticcompany> implements ILogisticcompanyService {

}

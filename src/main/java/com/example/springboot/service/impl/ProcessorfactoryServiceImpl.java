package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Processorfactory;
import com.example.springboot.mapper.ProcessorfactoryMapper;
import com.example.springboot.service.IProcessorfactoryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 处理工厂 服务实现类
 * </p>
 *
 * @author tzj
 * @since 2020-03-19
 */
@Service
public class ProcessorfactoryServiceImpl extends ServiceImpl<ProcessorfactoryMapper, Processorfactory> implements IProcessorfactoryService {

}

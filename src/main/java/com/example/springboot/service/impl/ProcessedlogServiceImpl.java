package com.example.springboot.service.impl;

import com.example.springboot.entity.Processedlog;
import com.example.springboot.mapper.ProcessedlogMapper;
import com.example.springboot.service.IProcessedlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 危废运输表 服务实现类
 * </p>
 *
 * @author tzj
 * @since 2020-03-19
 */
@Service
public class ProcessedlogServiceImpl extends ServiceImpl<ProcessedlogMapper, Processedlog> implements IProcessedlogService {

}

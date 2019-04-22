package com.example.springboot.service.impl;

import com.example.springboot.entity.AopLog;
import com.example.springboot.mapper.AopLogMapper;
import com.example.springboot.service.IAopLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 切面日志记录 服务实现类
 * </p>
 *
 * @author 童志杰
 * @since 2019-04-02
 */
@Service
public class AopLogServiceImpl extends ServiceImpl<AopLogMapper, AopLog> implements IAopLogService {

}

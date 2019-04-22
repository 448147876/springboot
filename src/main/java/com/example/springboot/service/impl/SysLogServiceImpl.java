package com.example.springboot.service.impl;

import com.example.springboot.entity.SysLog;
import com.example.springboot.mapper.SysLogMapper;
import com.example.springboot.service.ISysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志表 服务实现类
 * </p>
 *
 * @author 童志杰
 * @since 2019-04-02
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

}

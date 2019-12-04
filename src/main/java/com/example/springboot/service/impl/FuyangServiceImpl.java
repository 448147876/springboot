package com.example.springboot.service.impl;

import com.example.springboot.entity.Fuyang;
import com.example.springboot.mapper.FuyangMapper;
import com.example.springboot.service.IFuyangService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tzj
 * @since 2019-12-03
 */
@Service
public class FuyangServiceImpl extends ServiceImpl<FuyangMapper, Fuyang> implements IFuyangService {

}

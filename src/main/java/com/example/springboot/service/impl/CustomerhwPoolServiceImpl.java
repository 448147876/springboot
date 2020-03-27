package com.example.springboot.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Customerhw;
import com.example.springboot.mapper.CustomerhwMapper;
import com.example.springboot.service.ICustomerhwService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tzj
 * @since 2020-03-22
 */
@Service
@DS("slave")
public class CustomerhwPoolServiceImpl extends ServiceImpl<CustomerhwMapper, Customerhw> implements ICustomerhwService {

}

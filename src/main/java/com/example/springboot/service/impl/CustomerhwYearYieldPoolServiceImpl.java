package com.example.springboot.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.springboot.entity.CustomerhwYearYieldPool;
import com.example.springboot.mapper.CustomerhwYearYieldPoolMapper;
import com.example.springboot.service.ICustomerhwYearYieldPoolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户年产量产废信息 服务实现类
 * </p>
 *
 * @author tzj
 * @since 2020-03-19
 */
@Service
@DS("slave")
public class CustomerhwYearYieldPoolServiceImpl extends ServiceImpl<CustomerhwYearYieldPoolMapper, CustomerhwYearYieldPool> implements ICustomerhwYearYieldPoolService {

}

package com.example.springboot.service.impl;

import com.example.springboot.entity.SysDict;
import com.example.springboot.mapper.SysDictMapper;
import com.example.springboot.service.ISysDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author 童志杰
 * @since 2019-04-02
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

}

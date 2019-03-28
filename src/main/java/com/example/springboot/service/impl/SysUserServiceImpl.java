package com.example.springboot.service.impl;

import com.example.springboot.entity.SysUser;
import com.example.springboot.mapper.SysUserMapper;
import com.example.springboot.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 童志杰
 * @since 2019-03-28
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

}

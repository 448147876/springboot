package com.example.springboot.service.impl;

import com.example.springboot.entity.Areainfo;
import com.example.springboot.mapper.AreainfoMapper;
import com.example.springboot.service.IAreainfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 区域信息 服务实现类
 * </p>
 *
 * @author tzj
 * @since 2019-09-03
 */
@Service
public class AreainfoServiceImpl extends ServiceImpl<AreainfoMapper, Areainfo> implements IAreainfoService {

}

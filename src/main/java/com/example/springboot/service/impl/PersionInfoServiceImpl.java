package com.example.springboot.service.impl;

import com.example.springboot.entity.PersionInfo;
import com.example.springboot.mapper.PersionInfoMapper;
import com.example.springboot.service.IPersionInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 人员信息 服务实现类
 * </p>
 *
 * @author tzj
 * @since 2019-08-13
 */
@Service
public class PersionInfoServiceImpl extends ServiceImpl<PersionInfoMapper, PersionInfo> implements IPersionInfoService {

}

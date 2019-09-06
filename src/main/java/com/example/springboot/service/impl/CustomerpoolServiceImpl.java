package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.dto.MapLatLngDTO;
import com.example.springboot.entity.Customerpool;
import com.example.springboot.mapper.CustomerpoolMapper;
import com.example.springboot.service.ICustomerpoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 客户信息 服务实现类
 * </p>
 *
 * @author tzj
 * @since 2019-09-02
 */
@Service
public class CustomerpoolServiceImpl extends ServiceImpl<CustomerpoolMapper, Customerpool> implements ICustomerpoolService {



    @Override
    public List<MapLatLngDTO> selectByAreaCode(String valueOf) {
        return baseMapper.selectByAreaCode(valueOf);
    }

    @Override
    public List<MapLatLngDTO> selectByName(String name) {
        return baseMapper.selectByName(name);
    }

    @Override
    public List<MapLatLngDTO> selectByDistanct(Double distanct, double centerx, double centery) {
        //度数，距离内度数的范围，缩小范围
        double limitLngLat = 1d/111d*distanct;
        List<MapLatLngDTO>  latLngDTOS =  baseMapper.selectByDistanct(centerx,centery,distanct,limitLngLat);

        return latLngDTOS;
    }

    @Override
    public List<MapLatLngDTO> selectByAll() {
        return baseMapper.selectByAll();
    }

}

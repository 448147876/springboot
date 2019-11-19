package com.example.springboot.service.impl;

import com.example.springboot.entity.Cookies;
import com.example.springboot.mapper.CookiesMapper;
import com.example.springboot.service.ICookiesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tzj
 * @since 2019-11-18
 */
@Service
public class CookiesServiceImpl extends ServiceImpl<CookiesMapper, Cookies> implements ICookiesService {

    @Override
    public List<Cookies> selectCookieOne(String sourceName) {
        return getBaseMapper().selectCookieOne(sourceName);
    }
}

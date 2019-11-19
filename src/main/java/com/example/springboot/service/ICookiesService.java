package com.example.springboot.service;

import com.example.springboot.entity.Cookies;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tzj
 * @since 2019-11-18
 */
public interface ICookiesService extends IService<Cookies> {

    List<Cookies> selectCookieOne(String sourceName);

}

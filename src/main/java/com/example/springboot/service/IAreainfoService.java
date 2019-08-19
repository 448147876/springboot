package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.entity.Areainfo;

/**
 * <p>
 * 区域信息 服务类
 * </p>
 *
 * @author tzj
 * @since 2019-08-19
 */
public interface IAreainfoService extends IService<Areainfo> {

    void areaInfoFromAlMap();

}

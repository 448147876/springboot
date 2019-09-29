package com.example.springboot.service;

import com.example.springboot.dto.MapLatLngDTO;
import com.example.springboot.entity.Customerpool;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 客户信息 服务类
 * </p>
 *
 * @author tzj
 * @since 2019-09-02
 */
public interface ICustomerpoolService extends IService<Customerpool> {

    List<MapLatLngDTO> selectByAreaCode(String valueOf);

    List<MapLatLngDTO> selectByName(String name);

    List<MapLatLngDTO> selectByDistanct(Double distanct, double centerx, double centery);

    List<MapLatLngDTO> selectByAll();

    PageInfo<MapLatLngDTO> selectByDistanctPage(Integer pageCount, double centerx, double centery);
}

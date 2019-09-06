package com.example.springboot.mapper;

import com.example.springboot.dto.MapLatLngDTO;
import com.example.springboot.entity.Customerpool;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 客户信息 Mapper 接口
 * </p>
 *
 * @author tzj
 * @since 2019-09-02
 */
public interface CustomerpoolMapper extends BaseMapper<Customerpool> {

    @Select("SELECT name,address,c.X_Side as xSide , c.Y_Side as ySide FROM customerpool c WHERE c.X_Side IS NOT NULL AND c.AreaCode like CONCAT(#{valueOf},'%')")
    List<MapLatLngDTO> selectByAreaCode(String valueOf);
    @Select("SELECT name,address,c.X_Side as xSide , c.Y_Side as ySide FROM customerpool c WHERE c.X_Side IS NOT NULL AND c.Name like CONCAT('%',#{name},'%')")
    List<MapLatLngDTO> selectByName(String name);
    @Select("SELECT name,address,c.X_Side as xSide , c.Y_Side as ySide FROM customerpool c " +
            "WHERE c.X_Side <= #{centerx}+#{limitLngLat} AND c.X_Side >= #{centerx}-#{limitLngLat} AND c.Y_Side <= #{centery}+#{limitLngLat} AND c.Y_Side >=#{centery}-#{limitLngLat} " +
            "AND (6378.137 * 2 * ASIN(SQRT(POW(SIN((RADIANS(#{centery})- RADIANS(c.Y_Side))/ 2),2 )+ COS(RADIANS(#{centery}))* COS(RADIANS(c.Y_Side))* POW(SIN((RADIANS(#{centerx})- RADIANS(c.X_Side))/ 2), 2)))) <= #{distanct}")
    List<MapLatLngDTO> selectByDistanct(double centerx, double centery, Double distanct, double limitLngLat);
    @Select("SELECT name,address,c.X_Side as xSide , c.Y_Side as ySide FROM customerpool c WHERE c.X_Side IS NOT NULL")
    List<MapLatLngDTO> selectByAll();
}

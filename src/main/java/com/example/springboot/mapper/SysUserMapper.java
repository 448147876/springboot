package com.example.springboot.mapper;

import com.example.springboot.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author 童志杰
 * @since 2019-03-28
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("select * from ${tableName} where create_date <= #{dataTIme}")
    List<Map> getTable(@Param("tableName") String tableName,@Param("dataTIme") String dataTime);
}

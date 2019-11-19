package com.example.springboot.mapper;

import com.example.springboot.entity.Cookies;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tzj
 * @since 2019-11-18
 */
public interface CookiesMapper extends BaseMapper<Cookies> {

    @Select("SELECT id,cookie,phone FROM cookies where type=#{sourceName}  ORDER BY RAND() LIMIT 1")
    List<Cookies> selectCookieOne(String sourceName);

}

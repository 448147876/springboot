package com.example.springboot.mapper;

import com.example.springboot.entity.AopLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 切面日志记录 Mapper 接口
 * </p>
 *
 * @author 童志杰
 * @since 2019-04-02
 */
@Repository
public interface AopLogMapper extends BaseMapper<AopLog> {

}

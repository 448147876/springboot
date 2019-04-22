package com.example.springboot.mapper;

import com.example.springboot.entity.ReportInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 报表 Mapper 接口
 * </p>
 *
 * @author 童志杰
 * @since 2019-04-02
 */
@Repository
public interface ReportInfoMapper extends BaseMapper<ReportInfo> {

    int countLogin(String phoneNum);
}

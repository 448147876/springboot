package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.EnterpriseAll;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

/**
 * <p>
 * 企业表 Mapper 接口
 * </p>
 *
 * @author tzj
 * @since 2019-08-20
 */
public interface EnterpriseAllMapper extends BaseMapper<EnterpriseAll> {

    @Delete("delete from enterprise_all t where EXISTS (select 1 from enterprise_info f where f.enterprise_name = t.enterprise_name )")
    void deleteReadyEnt();

    @Insert("insert into enterprise_info (enterprise_name)  select enterprise_name from enterprise_all")
    void insertEntName();
}

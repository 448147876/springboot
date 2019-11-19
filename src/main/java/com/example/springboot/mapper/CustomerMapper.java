package com.example.springboot.mapper;

import com.example.springboot.entity.Customer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 客户信息 Mapper 接口
 * </p>
 *
 * @author tzj
 * @since 2019-11-06
 */
public interface CustomerMapper extends BaseMapper<Customer> {

    List<Customer> findCustomerNotHandle();

    @Select("SELECT c.Name FROM customer c WHERE !EXISTS(SELECT 1 FROM data_crawler dc WHERE dc.name = c.Name AND dc.source = #{sourceName}) ORDER BY RAND() LIMIT 1")
    List<String> listOne(String sourceName);
}

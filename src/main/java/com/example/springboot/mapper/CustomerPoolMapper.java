package com.example.springboot.mapper;

import com.example.springboot.entity.CustomerPool;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 客户信息 Mapper 接口
 * </p>
 *
 * @author tzj
 * @since 2020-03-18
 */
public interface CustomerPoolMapper extends BaseMapper<CustomerPool> {

    @Select("SELECT  cp.* FROM customer_pool cp \n" +
            "  WHERE  cp.OrganizationCode IS NOT NULL \n" +
            "  AND cp.AreaCode IS NOT NULL \n" +
            "  AND cp.Address IS NOT NULL \n" +
            "  AND cp.MainProducts IS NOT NULL \n" +
            "  AND cp.BusinessIndustry IS NOT NULL\n" +
            "  AND cp.RegisteredCapital IS NOT NULL\n" +
            "  AND cp.LegalRepresentative IS NOT NULL\n" +
            "  AND cp.delFlag = 1\n" +
            "  AND EXISTS (SELECT 1 FROM customeruser_pool cup WHERE cup.CustomerID = cp.ID AND cup.phone IS NOT NULL OR cup.MobilePhone IS NOT null) \n" +
            "   AND EXISTS (SELECT 1 FROM customerhw_year_yield_pool cyyp WHERE cyyp.customer_id = cp.ID)")
    List<CustomerPool> selectAllPreforCustomer();
}

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

    @Select("        SELECT\n" +
            "            c.Name AS name\n" +
            "        FROM customer c\n" +
            "            WHERE c.SourceType = 104010 \n" +
            "          AND (EXISTS(SELECT cyy.id FROM customerhw_year_yield cyy WHERE cyy.customer_id = c.id AND cyy.hw_code IS NOT null))\n" +
            "            AND\n" +
            "                (\n" +
            "                \n" +
            "                 (!EXISTS(SELECT cu.id FROM customeruser cu WHERE cu.CustomerID = c.id AND cu.RealName IS NOT NULL AND ((cu.MobilePhone IS NOT NULL AND TRIM(cu.MobilePhone)   <>  '')) OR (cu.phone IS NOT NULL AND TRIM(cu.phone)  <>  '')))\n" +
            "                OR (c.Address IS  NULL OR TRIM(c.Address) = '')\n" +
            "                OR (c.CompanyDescript IS  NULL OR TRIM(c.CompanyDescript) = '')\n" +
            "                OR (c.CreateTime IS  NULL OR TRIM(c.CreateTime) = '')\n" +
            "                OR (c.AreaCode IS  NULL OR TRIM(c.AreaCode) = '')\n" +
            "                OR (c.MainProducts IS   NULL OR TRIM(c.MainProducts) = '')\n" +
            "                OR (c.BusinessIndustry IS  NULL OR TRIM(c.BusinessIndustry) ='')\n" +
            "                OR (c.RegisteredCapital IS  NULL OR TRIM(c.RegisteredCapital) = '')\n" +
            "                OR (c.LegalRepresentative IS  NULL OR TRIM(c.LegalRepresentative) = '')\n" +
            "                )\n" +
            "        GROUP BY c.ID\n" +
            "         ORDER BY RAND() LIMIT 1")
    List<String> listOne(String sourceName);

    @Select("        SELECT\n" +
            "            c.ID AS id,\n" +
            "            c.customer_code AS customerCode,\n" +
            "            c.Name AS name,\n" +
            "            c.AreaCode AS areaCode,\n" +
            "            c.customer_type as customerType\n" +
            "        FROM customer c\n" +
            "          where\n" +
            "                (!EXISTS(SELECT cyy.id FROM customerhw_year_yield cyy WHERE cyy.customer_id = c.id AND cyy.hw_code IS NOT null))\n" +
            "                OR (!EXISTS(SELECT cu.id FROM customeruser cu WHERE cu.CustomerID = c.id  AND ((cu.MobilePhone IS NOT NULL AND TRIM(cu.MobilePhone)   <>  '')) OR (cu.phone IS NOT NULL AND TRIM(cu.phone)  <>  '')))\n" +
            "                OR (c.Address IS  NULL OR TRIM(c.Address) = '')\n" +
            "                OR (c.CompanyDescript IS  NULL OR TRIM(c.CompanyDescript) = '')\n" +
            "                OR (c.CreateTime IS  NULL OR TRIM(c.CreateTime) = '')\n" +
            "                OR (c.AreaCode IS  NULL OR TRIM(c.AreaCode) = '')\n" +
            "                OR (c.MainProducts IS   NULL OR TRIM(c.MainProducts) = '')\n" +
            "                OR (c.BusinessIndustry IS  NULL OR TRIM(c.BusinessIndustry) ='')\n" +
            "                OR (c.RegisteredCapital IS  NULL OR TRIM(c.RegisteredCapital) = '')\n" +
            "                OR (c.LegalRepresentative IS  NULL OR TRIM(c.LegalRepresentative) = '')\n" +
            "        GROUP BY c.ID\n" +
            "    ")
    List<Customer> selectNotInPoolCustomer();

    List<Customer> selectAllNotPool();
}

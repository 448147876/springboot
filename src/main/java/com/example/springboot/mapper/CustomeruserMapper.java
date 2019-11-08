package com.example.springboot.mapper;

import com.example.springboot.entity.Customeruser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tzj
 * @since 2019-11-06
 */
public interface CustomeruserMapper extends BaseMapper<Customeruser> {

    List<Customeruser> listNotContent(Integer customerId, String realName, String mobilePhone, String phone);
}

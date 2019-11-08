package com.example.springboot.service;

import com.example.springboot.entity.Customeruser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tzj
 * @since 2019-11-06
 */
public interface ICustomeruserService extends IService<Customeruser> {

    List<Customeruser> listNotContent(Integer customerId,String realName, String mobilePhone, String phone);
}

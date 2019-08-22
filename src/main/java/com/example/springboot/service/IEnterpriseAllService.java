package com.example.springboot.service;

import com.example.springboot.entity.EnterpriseAll;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 企业表 服务类
 * </p>
 *
 * @author tzj
 * @since 2019-08-20
 */
public interface IEnterpriseAllService extends IService<EnterpriseAll> {

    void deleteReadyEnt();

    void insertEntName();
}

package com.example.springboot.dto;


import com.example.springboot.entity.EnterpriseInfo;
import com.example.springboot.entity.PersionInfo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 爬一个企业的信息
 * @author: Tong
 * @date: 2019/8/13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "EnterpriseInfo对象", description = "企业表")
public class OneEnterpriseCrawlerDTO implements Serializable {

    private EnterpriseInfo enterpriseInfo;
    private List<PersionInfo> persionInfoList;

}

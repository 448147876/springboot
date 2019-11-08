package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 企查查和顺企网爬取数据
 * </p>
 *
 * @author tzj
 * @since 2019-11-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="CustomerImportQs对象", description="企查查和顺企网爬取数据")
public class CustomerImportQs implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String enterpriseName;

    private String orangeCode;

    private String address;

    private String industry;

    private String enterpriseType;

    private String areaCode;

    private String registerAddress;

    private String mainProduction;

    private String production;

    private String synopsis;

    private String businessScope;

    private String registeredCapital;

    private String registerDate;

    private String webUrl;

    private String businessStatue;

    private String managementForms;

    private String legalPsnName;

    private String psnName;

    private String dept;

    private String phone;

    private String mobelPhone;

    private String email;

    private String qq;

    private String remark;

    private String contantHtml;

    private String contantStr;

    private String businessInfoHtml;

    private String businessInfoStr;

    private String qqBak;

    private String mobelPhoneBak;

    private String phoneBak;

    private String emailBak;

    private String webUrlBak;

    @ApiModelProperty(value = "来源")
    private String sourceType;

    @ApiModelProperty(value = "处理编号")
    private Integer jobId;


}

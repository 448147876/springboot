package com.example.springboot.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 企业表
 * </p>
 *
 * @author tzj
 * @since 2019-08-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EnterpriseAll对象", description="企业表")
public class EnterpriseAll implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "组织机构代码")
    private String enterpriseId;

    @ApiModelProperty(value = "企业名称")
    private String enterpriseName;

    @ApiModelProperty(value = "法定代表人")
    private String legalPsnName;

    @ApiModelProperty(value = "注册资本(万元)")
    private BigDecimal registeredCapital;

    @ApiModelProperty(value = "成立日期")
    private LocalDateTime registerDate;

    @ApiModelProperty(value = "所属行业")
    private String industryName;

    @ApiModelProperty(value = "所属行业code")
    private String industryCode;

    @ApiModelProperty(value = "登记机关")
    private String registrationOrg;

    @ApiModelProperty(value = "所属地区	")
    private String areaId;

    @ApiModelProperty(value = "所属地区名称")
    private String areaName;

    @ApiModelProperty(value = "人员规模")
    private Integer staffSize;

    @ApiModelProperty(value = "公司简介")
    private String enterpriseInfo;

    @ApiModelProperty(value = "主要经营产品")
    private String mainProduct;

    @ApiModelProperty(value = "公司地址")
    private String address;

    @ApiModelProperty(value = "关键字")
    private String keyContent;

    @ApiModelProperty(value = "官网")
    private String officialUrl;

    @ApiModelProperty(value = "来源：10：网虫抓取。20：搜索查询，30：人工录入")
    private Integer resourceType;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "逻辑删除（1，删除，0，有效）")
    private Integer delFlag;

    @ApiModelProperty(value = "备注")
    private String remarks;


}

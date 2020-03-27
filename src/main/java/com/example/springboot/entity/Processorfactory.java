package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 处理工厂
 * </p>
 *
 * @author tzj
 * @since 2020-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Processorfactory对象", description = "处理工厂")
public class Processorfactory implements Serializable {


    private static final long serialVersionUID = -5860066678073469503L;
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "处理工厂名称")
    @TableField("Name")
    private String Name;

    @TableField("CompanyType")
    private Integer CompanyType;

    @ApiModelProperty(value = "地址")
    @TableField("Address")
    private String Address;

    @TableField("BusinessLicence")
    private String BusinessLicence;

    @TableField("CompanyUrl")
    private String CompanyUrl;

    @TableField("ManageingDirector")
    private String ManageingDirector;

    @TableField("AreaID")
    private Integer AreaID;

    @TableField("Email")
    private String Email;

    @TableField("MobilePhone")
    private String MobilePhone;

    @TableField("Phone")
    private String Phone;

    @TableField("AddTime")
    private LocalDateTime AddTime;

    @TableField("UpdateTime")
    private LocalDateTime UpdateTime;

    @ApiModelProperty(value = "经度")
    @TableField("X_Side")
    private Double xSide;

    @ApiModelProperty(value = "纬度")
    @TableField("Y_Side")
    private Double ySide;

    @ApiModelProperty(value = "1、数据导入 2、网上抓取 3、人工录入")
    @TableField("SourceType")
    private Integer SourceType;

    @ApiModelProperty(value = "地区code")
    @TableField("AreaCode")
    private String AreaCode;

    @ApiModelProperty(value = "是否启用")
    private Boolean isEnable;


}

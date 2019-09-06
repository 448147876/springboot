package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 客户信息
 * </p>
 *
 * @author tzj
 * @since 2019-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Customerpool对象", description="客户信息")
public class Customerpool implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @TableField("Name")
    private String Name;

    @ApiModelProperty(value = "企业性质")
    @TableField("CompanyType")
    private Integer CompanyType;

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

    @TableField("X_Side")
    private Double xSide;

    @TableField("Y_Side")
    private Double ySide;

    private Integer adduser;

    @ApiModelProperty(value = "0初始用户，1 C级用户 2 B级用户 3 A级用户 ")
    @TableField("UserLevel")
    private Integer UserLevel;

    @TableField("KeyWord")
    private String KeyWord;

    @TableField("CompanyDescript")
    private String CompanyDescript;

    @TableField("WebSite")
    private String WebSite;

    @TableField("EmployCount")
    private Integer EmployCount;

    @TableField("CreateTime")
    private String CreateTime;

    @ApiModelProperty(value = "1、数据导入 2、网上抓取 3、人工录入")
    @TableField("SourceType")
    private Integer SourceType;

    @TableField("AreaCode")
    private String AreaCode;

    @ApiModelProperty(value = "经营模式")
    @TableField("BusinessMode")
    private String BusinessMode;

    @ApiModelProperty(value = "经营产品")
    @TableField("MainProducts")
    private String MainProducts;

    @ApiModelProperty(value = "主营行业")
    @TableField("BusinessIndustry")
    private String BusinessIndustry;

    @ApiModelProperty(value = "抓取job")
    @TableField("JobID")
    private Integer JobID;


}

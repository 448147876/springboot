package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 * 客户信息
 * </p>
 *
 * @author tzj
 * @since 2019-11-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Customer对象", description="客户信息")
@ToString
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "客户编码")
    private String customerCode;

    @TableField("Name")
    private String Name;

    @ApiModelProperty(value = "企业性质")
    @TableField("CompanyType")
    private Integer CompanyType;

    @ApiModelProperty(value = "企业地址")
    @TableField("Address")
    private String Address;

    @ApiModelProperty(value = "组织机构代码")
    @TableField("BusinessLicence")
    private String BusinessLicence;

    @ApiModelProperty(value = "公司网址")
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

    @ApiModelProperty(value = "添加时间")
    @TableField("AddTime")
    private LocalDateTime AddTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("UpdateTime")
    private LocalDateTime UpdateTime;

    @ApiModelProperty(value = "经度")
    @TableField("X_Side")
    private Double xSide;

    @ApiModelProperty(value = "纬度")
    @TableField("Y_Side")
    private Double ySide;

    @ApiModelProperty(value = "添加人员")
    private Integer adduser;

    @ApiModelProperty(value = "0初始用户，1 C级用户 2 B级用户 3 A级用户 ")
    @TableField("UserLevel")
    private Integer UserLevel;

    @ApiModelProperty(value = "关键字")
    @TableField("KeyWord")
    private String KeyWord;

    @ApiModelProperty(value = "企业简介")
    @TableField("CompanyDescript")
    private String CompanyDescript;

    @ApiModelProperty(value = "企业网址")
    @TableField("WebSite")
    private String WebSite;

    @ApiModelProperty(value = "职员人数")
    @TableField("EmployCount")
    private Integer EmployCount;

    @ApiModelProperty(value = "成立时间")
    @TableField("CreateTime")
    private String CreateTime;

    @ApiModelProperty(value = "数据来源：1、数据导入 2、网上抓取 3、人工录入")
    @TableField("SourceType")
    private Integer SourceType;

    @ApiModelProperty(value = "行政区域")
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

    @ApiModelProperty(value = "注册资金")
    @TableField("RegisteredCapital")
    private Integer RegisteredCapital;

    @ApiModelProperty(value = "人员规模")
    @TableField("StaffSize")
    private String StaffSize;

    @ApiModelProperty(value = "法人代表")
    @TableField("LegalRepresentative")
    private String LegalRepresentative;

    @ApiModelProperty(value = "统一社会信用代码")
    @TableField("OrganizationCode")
    private String OrganizationCode;

    @ApiModelProperty(value = "公司税号")
    @TableField("TaxNumber")
    private String TaxNumber;

    @ApiModelProperty(value = "是否保护")
    @TableField("ProtectOrNot")
    private Integer ProtectOrNot;

    @ApiModelProperty(value = "财务联系人")
    @TableField("FinancingPsnName")
    private String FinancingPsnName;

    @ApiModelProperty(value = "财务工作岗位")
    @TableField("FinancingPsnPost")
    private String FinancingPsnPost;

    @ApiModelProperty(value = "财务联系电话")
    @TableField("FinancingPhone")
    private String FinancingPhone;

    @ApiModelProperty(value = "认领类型（308001:已认领，308002:未认领）")
    @TableField("ClaimType")
    private Integer ClaimType;

    @ApiModelProperty(value = "认领人")
    @TableField("ClaimPsnId")
    private String ClaimPsnId;

    @ApiModelProperty(value = "认领人部门id")
    @TableField("ClaimPsnDepId")
    private Integer ClaimPsnDepId;

    @ApiModelProperty(value = "认领时间")
    @TableField("ClaimTime")
    private LocalDateTime ClaimTime;

    @ApiModelProperty(value = "入库次数")
    @TableField("PutStorageCount")
    private Integer PutStorageCount;

    @ApiModelProperty(value = "入库时间")
    @TableField("PutStorageTime")
    private LocalDateTime PutStorageTime;

    @ApiModelProperty(value = "银行账号")
    private String bankAccount;

    @ApiModelProperty(value = "开户银行")
    private String bankName;

    @ApiModelProperty(value = "年产值")
    private Integer yearOutputValue;

    @ApiModelProperty(value = "产废类型：")
    private String hwType;

    @ApiModelProperty(value = "客户标签")
    private Integer customerType;

    @ApiModelProperty(value = "首次签约时间")
    private LocalDate firstSigningDate;

    @ApiModelProperty(value = "首次接触时间")
    private LocalDate firstContactDate;

    @ApiModelProperty(value = "手工录入处理人员id")
    private Integer handleUserId;

    @ApiModelProperty(value = "到期提醒日期")
    private LocalDateTime reminderDate;

    @ApiModelProperty(value = "用户表，包含多个用户")
    @TableField(exist = false)
    private List<Customeruser> customeruserList;


}

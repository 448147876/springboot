package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
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
 * 
 * </p>
 *
 * @author tzj
 * @since 2020-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Customeruser对象", description="")
public class Customeruser implements Serializable {


    private static final long serialVersionUID = -7409978730368062827L;
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    @TableField("CustomerID")
    private Integer CustomerID;

    @ApiModelProperty(value = "真实姓名")
    @TableField("RealName")
    private String RealName;

    @ApiModelProperty(value = "性别(0女1男)")
    @TableField("Sex")
    private Integer Sex;

    @ApiModelProperty(value = "年龄")
    @TableField("Age")
    private Integer Age;

    @ApiModelProperty(value = "民族")
    private String nation;

    @ApiModelProperty(value = "职务")
    @TableField("Position")
    private String Position;

    @ApiModelProperty(value = "手机号")
    @TableField("MobilePhone")
    private String MobilePhone;

    @ApiModelProperty(value = "办公电话")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    @TableField("Email")
    private String Email;

    @ApiModelProperty(value = "QQ")
    private String qq;

    @ApiModelProperty(value = "是否用户(0否1是)")
    @TableField("IsUser")
    private Integer IsUser;

    @ApiModelProperty(value = "新增时间")
    @TableField("AddTime")
    private LocalDateTime AddTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("UpdateTime")
    private LocalDateTime UpdateTime;

    @ApiModelProperty(value = "操作用户")
    @TableField("Adduser")
    private Integer Adduser;

    @ApiModelProperty(value = "采集来源")
    @TableField("SourcType")
    private Integer SourcType;

    @ApiModelProperty(value = "抓取job")
    @TableField("JobID")
    private Integer JobID;

    @ApiModelProperty(value = "生日")
    private LocalDate birthDate;

    @ApiModelProperty(value = "籍贯")
    private String nativePlace;

    @ApiModelProperty(value = "部门")
    private String department;

    @ApiModelProperty(value = "传真")
    private String faxNumber;

    @ApiModelProperty(value = "微信")
    private String wechart;

    @ApiModelProperty(value = "是否主要联系人")
    private Integer isMainContact;

    @ApiModelProperty(value = "兴趣爱好")
    private String taste;

    @ApiModelProperty(value = "结婚纪念日")
    private LocalDate marriedDate;

    @ApiModelProperty(value = "配偶")
    private String mateName;

    @ApiModelProperty(value = "是否已婚")
    private Integer isMarried;

    @ApiModelProperty(value = "是否有效")
    private Integer delFlag;

    @ApiModelProperty(value = "是否在用")
    @TableField("Usabled")
    private Integer Usabled;

    @ApiModelProperty(value = "备注")
    private String remarks;


}

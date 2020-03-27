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
 * 物流公司
 * </p>
 *
 * @author tzj
 * @since 2020-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Logisticcompany对象", description="物流公司")
public class Logisticcompany implements Serializable {


    private static final long serialVersionUID = -2462108011237777999L;
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

    @TableField("Director")
    private String Director;

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

    @ApiModelProperty(value = "1、数据导入 2、网上抓取 3、人工录入")
    @TableField("SourceType")
    private Integer SourceType;


}

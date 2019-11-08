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
 * 
 * </p>
 *
 * @author tzj
 * @since 2019-11-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="CustomerImport对象", description="")
public class CustomerImport implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "企业名称")
    @TableField("CustomerName")
    private String CustomerName;

    @ApiModelProperty(value = "联系人姓名")
    @TableField("PsnName")
    private String PsnName;

    @ApiModelProperty(value = "手机号码")
    @TableField("PsnMobelPhone")
    private String PsnMobelPhone;

    @ApiModelProperty(value = "座机")
    @TableField("PsnPhone")
    private String PsnPhone;

    @ApiModelProperty(value = "邮箱")
    @TableField("Email")
    private String Email;

    @ApiModelProperty(value = "地址")
    @TableField("Address")
    private String Address;

    @ApiModelProperty(value = "企业性质")
    @TableField("CompanyType")
    private String CompanyType;

    @ApiModelProperty(value = "分机号")
    @TableField("ExtensionNum")
    private String ExtensionNum;

    @ApiModelProperty(value = "行业类型")
    @TableField("BusinessIndustry")
    private String BusinessIndustry;

    @ApiModelProperty(value = "经营范围")
    @TableField("MainProducts")
    private String MainProducts;

    @ApiModelProperty(value = "传真号")
    @TableField("FaxNo")
    private String FaxNo;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "创建时间")
    @TableField("CreateTime")
    private LocalDateTime CreateTime;


}

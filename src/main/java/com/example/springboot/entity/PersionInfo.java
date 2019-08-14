package com.example.springboot.entity;

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
 * 人员信息
 * </p>
 *
 * @author tzj
 * @since 2019-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="PersionInfo对象", description="人员信息")
public class PersionInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "人员名称")
    private String psnName;

    @ApiModelProperty(value = "企业id")
    private String enterpriseId;

    @ApiModelProperty(value = "企业名称")
    private String enterpriseName;

    @ApiModelProperty(value = "固定电话")
    private String phone;

    @ApiModelProperty(value = "手机号码")
    private String mobilePhone;

    @ApiModelProperty(value = "职务")
    private String psnPosition;

    @ApiModelProperty(value = "邮件")
    private String email;

    @ApiModelProperty(value = "来源：10：网虫抓取。20：搜索查询，30：人工录入")
    private Integer sourceType;

    @ApiModelProperty(value = "是否企业主要联系人：0：是，1：否")
    private Integer entMain;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "逻辑删除（1，删除，0，有效）")
    private Integer delFlag;

    @ApiModelProperty(value = "备注")
    private String remarks;


}

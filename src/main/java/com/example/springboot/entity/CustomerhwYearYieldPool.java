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
 * 客户年产量产废信息
 * </p>
 *
 * @author tzj
 * @since 2020-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="CustomerhwYearYieldPool对象", description="客户年产量产废信息")
public class CustomerhwYearYieldPool implements Serializable {


    private static final long serialVersionUID = -4065846831479537640L;
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "危废名称")
    private String hwName;

    @ApiModelProperty(value = "危废大类")
    private String hwType;

    @ApiModelProperty(value = "危废code")
    private String hwCode;

    @ApiModelProperty(value = "客户id")
    private Integer customerId;

    @ApiModelProperty(value = "年份")
    private Integer year;

    @ApiModelProperty(value = "年产量")
    private BigDecimal yearYield;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer createUser;

    private Integer updateUser;

    private String remork;

    @ApiModelProperty(value = "是否有效")
    private Integer delFlag;


}

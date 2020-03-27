package com.example.springboot.entity;

import java.math.BigDecimal;
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
 * 危废运输表
 * </p>
 *
 * @author tzj
 * @since 2020-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Processedlog对象", description="危废运输表")
public class Processedlog implements Serializable {


    private static final long serialVersionUID = 8276012858843645273L;
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @TableField("CustomerID")
    private Integer CustomerID;

    @ApiModelProperty(value = "企业性质")
    @TableField("ProcessorID")
    private Integer ProcessorID;

    @ApiModelProperty(value = "联单号")
    @TableField("LinkedNo")
    private String LinkedNo;

    @ApiModelProperty(value = "计划单号")
    @TableField("PlanNo")
    private String PlanNo;

    @TableField("ProductName")
    private String ProductName;

    @TableField("HWCode")
    private String HWCode;

    @ApiModelProperty(value = "1固态、2半固态、3液态、4气态")
    @TableField("PhysicalState")
    private Integer PhysicalState;

    private String jyfs;

    @TableField("Transfer")
    private BigDecimal Transfer;

    @TableField("Packaging")
    private String Packaging;

    @ApiModelProperty(value = "单位 1吨")
    @TableField("Unit")
    private String Unit;

    private Integer lcid;

    @TableField("Path")
    private String Path;

    @TableField("ProductionDate")
    private Integer ProductionDate;

    @TableField("RecievedDate")
    private Integer RecievedDate;

    @TableField("AddTime")
    private LocalDateTime AddTime;

    @TableField("UpdateTime")
    private LocalDateTime UpdateTime;

    @TableField("CarNo")
    private String CarNo;

    @TableField("ImportNo")
    private Long ImportNo;

    @TableField("SourceType")
    private Integer SourceType;

    @TableField("HWType")
    private String HWType;


}

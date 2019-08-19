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
 * 区域信息
 * </p>
 *
 * @author tzj
 * @since 2019-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Areainfo对象", description="区域信息")
public class Areainfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @TableField("Code")
    private String Code;

    @TableField("AreaName")
    private String AreaName;

    @TableField("ParentCode")
    private String ParentCode;

    @TableField("AreaLevel")
    private Integer AreaLevel;

    @TableField("AddTime")
    private LocalDateTime AddTime;

    @TableField("UpdateTime")
    private LocalDateTime UpdateTime;

    @ApiModelProperty(value = "全称")
    @TableField("GroupName")
    private String GroupName;

    @ApiModelProperty(value = "中心坐标")
    @TableField("Center")
    private String Center;

    @ApiModelProperty(value = "类别")
    @TableField("Level")
    private String Level;


}

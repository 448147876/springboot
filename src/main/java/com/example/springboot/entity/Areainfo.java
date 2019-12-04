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
 * 区域信息
 * </p>
 *
 * @author tzj
 * @since 2019-12-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Areainfo对象", description="区域信息")
public class Areainfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "地区编码")
    @TableField("Code")
    private String Code;

    @ApiModelProperty(value = "地区名称")
    @TableField("AreaName")
    private String AreaName;

    @ApiModelProperty(value = "父级地区编码")
    @TableField("ParentCode")
    private String ParentCode;

    @ApiModelProperty(value = "级别")
    @TableField("AreaLevel")
    private Integer AreaLevel;

    @ApiModelProperty(value = "添加时间")
    @TableField("AddTime")
    private LocalDateTime AddTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("UpdateTime")
    private LocalDateTime UpdateTime;


}

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
 * @since 2020-03-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Customerhw对象", description="")
public class Customerhw implements Serializable {


    private static final long serialVersionUID = 2077510878108051852L;
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @TableField("HWType")
    private String HWType;

    @TableField("HWName")
    private String HWName;

    @TableField("HWCode")
    private String HWCode;

    @TableField("CustomerID")
    private Integer CustomerID;

    @TableField("AddTime")
    private LocalDateTime AddTime;

    @TableField("UpdateTime")
    private LocalDateTime UpdateTime;


}

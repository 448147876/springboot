package com.example.springboot.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: 室内地图相关数据
 * @author: tzj
 * @Date: 2019/8/29
 */
@Data
@ToString
public class IndoorData implements Serializable {
    private String cpid;
    private String floor;
    private String truefloor;
}

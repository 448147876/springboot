package com.example.springboot.entity.gaode;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: 城市
 * @author: tzj
 * @Date: 2019/8/29
 */
@Data
@ToString
public class Citie implements Serializable {
    /**
     * 名称
     */
    private String name;
    /**
     * 该城市包含此关键字的个数
     */
    private Integer num;
    /**
     * 该城市的citycode
     */
    private String citycode;
    /**
     * 该城市的adcode
     */
    private String adcode;
}

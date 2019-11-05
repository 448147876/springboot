package com.example.springboot.entity.gaode;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 城市建议
 * @author: tzj
 * @Date: 2019/8/29
 */
@Data
@ToString
public class Suggestion implements Serializable {

    /**
     * 关键字
     */
    private String keywords;
    /**
     * 城市列表
     */
    private List<Citie> cities;

}

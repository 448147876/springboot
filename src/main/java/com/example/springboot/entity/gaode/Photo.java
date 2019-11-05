package com.example.springboot.entity.gaode;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: 照片相关信息
 * @author: tzj
 * @Date: 2019/8/29
 */
@Data
@ToString
public class Photo implements Serializable {
    private String titile;
    private String url;
}

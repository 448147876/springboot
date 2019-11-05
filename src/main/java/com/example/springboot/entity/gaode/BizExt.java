package com.example.springboot.entity.gaode;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: 深度信息
 * @author: tzj
 * @Date: 2019/8/29
 */
@Data
@ToString
public class BizExt implements Serializable {
    private String rating;
    private String cost;
    private String meal_ordering;
    private String seat_ordering;
    private String ticket_ordering;
    private String hotel_ordering;
}
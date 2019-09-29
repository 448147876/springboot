package com.example.springboot.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class PoiList implements Serializable {
    private Integer count;
    private Integer pageIndex;
    private Integer pageSize;
    private List<Poi> pois;
}

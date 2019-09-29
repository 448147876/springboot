package com.example.springboot.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
@Data
@ToString
public class MapResult implements Serializable {

    private String info;
    private PoiList poiList;






}

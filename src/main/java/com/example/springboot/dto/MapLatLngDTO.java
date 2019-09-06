package com.example.springboot.dto;


import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class MapLatLngDTO implements Serializable {
    private String name;
    private String address;
    private Double xSide;
    private Double ySide;



}

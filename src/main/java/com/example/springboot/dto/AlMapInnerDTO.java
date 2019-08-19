package com.example.springboot.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class AlMapInnerDTO {

    private String citycode;
    private String adcode;
    private String name;
    private String center;
    private String level;
    private List<AlMapInnerDTO> districts;
}

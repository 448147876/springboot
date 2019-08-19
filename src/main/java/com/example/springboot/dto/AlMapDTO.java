package com.example.springboot.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;


@Data
@ToString
public class AlMapDTO {

    private String status;
    private String info;
    private String infocode;
    private String count;
    private List<AlMapInnerDTO> districts;
    private Object suggestion;
}

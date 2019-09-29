package com.example.springboot.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: POI信息
 * @author: tzj
 * @Date: 2019/8/29
 */
@Data
@ToString
public class Poi implements Serializable {
    private String id;
    private String parent;
    private String name;
    private String type;
    private String typecode;
    private String biz_type;
    private String address;
    private String location;
    private String distance;
    private String tel;
    private String postcode;
    private String website;
    private String email;
    private String pcode;
    private String pname;
    private String citycode;
    private String cityname;
    private String adcode;
    private String adname;
    private String entr_location;
    private String exit_location;
    private String navi_poiid;
    private String gridcode;
    private String alias;
    private String business_area;
    private String parking_type;
    private String tag;
    private String indoor_map;
    private IndoorData indoor_data;
    private String groupbuy_num;
    private String discount_num;
    private BizExt biz_ext;
    private List<Photo> photos;

}


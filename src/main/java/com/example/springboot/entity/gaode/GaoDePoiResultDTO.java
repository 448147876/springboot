package com.example.springboot.entity.gaode;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 高德地图poi接口返回结果集
 * @to see :https://lbs.amap.com/api/webservice/guide/api/search
 * @author: tzj
 * @Date: 2019/8/29
 */
@Data
@ToString
public class GaoDePoiResultDTO implements Serializable {

    /**
     * 结果状态值，值为0或1
     */
    private Integer status;
    /**
     * 返回状态说明
     */
    private String info;
    /**
     * 搜索方案数目(最大值为1000)
     */
    private Integer count;
    /**
     * 城市建议列表
     */
    private Suggestion suggestion;
    /**
     * 搜索POI信息列表
     */
    private List<Poi> pois;


}










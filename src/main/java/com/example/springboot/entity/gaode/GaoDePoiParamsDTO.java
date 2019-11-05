package com.example.springboot.entity.gaode;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: 高德地图poi接口返回类型
 * @to see :https://lbs.amap.com/api/webservice/guide/api/search
 * @author: tzj
 * @Date: 2019/8/29
 */
@Data
@ToString
public class GaoDePoiParamsDTO implements Serializable {

    /**
     *
     * 请求服务权限标识
     */
    private String key;
    /**
     * 查询关键字
     */
    private String keywords;

    /**
     *
     * 查询POI类型
     */
    private String types;
    /**
     *
     * 查询城市
     */
    private String city;
    /**
     *
     * 仅返回指定城市数据
     */
    private Boolean citylimit;
    /**
     * 是否按照层级展示子POI数据
     */
    private Integer children;
    /**
     *
     * 每页记录数据
     */
    private Integer offset;
    /**
     * 当前页数
     */
    private Integer page;
    /**
     * 返回结果控制
     */
    private String extensions;
    /**
     *
     * 数字签名
     */
    private String sig;
    /**
     * 返回数据格式类型
     */
    private String output;
    /**
     * 回调函数
     */
    private String callback;




}

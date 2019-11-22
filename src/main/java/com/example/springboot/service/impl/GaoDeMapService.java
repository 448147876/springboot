package com.example.springboot.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.example.springboot.entity.gaode.GaoDePoiParamsDTO;
import com.example.springboot.entity.gaode.GaoDePoiResultDTO;
import com.example.springboot.utils.JsonUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Description: 高德地图 map接口
 * @author: tzj
 * @Date: 2019/8/30
 */
@Service
public class GaoDeMapService {

    private static Logger logger = LoggerFactory.getLogger(GaoDeMapService.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    RedisTemplate redisTemplate;
    /**
     * 城市类型
     */
    public static final String COMPANY_TYPE = "170200";
    public static final String LOAD_TYPE = "190100";
    public static final String GAO_DE_KEY_REDIS = "gaode.key.redis";
    /**
     * 接口地址
     */
    private static final String URL_POI_ADDRESS = "https://restapi.amap.com/v3/place/text";

    private static final List<String> GAODE_KEY_LIST = new ArrayList<String>(Arrays.asList("45813c8b4addb0b10b9e8a914861cbf1", "e9fc7e3c656fcf55cf95ded839c6f96d", "df2e96128f390ed369465afe3b1c2547", "d79528c1da67220b0b205643e62fcb1a", "8944a10b48d83d6613a7601e56b77efb"));


    /**
     * 高德地图poi获取
     *
     * @param gaoDePoiParamsDTO
     * @return
     */
    public GaoDePoiResultDTO poiSearch(GaoDePoiParamsDTO gaoDePoiParamsDTO) {
        GaoDePoiResultDTO gaoDePoiResultDTO = null;
        String gaodeKey = GAODE_KEY_LIST.get(RandomUtils.nextInt(0, GAODE_KEY_LIST.size()));
        gaoDePoiParamsDTO.setKey(gaodeKey);
        try {
            Thread.sleep(1000);
            Map<String, Object> map = JsonUtils.str2Map(JSONObject.toJSONString(gaoDePoiParamsDTO));
            String url = URL_POI_ADDRESS+"?";
            for(String key:map.keySet()){
                url+=key+"="+map.get(key)+"&";
            }
            url = url.substring(0,url.length()-1);
            String info = restTemplate.getForObject(url, String.class);

            return JSONObject.parseObject(info,GaoDePoiResultDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }


        gaoDePoiResultDTO = new GaoDePoiResultDTO();
        gaoDePoiResultDTO.setStatus(0);
        return gaoDePoiResultDTO;
    }

    /**
     * 访问次数限制前缀
     */
    private static final String RATE_LIMIT_PREFX = "webcrawler.rate.";

    /**
     * 指定key在单位时间内最大访问次数
     *
     * @param key        主键
     * @param limitTime  单位时间（秒）
     * @param limitCount 单位时间次数
     * @return true，通过；false：不通过
     */
    public boolean rateLimit(String key, int limitTime, int limitCount) {
        key = RATE_LIMIT_PREFX + key;
        Long size = redisTemplate.opsForList().size(key);
        if (size >= limitCount) {
            Long biggerTime = (Long) redisTemplate.opsForList().leftPop(key);
            redisTemplate.opsForList().rightPush(key, System.currentTimeMillis());
            if (biggerTime > System.currentTimeMillis() - limitTime * 1000) {
                return false;
            }
        } else {
            redisTemplate.opsForList().rightPush(key, System.currentTimeMillis());
        }
        return true;

    }


}

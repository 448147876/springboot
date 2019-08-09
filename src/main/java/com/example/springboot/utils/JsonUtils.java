package com.example.springboot.utils;

import com.alibaba.druid.support.spring.stat.annotation.Stat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;


/**
  * @Description: jackson 工具类封装
  * @Auther: 童志杰
  * @Date: 2019/3/22
  * @version : V1.0
  */
public class JsonUtils {

    private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);


    private final static ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtils() {
        super();
    }

    public static ObjectMapper getInstance() {

        return objectMapper;
    }

    /**
     * javaBean,list,array convert to json string
     */
    public static String bean2Str(Object obj){
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("redis",e);
            return null;
        }
    }

    /**
     * json string convert to javaBean
     */
    public static <T> T str2Bean(String jsonStr, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonStr, clazz);
        } catch (IOException e) {
            logger.error("redis",e);
            return null;
        }
    }

    /**
     * json string convert to map
     */
    public static <T> Map<String, Object> str2Map(String jsonStr) {
        try {
            return objectMapper.readValue(jsonStr, Map.class);
        } catch (IOException e) {
            logger.error("redis",e);
            return null;
        }
    }

    /**
     * json string convert to map with javaBean
     */
    public static <T> Map<String, T> str2map(String jsonStr, Class<T> clazz)  {
        Map<String, Map<String, Object>> map = null;
        try {
            map = objectMapper.readValue(jsonStr,
                    new TypeReference<Map<String, T>>() {
                    });
        } catch (IOException e) {
            logger.error("redis",e);
            return null;
        }
        Map<String, T> result = new HashMap<String, T>();
        for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            result.put(entry.getKey(), map2Bean(entry.getValue(), clazz));
        }
        return result;
    }

    /**
     * json array string convert to list with javaBean
     */
    public static <T> List<T> str2List(String jsonArrayStr, Class<T> clazz) {
        List<Map<String, Object>> list = null;
        try {
            list = objectMapper.readValue(jsonArrayStr,
                    new TypeReference<List<T>>() {
                    });
        } catch (IOException e) {
            logger.error("redis",e);
            return null;
        }
        List<T> result = new ArrayList<T>();
        for (Map<String, Object> map : list) {
            result.add(map2Bean(map, clazz));
        }
        return result;
    }

    /**
     * map convert to javaBean
     */
    public static <T> T map2Bean(Map map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }

    public static Map bean2Map(Object object)  {
        String str = bean2Str(object);
        return str2Map(str);
    }
}

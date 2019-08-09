package com.example.springboot.utils;


import com.example.springboot.prefix.BasePrefix;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.context.ContextLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version :
 * @Description: redis工具类
 * @Auther: 童志杰
 * @Date: 2019/3/20
 */

public class RedisUtils {

    static StringRedisTemplate stringRedisTemplate = null;

    static {
        stringRedisTemplate = (StringRedisTemplate) SpringContextUtil.getBean("stringRedisTemplate");
    }


    /**
     * 加1
     *
     * @param basePrefix
     * @return
     */
    public static long incr(BasePrefix basePrefix,String key) {
        synchronized (basePrefix.getPrefix()) {
            return stringRedisTemplate.opsForValue().increment(basePrefix.getPrefix()+":"+key);
        }
    }

    /**
     * 加N
     *
     * @param basePrefix
     * @return
     */
    public static long incr(BasePrefix basePrefix,String key, int i) {
        synchronized (basePrefix.getPrefix()) {
            return stringRedisTemplate.opsForValue().increment(basePrefix.getPrefix()+":"+key, i);
        }
    }

    /**
     * 减1
     *
     * @param basePrefix
     * @return
     */
    public static long decr(BasePrefix basePrefix,String key) {
        synchronized (basePrefix.getPrefix()) {
            return stringRedisTemplate.opsForValue().decrement(basePrefix.getPrefix()+":"+key);
        }
    }

    /**
     * 减N
     *
     * @param basePrefix
     * @return
     */
    public static long decr(BasePrefix basePrefix,String key, int i) {
        synchronized (basePrefix.getPrefix()) {
            return stringRedisTemplate.opsForValue().decrement(basePrefix.getPrefix()+":"+key, i);
        }
    }

    /**
     * 键值是否存在
     *
     * @param basePrefix
     * @return
     */
    public static boolean exists(BasePrefix basePrefix,String key) {
        return stringRedisTemplate.hasKey(basePrefix.getPrefix()+":"+key);
    }

    /**
     * 获取字符串
     *
     * @param basePrefix
     * @return
     */
    public static String get(BasePrefix basePrefix,String key) {
        String str = stringRedisTemplate.opsForValue().get(basePrefix.getPrefix()+":"+key);
        return str;
    }


    /**
     * 获取对象
     *
     * @param basePrefix
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T get(BasePrefix basePrefix,String key, Class<T> tClass) {
        String str = stringRedisTemplate.opsForValue().get(basePrefix.getPrefix()+":"+key);
        if (str == null || Constants.NUll_CHAR.equals(str.trim())) {
            return null;
        }
        T t = JsonUtils.str2Bean(str, tClass);
        return t;
    }

    /**
     * 保存对象
     *
     * @param basePrefix
     * @param object
     * @return
     */
    public static boolean set(BasePrefix basePrefix,String key, Object object) {
        String str = JsonUtils.bean2Str(object);
        if (str == null || Constants.NUll_CHAR.equals(str.trim())) {
            return false;
        }
        if (basePrefix.expireSeconds() != 0) {
            stringRedisTemplate.opsForValue().set(basePrefix.getPrefix()+":"+key, str);
        } else {
            stringRedisTemplate.opsForValue().set(basePrefix.getPrefix()+":"+key, str, basePrefix.expireSeconds());
        }
        return true;
    }

    /**
     * 删除缓存
     *
     * @param basePrefix
     * @return
     */
    public static boolean del(BasePrefix basePrefix,String key) {
        return stringRedisTemplate.delete(basePrefix.getPrefix()+":"+key);
    }


    /**
     * 获取hashValue
     *
     * @param key
     * @param basePrefix
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T hget(BasePrefix basePrefix,String key,String keyh, Class<T> tClass) {
        Object o = stringRedisTemplate.opsForHash().get(basePrefix.getPrefix()+":"+key,keyh );
        if (o == null) {
            return null;
        }
        return JsonUtils.str2Bean(JsonUtils.bean2Str(o), tClass);
    }

    /**
     * 获取hashValue(String)
     *
     * @param key
     * @param basePrefix
     * @return
     */
    public static String hget(BasePrefix basePrefix,String key,String keyh) {
        Object o = stringRedisTemplate.opsForHash().get(basePrefix.getPrefix()+":"+key, keyh);
        if (o == null) {
            return null;
        }
        return JsonUtils.bean2Str(o);
    }

    /**
     * 获取hashMaps(String)
     *
     * @param key
     * @return
     */
    public static Map<String, String> hgetMap(BasePrefix basePrefix,String key) {
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(basePrefix.getPrefix()+":"+key);
        if (entries == null || entries.size() == 0) {
            return null;
        }
        Map<String, String> map = new HashMap<>(entries.size());
        for (Object o : entries.keySet()) {
            map.put(JsonUtils.bean2Str(o), JsonUtils.bean2Str(entries.get(o)));
        }
        return map;
    }


    public static List<String> lgetList(BasePrefix basePrefix,String key) {
        List<Object> values = stringRedisTemplate.opsForHash().values(basePrefix.getPrefix()+":"+key);
        if (values == null || values.size() == 0) {
            return null;
        }
        List<String> list = new ArrayList<>(values.size());
        for (Object o : values) {
            list.add(JsonUtils.bean2Str(o));
        }
        return list;
    }


}

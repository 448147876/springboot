package com.example.springboot.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.dto.AlMapDTO;
import com.example.springboot.dto.AlMapInnerDTO;
import com.example.springboot.entity.Areainfo;
import com.example.springboot.mapper.AreainfoMapper;
import com.example.springboot.service.IAreainfoService;
import com.example.springboot.utils.HttpUtils;
import com.example.springboot.utils.JsonUtils;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 区域信息 服务实现类
 * </p>
 *
 * @author tzj
 * @since 2019-08-19
 */
@Service
public class AreainfoServiceImpl extends ServiceImpl<AreainfoMapper, Areainfo> implements IAreainfoService {


    @Autowired
    AreainfoMapper areainfoMapper;

    @Override
    public void areaInfoFromAlMap() {
        //省
        Map<String,Object> params = Maps.newHashMap();
        params.put("keywords","中国");
        params.put("subdistrict","1");
        params.put("key","51e3d83539aaa1c5c97dfe64af595d43");
        String request = HttpUtils.getRequest("https://restapi.amap.com/v3/config/district", params);
//        AlMapDTO alMapDTO = JsonUtils.str2Bean(request,AlMapDTO.class);
        AlMapDTO alMapDTO = JSON.parseObject(request,AlMapDTO.class);
        List<AlMapInnerDTO> districts11 = alMapDTO.getDistricts();
        Areainfo areainfo = new Areainfo();
        AlMapInnerDTO alMapInnerDTO1 = districts11.get(0);
        areainfo.setCode(alMapInnerDTO1.getAdcode());
        areainfo.setAreaName(alMapInnerDTO1.getName());
        areainfo.setAreaLevel(1);
        areainfo.setLevel(alMapInnerDTO1.getLevel());
        areainfo.setCenter(alMapInnerDTO1.getCenter());
        areainfo.setParentCode("0");
        areainfoMapper.insert(areainfo);
        List<AlMapInnerDTO> districts2 = alMapInnerDTO1.getDistricts();
        String parentCode1 =  alMapInnerDTO1.getAdcode();
        for(AlMapInnerDTO alMapInnerDTOEach : districts2){

            areainfo.setCode(alMapInnerDTOEach.getAdcode());
            areainfo.setAreaName(alMapInnerDTOEach.getName());
            areainfo.setAreaLevel(2);
            areainfo.setLevel(alMapInnerDTOEach.getLevel());
            areainfo.setCenter(alMapInnerDTOEach.getCenter());
            areainfo.setParentCode(parentCode1);
            areainfoMapper.insert(areainfo);

            get2(alMapInnerDTOEach,3,alMapInnerDTOEach.getAdcode());


        }
    }



    //市
    public void get2(AlMapInnerDTO alMapInnerDTO,Integer count,String parentCode){
        Map<String,Object> params = Maps.newHashMap();
        params.put("subdistrict","1");
        params.put("key","51e3d83539aaa1c5c97dfe64af595d43");
        params.put("keywords",alMapInnerDTO.getName());
        String request = HttpUtils.getRequest("https://restapi.amap.com/v3/config/district", params);
        AlMapDTO alMapDTO = JSON.parseObject(request,AlMapDTO.class);

        List<AlMapInnerDTO> districts1 = alMapDTO.getDistricts();
        List<AlMapInnerDTO> districts2 = districts1.get(0).getDistricts();
        for(AlMapInnerDTO alMapInnerDTOEach:districts2){
            Areainfo areainfo = new Areainfo();
            areainfo.setCode(alMapInnerDTOEach.getAdcode());
            areainfo.setAreaName(alMapInnerDTOEach.getName());
            areainfo.setAreaLevel(count);
            areainfo.setLevel(alMapInnerDTOEach.getLevel());
            areainfo.setCenter(alMapInnerDTOEach.getCenter());
            areainfo.setParentCode(parentCode);
            areainfoMapper.insert(areainfo);

            get3(alMapInnerDTOEach,4,alMapInnerDTOEach.getAdcode());


        }
    }



    //县
    public void get3(AlMapInnerDTO alMapInnerDTO,Integer count,String parentCode){
        Map<String,Object> params = Maps.newHashMap();
        params.put("subdistrict","1");
        params.put("key","51e3d83539aaa1c5c97dfe64af595d43");
        params.put("keywords",alMapInnerDTO.getName());
        String request = HttpUtils.getRequest("https://restapi.amap.com/v3/config/district", params);
        AlMapDTO alMapDTO = JSON.parseObject(request,AlMapDTO.class);

        List<AlMapInnerDTO> districts1 = alMapDTO.getDistricts();
        List<AlMapInnerDTO> districts2 = districts1.get(0).getDistricts();
        for(AlMapInnerDTO alMapInnerDTOEach:districts2){
            Areainfo areainfo = new Areainfo();
            areainfo.setCode(alMapInnerDTOEach.getAdcode());
            areainfo.setAreaName(alMapInnerDTOEach.getName());
            areainfo.setAreaLevel(count);
            areainfo.setLevel(alMapInnerDTOEach.getLevel());
            areainfo.setCenter(alMapInnerDTOEach.getCenter());
            areainfo.setParentCode(parentCode);
            areainfoMapper.insert(areainfo);

        }
    }
}

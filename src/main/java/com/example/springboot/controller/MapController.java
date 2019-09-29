package com.example.springboot.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.dto.MapLatLngDTO;
import com.example.springboot.dto.MapResult;
import com.example.springboot.dto.Poi;
import com.example.springboot.dto.PoiList;
import com.example.springboot.entity.Areainfo;
import com.example.springboot.entity.UserInfo;
import com.example.springboot.prefix.MapKey;
import com.example.springboot.service.impl.AreainfoServiceImpl;
import com.example.springboot.service.impl.CustomerpoolServiceImpl;
import com.example.springboot.utils.RedisUtils;
import com.example.springboot.utils.ResponseData;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 地图接口
 * @author: tzj
 * @Date: 2019/9/2
 */
@Controller
@RequestMapping("/map")
public class MapController {

    @Autowired
    CustomerpoolServiceImpl customerpoolService;

    @Autowired
    AreainfoServiceImpl areainfoService;



    /**
     * 初始化坐标到redis中
     */
    @GetMapping("/init")
    @ResponseBody
    public void initLatLng(String areaCode){

        QueryWrapper<Areainfo> queryWrapper = new QueryWrapper();
        queryWrapper.eq("AreaLevel",1);
        List<Areainfo> listAreaInfo = areainfoService.list(queryWrapper);

        if(StringUtils.isNotEmpty(areaCode)){
            List<MapLatLngDTO> list = customerpoolService.selectByAreaCode(areaCode);
            RedisUtils.set(MapKey.latlngByAreaCode,areaCode,list);
            return;
        }
        for(Areainfo areainfo:listAreaInfo){
            List<MapLatLngDTO> list = customerpoolService.selectByAreaCode(areainfo.getCode());
            RedisUtils.set(MapKey.latlngByAreaCode,areainfo.getCode(),list);
        }
        List<MapLatLngDTO> listAll = customerpoolService.selectByAll();

        RedisUtils.set(MapKey.latlngByAreaCode,"0",listAll);
    }

    /**
     * 根据区域获取坐标
     * @param areaCode
     * @return
     */

    @GetMapping("/{areaCode}")
    @ResponseBody
    public ResponseData getLatLng(@PathVariable String areaCode){
        if(StringUtils.isEmpty(areaCode)){
            areaCode = "11";
        }
        ArrayList arrayList = RedisUtils.get(MapKey.latlngByAreaCode, areaCode, ArrayList.class);
        return ResponseData.SUCCESS(arrayList);
    }

    /**
     * 获取省区域代码
     * @return
     */
    @GetMapping("/areaCode")
    @ResponseBody
    public ResponseData getAllAreaCode(){

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("AreaLevel",1);
        List<Areainfo> listAreaInfo = null;
        try{
            listAreaInfo = areainfoService.list(queryWrapper);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseData.SUCCESS(listAreaInfo);
    }


    /**
     * 跳转聚合页面
     * @return
     */
    @RequestMapping("/jh")
    public String mapJuHe(Model model){
        model.addAttribute("name","张三");
        return "juhe";
    }

    /**
     * 根据名称获取坐标
     * @param name
     * @return
     */

    @GetMapping("/name/{name}")
    @ResponseBody
    public ResponseData getLatLngByName(@PathVariable String name){
        List<MapLatLngDTO> list =  customerpoolService.selectByName(name);
        return ResponseData.SUCCESS(list);
    }


    private static  double centerx = 114.28737;
    private static  double centery = 31.004418;

    /**
     * 跳转到范围页
     * @return
     */
    @RequestMapping("/fw")
    public String mapFanwei(Model model){
        model.addAttribute("centerx", centerx);
        model.addAttribute("centery", centery);
        return "fanwei";
    }


    /**
     * 根据名称获取坐标
     * @param distanct
     * @return
     */

    @GetMapping("/circle/{distanct}")
    @ResponseBody
    public ResponseData getLatLngByDistanct(@PathVariable Double distanct){
        if(distanct == null){
            return ResponseData.ERROR("距离错误");
        }



        List<MapLatLngDTO> list =  customerpoolService.selectByDistanct(distanct,centerx,centery);
        return ResponseData.SUCCESS(list);
    }




    /**
     * 跳转行政分组
     * @return
     */
    @RequestMapping("/group")
    public String mapgroup(Model model){
//        model.addAttribute("centerx", centerx);
//        model.addAttribute("centery", centery);
        return "group";
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseData getLatLngall(){
        ArrayList arrayList = RedisUtils.get(MapKey.latlngByAreaCode, "0", ArrayList.class);
        if(arrayList == null){
            arrayList = new ArrayList();
        }
        return ResponseData.SUCCESS(arrayList);
    }

    /**
     * 跳转行政分组
     * @return
     */
    @RequestMapping("/group2")
    public String mapgroup2(Model model){
//        model.addAttribute("centerx", centerx);
//        model.addAttribute("centery", centery);
        return "group2";
    }

    public static void main(String[] args) {
        UserInfo u = new UserInfo();
        u.setAge(10);
        u.setName("张三");
        UserInfo u2 = new UserInfo();
        u2.setAge(12);
        u2.setName("张三2");
        List<UserInfo> list = Lists.newLinkedList();
        list.add(u);
        list.add(u2);
        System.out.println(JSONObject.toJSONString(list));
        System.out.println(System.currentTimeMillis());
    }









    /**
     * 搜索
     * @return
     */
    @RequestMapping("/select")
    public String mapselect(Model model){




//        model.addAttribute("centerx", centerx);
//        model.addAttribute("centery", centery);
        return "select";
    }


    /**
     * 获取指定点附近数量
     * @return
     */
    @GetMapping("/page")
    @ResponseBody
    public MapResult getLatLngByPage(MapLatLngDTO mapLatLngDTO,Integer pageCount){
        PageInfo<MapLatLngDTO> pageInfo = customerpoolService.selectByDistanctPage(pageCount, mapLatLngDTO.getXSide(), mapLatLngDTO.getYSide());
        MapResult mapResult = new MapResult();
        mapResult.setInfo("ok");
        PoiList poiList = new PoiList();
        poiList.setCount((int) pageInfo.getTotal());
        poiList.setPageIndex(pageCount);
        poiList.setPageSize(10);
        List<Poi> list = Lists.newLinkedList();
        for(MapLatLngDTO mapLatLngDTOEach:pageInfo.getList()){
            Poi poi = new Poi();
            poi.setAddress(mapLatLngDTOEach.getAddress());
            poi.setName(mapLatLngDTOEach.getName());
            poi.setLocation("lng:"+mapLatLngDTOEach.getXSide()+",lat:"+mapLatLngDTOEach.getYSide());
            list.add(poi);
        }
        poiList.setPois(list);
        mapResult.setPoiList(poiList);
        return mapResult;
    }


}

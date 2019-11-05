package com.example.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.Customer;
import com.example.springboot.entity.EnterpriseDTO;
import com.example.springboot.entity.gaode.GaoDePoiParamsDTO;
import com.example.springboot.entity.gaode.GaoDePoiResultDTO;
import com.example.springboot.service.GaoDeMapService;
import com.example.springboot.service.ICustomerService;
import com.example.springboot.service.WebCrawlerService;
import com.example.springboot.utils.ResponseData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.example.springboot.service.GaoDeMapService.COMPANY_TYPE;

@RestController
@RequestMapping("/web/")
public class WebPaChongController {


    @Autowired
    WebCrawlerService webCrawlerService;

    @Autowired
    ICustomerService customerService;

    @Autowired
    GaoDeMapService gaoDeMapService;


    /**
     * 从顺企网上爬取联系人和企业信息
     * @param enterpriseName
     * @return
     */
    @GetMapping("crawlerFrom114")
    public ResponseData crawlerFrom114(String enterpriseName){

        if (StringUtils.isEmpty(enterpriseName)){
            return ResponseData.ERROR("企业名字不能为空!");
        }
        ResponseData<EnterpriseDTO> responseData = webCrawlerService.crawlerFrom114(enterpriseName.replaceAll(" ",""));
        return responseData;
    }

    @GetMapping("crawlerFromGaoDe")
    public ResponseData<EnterpriseDTO> crawlerFromGaoDe(String enterpriseName){
        EnterpriseDTO enterpriseDTO = new EnterpriseDTO();
        enterpriseDTO.setEnterpriseName(enterpriseName);
        GaoDePoiParamsDTO gaoDePoiParamsDTO = new GaoDePoiParamsDTO();
        gaoDePoiParamsDTO.setOffset(20);
        gaoDePoiParamsDTO.setPage(1);
        gaoDePoiParamsDTO.setTypes(COMPANY_TYPE);
        gaoDePoiParamsDTO.setKeywords(enterpriseName);
        GaoDePoiResultDTO gaoDePoiResultDTO = gaoDeMapService.poiSearch(gaoDePoiParamsDTO);
        if (new Integer(1).equals(gaoDePoiResultDTO.getStatus())) {
            //获得坐标信息
            if (gaoDePoiResultDTO.getPois() != null && gaoDePoiResultDTO.getPois().size() > 0) {
                String location = gaoDePoiResultDTO.getPois().get(0).getLocation();
                if (StringUtils.isNotEmpty(location)) {
                    String lng = location.split(",")[0];
                    String lat = location.split(",")[1];
                    enterpriseDTO.setLat(Double.parseDouble(lat));
                    enterpriseDTO.setLon(Double.parseDouble(lng));
                }
            }
        }else{
            System.out.println(gaoDePoiResultDTO.toString());
        }
        return ResponseData.SUCCESS(enterpriseDTO);
    }
    @GetMapping("crawlerFromGaoDeAll")
    public ResponseData crawlerFromGaoDeAll(){
        boolean flag = true;
        int count = 0;
        while (flag){
            count++;
            PageHelper.startPage(count,100);
            List<Customer> list = customerService.listSideNull();
            PageInfo<Customer> pageInfo = new PageInfo(list);
            if(!pageInfo.isHasNextPage()){
                flag  =false;
            }
            List<Customer> customerList = Lists.newArrayList();
            for(Customer customer:pageInfo.getList()){
                if(customer.getXSide() != null){
                    continue;
                }
                ResponseData<EnterpriseDTO> responseData = this.crawlerFromGaoDe(customer.getName());
                EnterpriseDTO data = responseData.getData();
                if(data == null || data.getLat() == null){
                    continue;
                }else{
                    customer.setXSide(data.getLon());
                    customer.setYSide(data.getLat());
                    customerList.add(customer);
                }
            }
            if(customerList.size()>0){
                customerService.updateBatchById(customerList);
            }
        }
        return ResponseData.SUCCESS("enterpriseDTO");
    }

    @GetMapping("crawlerFromQcc")
    public ResponseData crawlerFromQcc(String enterpriseName){
        return null;
    }

    @GetMapping("crawlerFromQxb")
    public ResponseData crawlerFromQxb(String enterpriseName){
        if (StringUtils.isEmpty(enterpriseName)){
            return ResponseData.ERROR("企业名字不能为空!");
        }
        ResponseData<EnterpriseDTO> responseData = webCrawlerService.crawlerFromQxb(enterpriseName.replaceAll(" ",""));
        return responseData;
    }


}

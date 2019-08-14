package com.example.springboot.controller;

import com.example.springboot.dto.OneEnterpriseCrawlerDTO;
import com.example.springboot.service.PullOneService;
import com.example.springboot.utils.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 单个企业信息和人员信息的获取
 *
 * @author: Tong
 * @date:  2019/8/13
 */
@RestController
@RequestMapping("/one")
@Api(tags = "网络爬虫，单条件获取")
public class PullOneController {


    @Autowired
    PullOneService pullOneService;


    @GetMapping("/oneEntByNameForm11467")
    @ApiOperation(value = "根据企业名称获取企业信息", notes = "根据企业名称获取企业信息")
    public ResponseData<OneEnterpriseCrawlerDTO> getOneEntByName(String enterpriseName){
        ResponseData<OneEnterpriseCrawlerDTO> responseData = pullOneService.getOneEntByName(enterpriseName);
        return responseData;
    }




}

package com.example.springboot.controller;

import com.example.springboot.service.CrawlerByCodeService;
import com.example.springboot.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 遍历获取企业
 *
 * @author: Tong
 * @date:  2019/8/16
 */
@RestController
@RequestMapping("/crawlerbycode")
public class CrawlerByCodeController {

    @Autowired
    CrawlerByCodeService crawlerByCodeService;



    @GetMapping("/{areacode}")
    public void start( @PathVariable String areacode){
        crawlerByCodeService.start(areacode);
    }

}

package com.example.springboot.controller;

import com.example.springboot.service.CrawlerByCodeService;
import com.example.springboot.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 企业获取
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


    /**
     * 合并企业名称到企业表中
     * @return
     */
    @GetMapping("/marageEnterpriseName")
    public ResponseData marageEnterpriseName(){
        crawlerByCodeService.marageEnterpriseName();
        return ResponseData.SUCCESS("成功！");
    }
    /**
     * 爬取企业信息（顺企网）
     * @return
     */
    @GetMapping("/crawlersqw")
    public ResponseData crawlersqw(){
        crawlerByCodeService.crawlersqw();
        return ResponseData.SUCCESS("成功！");
    }


    /**
     * 爬取企业信息（天眼查）
     * @return
     */
    @GetMapping("/crawlertyc")
    public ResponseData crawlertyc(){
        crawlerByCodeService.crawlertyc();
        return ResponseData.SUCCESS("成功！");
    }


}

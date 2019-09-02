package com.example.springboot.controller;

import com.example.springboot.service.CrawlerByCodeService;
import com.example.springboot.utils.ResponseData;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
    public ResponseData marageEnterpriseName(HttpServletRequest request){
//        crawlerByCodeService.marageEnterpriseName();

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ResponseData.SUCCESS("成功！");
    }
    /**
     * 爬取企业信息（顺企网）
     * @return
     */
    @GetMapping("/crawlersqw")
    public ResponseData crawlersqw(String enterpriseName){
        ResponseData responseData = crawlerByCodeService.crawlersqw(enterpriseName);
        return responseData;
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

    /**
     * 爬取企业工商信息（天眼查）
     * @return
     */
    @GetMapping("/crawlertycid")
    public ResponseData crawlertycid(String enterpriseName){
        ResponseData responseData = crawlerByCodeService.crawlertycid(enterpriseName);
        return responseData;
    }


}

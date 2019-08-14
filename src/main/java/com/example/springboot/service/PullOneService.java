package com.example.springboot.service;


import com.example.springboot.crawler.PageCrawler;
import com.example.springboot.crawler.PageParserTool;
import com.example.springboot.crawler.RequestAndResponseTool;
import com.example.springboot.dto.OneEnterpriseCrawlerDTO;
import com.example.springboot.entity.EnterpriseInfo;
import com.example.springboot.entity.PersionInfo;
import com.example.springboot.utils.Constants;
import com.example.springboot.utils.ResponseData;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 网络爬虫 单数据的获取处理类
 *
 * @author: Tong
 * @date:  2019/8/13
 */
@Service
public class PullOneService {

    private static Logger logger = LoggerFactory.getLogger(PullOneService.class);


    /**
     * 根据企业名称查询企业情况
     * @param enterpriseName
     * @return
     */
    public ResponseData<OneEnterpriseCrawlerDTO> getOneEntByName(String enterpriseName) {
        if(StringUtils.isBlank(enterpriseName)){
            return ResponseData.RESPONSE(Constants.httpState.PARAM_ERROR);
        }
        //从顺企网获取数据
        ResponseData<OneEnterpriseCrawlerDTO> responseDataBy11467 = this.getOneEntByNameFrom11467(enterpriseName);

        return responseDataBy11467;
    }

    /**
     * 从顺企网获取数据
     * @param enterpriseName 企业名称
     * @return
     */
    private ResponseData<OneEnterpriseCrawlerDTO> getOneEntByNameFrom11467(String enterpriseName) {
        String encoderEnterpriseName = null;
        try {
            encoderEnterpriseName = URLEncoder.encode(enterpriseName,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return ResponseData.RESPONSE(Constants.httpState.URLENCODER_ERROR);
        }
        String url = "http://so.11467.com/cse/search?q="+encoderEnterpriseName+"&click=1&s=662286683871513660&nsid=1";
        String resultUrl = null;
        try {
            Thread.sleep(1000*5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PageCrawler page = RequestAndResponseTool.sendRequstAndGetResponse(url);
        Elements elBody = PageParserTool.select(page, "body");
        Elements elH3 = elBody.select("h3");

        //获取企业详情地址
        for(Element elementEach:elH3){
            Elements em = elementEach.select("em");
            if(!StringUtils.equals(em.html(),enterpriseName)){
                continue;
            }
            resultUrl = elementEach.select("a").attr("href");
            if(StringUtils.startsWith(resultUrl,"https://www.11467.com/")){
                break;
            }
        }


        //获取企业详情
        try {
            Thread.sleep(1000*5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(StringUtils.isBlank(resultUrl)){
            return ResponseData.RESPONSE(Constants.httpState.PARAM_ERROR,"无法获取到顺企网企业地址");
        }
        page = RequestAndResponseTool.sendRequstAndGetResponse(resultUrl);
        //初始化参数
        EnterpriseInfo enterpriseInfo = new EnterpriseInfo();
        enterpriseInfo.setEnterpriseName(enterpriseName);
        PersionInfo persionInfo = new PersionInfo();
        persionInfo.setEnterpriseName(enterpriseName);
        //获取联系方式
        Elements elContacts = PageParserTool.select(page, "dd");
        if (elContacts.size() >= 6) {
            enterpriseInfo.setAddress(elContacts.get(0).html());
            persionInfo.setPhone(elContacts.get(1).html());
            persionInfo.setPsnName(elContacts.get(2).html());
            persionInfo.setMobilePhone(elContacts.get(3).html());
            persionInfo.setEmail(elContacts.get(4).html());
        }
        OneEnterpriseCrawlerDTO oneEnterpriseCrawlerDTO = new OneEnterpriseCrawlerDTO();
        oneEnterpriseCrawlerDTO.setEnterpriseInfo(enterpriseInfo);
        List<PersionInfo> persionInfoList = new ArrayList<>();
        persionInfoList.add(persionInfo);
        oneEnterpriseCrawlerDTO.setPersionInfoList(persionInfoList);



        return ResponseData.SUCCESS(oneEnterpriseCrawlerDTO);
    }
}

package com.example.springboot.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.crawler.PageCrawler;
import com.example.springboot.crawler.PageParserTool;
import com.example.springboot.crawler.RequestAndResponseTool;
import com.example.springboot.dto.OneEnterpriseCrawlerDTO;
import com.example.springboot.entity.EnterpriseAll;
import com.example.springboot.entity.EnterpriseInfo;
import com.example.springboot.entity.PersionInfo;
import com.example.springboot.utils.Constants;
import com.example.springboot.utils.G_Code;
import com.example.springboot.utils.HttpUtils;
import com.example.springboot.utils.ResponseData;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Service
public class CrawlerByCodeService {

    private static Logger logger = LoggerFactory.getLogger(CrawlerByCodeService.class);

    @Autowired
    IAreainfoService areainfoService;

    @Autowired
    IEnterpriseInfoService enterpriseInfoService;

    @Autowired
    IEnterpriseAllService enterpriseAllService;




    private static char[] lastCOde = "0123456789QWERTYUPADFGHJKLXCBNM".toCharArray();


    public void start(String areacode) {
        List<String> listIds = Lists.newLinkedList();
//        List<String> listIds = areainfoService.selectIdsByAreaCode(areacode);
        Map<String, String> params = Maps.newHashMap();
        params.put("Accept", "*/*");
        params.put("Sec-Fetch-Mode", "cors");
        params.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36");
        params.put("version", "TYC-Web");
        params.put("X-Auth-Token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMzk4OTgwNTc1NSIsImlhdCI6MTU2NjU0NzI3OSwiZXhwIjoxNTk4MDgzMjc5fQ.-atFioMd63_9W8cKIIPti0gfhiyDikeokgmSSkTTWpnok8rByNItA66kMkQcNWUKQv7EwULK9CNqwVQcU6hmcw");
        List<String> listOrgCode = G_Code.allOrgCode();
        outter:
        for (String idCode : listIds) {
            String code = "91" + idCode;
            for (String orgCode : listOrgCode) {

                String code2 = code + orgCode;

                inner:
                for (char c : lastCOde) {
                    String realCode = code2 + c;
                    if (G_Code.checkUSCC(realCode)) {
                        params.put("Referer", "https://www.tianyancha.com/search?key=" + realCode);
                        String url = "https://www.qichacha.com/gongsi_mindlist?type=mind&searchKey=" + realCode + "&searchType=0";
                        try {
                            Thread.sleep(1000 * 1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Document doc = null;
                        try {
                            doc = Jsoup.connect(url).headers(params).get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (doc.select("span").size() >= 2) {
                            System.out.println(realCode + "  :  " + doc.select("span").get(1).html());

                            EnterpriseInfo enterpriseInfo = new EnterpriseInfo();
                            enterpriseInfo.setEnterpriseName(doc.select("span").get(1).html());
                            enterpriseInfo.setEnterpriseId(realCode);
                            enterpriseInfoService.save(enterpriseInfo);
                        }
                        break inner;
                    }
                }


            }


        }
    }


    @Async
    public void marageEnterpriseName() {
        //删除列入的企业
        enterpriseAllService.deleteReadyEnt();
        //批量插入
        enterpriseAllService.insertEntName();
        //删除列入的企业
        enterpriseAllService.deleteReadyEnt();
        //todo 删除重复记录
    }


    /**
     * 从顺企网爬数据
     * @param enterpriseName
     * @return
     */
    public ResponseData crawlersqw(String enterpriseName) {

        EnterpriseAll enterpriseAll = enterpriseAllService.getOne(new QueryWrapper<EnterpriseAll>().eq("enterprise_Name", enterpriseName));
        if(enterpriseAll == null){
            return ResponseData.ERROR("企业名称有误");
        }


        String encoderEnterpriseName = null;
        try {
            encoderEnterpriseName = URLEncoder.encode(enterpriseName,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return ResponseData.ERROR(e.getMessage());
        }
        String url = "http://so.11467.com/cse/search?q="+encoderEnterpriseName+"&click=1&s=662286683871513660&nsid=1";
        String resultUrl = null;
        try {
            //睡眠2秒钟
            Thread.sleep(1000*2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseData.ERROR(e.getMessage());
        }
        Elements elBody = doc.select("body");

//
//        PageCrawler page = RequestAndResponseTool.sendRequstAndGetResponse(url);
//        Elements elBody = PageParserTool.select(page, "body");
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
            Thread.sleep(1000*1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        page = RequestAndResponseTool.sendRequstAndGetResponse(resultUrl);
        try {
            doc = Jsoup.connect(resultUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //初始化参数

        PersionInfo persionInfo = new PersionInfo();
        persionInfo.setEnterpriseName(enterpriseName);
        //获取联系方式
        Elements elContacts = doc.select( "dd");
        if (elContacts.size() >= 6) {
            enterpriseAll.setAddress(elContacts.get(0).html());
            persionInfo.setPhone(elContacts.get(1).html());
            persionInfo.setPsnName(elContacts.get(2).html());
            persionInfo.setMobilePhone(elContacts.get(3).html());
            persionInfo.setEmail(elContacts.get(4).html());
        }
        OneEnterpriseCrawlerDTO oneEnterpriseCrawlerDTO = new OneEnterpriseCrawlerDTO();
        EnterpriseInfo enterpriseInfo = new EnterpriseInfo();
        BeanUtils.copyProperties(enterpriseAll,enterpriseInfo);
        oneEnterpriseCrawlerDTO.setEnterpriseInfo(enterpriseInfo);
        List<PersionInfo> persionInfoList = new ArrayList<>();
        persionInfoList.add(persionInfo);
        oneEnterpriseCrawlerDTO.setPersionInfoList(persionInfoList);

        return ResponseData.SUCCESS(oneEnterpriseCrawlerDTO);
    }

    @Async
    public void crawlertyc() {

        int count = 0;
        while (true) {
            count++;
            IPage<EnterpriseInfo> enterpriseListPage = enterpriseInfoService.page(new Page<EnterpriseInfo>(count, 100) {
            }, new QueryWrapper<EnterpriseInfo>().eq("resourceType",10).isNull("enterpriseId"));
            for (EnterpriseInfo enterpriseInfo : enterpriseListPage.getRecords()) {

                //获取企业id
                String url = getEnterpriseId(enterpriseInfo);




            }

            if (enterpriseListPage.getCurrent() >= enterpriseListPage.getPages()) {
                logger.info("天眼查爬虫结束");
                break;
            }
        }

    }

    /**
     * 获取企业id
     * @param enterpriseInfo
     * @return 天眼查企业地址
     */
    private String getEnterpriseId(EnterpriseInfo enterpriseInfo) {
        return null;
    }


    /**
     * 查询工商信息（天眼查）
     * @param enterpriseName
     * @return
     */
    public ResponseData crawlertycid(String enterpriseName) {
        Map<String, String> handers = Maps.newHashMap();
        handers.put("Accept", "*/*");
        handers.put("Sec-Fetch-Mode", "cors");
        handers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36");
        handers.put("version", "TYC-Web");
        handers.put("X-Auth-Token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMzk4OTgwNTc1NSIsImlhdCI6MTU2NjU0NzI3OSwiZXhwIjoxNTk4MDgzMjc5fQ.-atFioMd63_9W8cKIIPti0gfhiyDikeokgmSSkTTWpnok8rByNItA66kMkQcNWUKQv7EwULK9CNqwVQcU6hmcw");
        String encoderEnterpriseName = null;
        try {
            encoderEnterpriseName = URLEncoder.encode(enterpriseName,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return ResponseData.ERROR(e.getMessage());
        }

        Map<String, Object> params = Maps.newHashMap();
        params.put("key",encoderEnterpriseName);
        params.put("_",String.valueOf(Calendar.getInstance().getTime().getTime()));
        String url = "https://sp0.tianyancha.com/search/suggestV2.json";

        String request = HttpUtils.getRequest(url, handers, params);
        String id = request.substring(request.indexOf("\"data\":[{\"id\":")+14,request.lastIndexOf("\"name\":")-2);

        handers.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
        handers.put("Accept-Encoding","gzip, deflate, br");
        handers.put("Accept-Language","Accept-Language");
        handers.put("Cache-Control","max-age=0");
        handers.put("Connection","keep-alive");
        handers.put("Cookie","aliyungf_tc=AQAAACkm40ZDsw0AwCLMc7tg7Tc4Jk80; csrfToken=jfAYT1tIVBkaJM3zQ3vgPZ8r; jsid=SEM-BAIDU-PZ1907-SY-000100; TYCID=2e7fa2f6fb8b4319949ae5d921c72a34; undefined=1b7eeb70c57c11e9b81d4bfe21ed2dc9; ssuid=9380652800; bannerFlag=undefined; Hm_lvt_e92c8d65d92d534b0fc290df538b4758=1566547245; _ga=GA1.2.1070884587.1566547245; _gid=GA1.2.1418381795.1566547245; token=272439427fec47c9a6375e1ff286aa70; _utm=7695712d35b94816a1936b4099081444; tyc-user-info=%257B%2522claimEditPoint%2522%253A%25220%2522%252C%2522myAnswerCount%2522%253A%25220%2522%252C%2522myQuestionCount%2522%253A%25220%2522%252C%2522signUp%2522%253A%25220%2522%252C%2522explainPoint%2522%253A%25220%2522%252C%2522privateMessagePointWeb%2522%253A%25220%2522%252C%2522nickname%2522%253A%2522%25E5%2591%25A8%25E5%2585%25AC%2522%252C%2522integrity%2522%253A%25220%2525%2522%252C%2522privateMessagePoint%2522%253A%25220%2522%252C%2522state%2522%253A%25220%2522%252C%2522announcementPoint%2522%253A%25220%2522%252C%2522isClaim%2522%253A%25220%2522%252C%2522vipManager%2522%253A%25220%2522%252C%2522discussCommendCount%2522%253A%25221%2522%252C%2522monitorUnreadCount%2522%253A%25220%2522%252C%2522onum%2522%253A%25221%2522%252C%2522claimPoint%2522%253A%25220%2522%252C%2522token%2522%253A%2522eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMzk4OTgwNTc1NSIsImlhdCI6MTU2NjU0NzI3OSwiZXhwIjoxNTk4MDgzMjc5fQ.-atFioMd63_9W8cKIIPti0gfhiyDikeokgmSSkTTWpnok8rByNItA66kMkQcNWUKQv7EwULK9CNqwVQcU6hmcw%2522%252C%2522pleaseAnswerCount%2522%253A%25220%2522%252C%2522redPoint%2522%253A%25220%2522%252C%2522bizCardUnread%2522%253A%25220%2522%252C%2522vnum%2522%253A%25220%2522%252C%2522mobile%2522%253A%252213989805755%2522%257D; auth_token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMzk4OTgwNTc1NSIsImlhdCI6MTU2NjU0NzI3OSwiZXhwIjoxNTk4MDgzMjc5fQ.-atFioMd63_9W8cKIIPti0gfhiyDikeokgmSSkTTWpnok8rByNItA66kMkQcNWUKQv7EwULK9CNqwVQcU6hmcw; RTYCID=33def4004af44042965ca9ec86eabc14; CT_TYCID=2e7fa2f6fb8b4319949ae5d921c72a34; Hm_lpvt_e92c8d65d92d534b0fc290df538b4758=1566548669; cloud_token=4e0cb00116e8441d9ef1ffa763a20c28; cloud_utm=c4d97792a608461da8bbb174f5bd31a6");
        handers.put("Host","www.tianyancha.com");
        handers.put("Sec-Fetch-Mode","navigate");
        handers.put("Sec-Fetch-Site","none");
        handers.put("Sec-Fetch-User","?1");
        handers.put("Upgrade-Insecure-Requests","1");
        handers.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36");

        String s = HttpUtils.postRequest("https://www.tianyancha.com/company/" + id, handers, null);
//        Document doc = null;
//        try {
//            doc = Jsoup.connect("https://www.tianyancha.com/company/"+id).headers(handers).get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        return null;
    }
}

package com.example.springboot.service;

import com.example.springboot.entity.ContactsDTO;
import com.example.springboot.entity.EnterpriseDTO;
import com.example.springboot.utils.ResponseData;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class WebCrawlerService {
    private final static Logger LOGGER = LoggerFactory.getLogger(WebCrawlerService.class);

    @Autowired
    ProxyPoolService proxyPoolService;

     static final String URL_114 = "http://so.11467.com/cse/search";

    static final Pattern patternQQ = Pattern.compile("[1-9]([0-9]{5,11})");

    /**
     * 从顺企网上爬取企业信息
     *
     * @param enterpriseName
     * @return
     */
    public ResponseData<EnterpriseDTO> crawlerFrom114(String enterpriseName) {
        Map<String, String> params = new HashMap<>();
        params.put("q", enterpriseName);
        params.put("click", "1");
        params.put("nsid", "1");
        params.put("s", "662286683871513660");
        Document doc = null;
        Proxy proxy = null;
        try {
            Thread.sleep(1000 * 15);
//            proxy = proxyPoolService.getProxy(URL_114);
//            doc = Jsoup.connect(URL_114).proxy(proxy).data(params).get();
            doc = Jsoup.connect(URL_114).data(params).get();
            if(doc == null){
                throw new RuntimeException("获取document失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("顺企网获取失败!", e);
            return ResponseData.ERROR("顺企网获取失败!");
        }
        Element elBody = doc.selectFirst("div[id=results]");
        //获取企业详情地址
        Element em = elBody.selectFirst("em");
        if (!StringUtils.equals(StringUtils.trim(em.html()), enterpriseName)) {
            return ResponseData.ERROR("顺企网无法获取该企业");
        }
        String resultUrl = elBody.select("a").attr("href");
        if (!StringUtils.contains(resultUrl, "11467") ) {
            LOGGER.error("第一个网址不是企业网址！");
            return ResponseData.ERROR("第一个网址错误！");
        }
        try {
//            doc = Jsoup.connect(resultUrl).proxy(proxy).get();
            Thread.sleep(1000 * 15);
            doc = Jsoup.connect(resultUrl).get();
            if(doc == null){
                throw new RuntimeException("获取企业基本信息失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("获取企业基本信息失败！",e);
        }

        Element elContact= doc.selectFirst("div[id=contact]");
        if(elContact == null){
            return ResponseData.ERROR("获取企业联系人失败！！");
        }
        EnterpriseDTO enterpriseDTO = new EnterpriseDTO();
        enterpriseDTO.setEnterpriseName(enterpriseName);
        //获取联系人方式
        getContentInfo114(elContact, enterpriseDTO);
        Element elBuss= doc.selectFirst("div[id=gongshang]");
        //获取主营产品
        getEnterpriseInfo114(elBuss, enterpriseDTO);

        return ResponseData.SUCCESS(enterpriseDTO);
    }

    /**
     * 获取企业主营产品
     * @param elBuss
     * @param enterpriseDTO
     */
    private void getEnterpriseInfo114(Element elBuss, EnterpriseDTO enterpriseDTO) {
        Elements elementsContactTrs = elBuss.select("tr");
        if(elementsContactTrs == null && elementsContactTrs.isEmpty()){
            return;
        }
        for(Element element:elementsContactTrs){
            Elements elementTds = element.select("td");
            if(elementTds == null || elementTds.isEmpty()){
                return;
            }
            String html = elementTds.get(0).html();
            if(html != null && StringUtils.contains(html,"主要经营产品")){
                enterpriseDTO.setMainProduction(elementTds.get(1).html());
            }
        }
    }

    /**
     * 获取联系人信息
     * @param elContact
     * @param enterpriseDTO
     */
    public void getContentInfo114(Element elContact, EnterpriseDTO enterpriseDTO) {
        ContactsDTO contactsDTO = new ContactsDTO();
        contactsDTO.setEnterpriseName(enterpriseDTO.getEnterpriseName());
        //获取联系人头
        Elements elementsContactDts = elContact.select("dt");
        //获取联系人内容
        Elements elementsContactDds = elContact.select("dd");

        int elCount = -1;
        for(Element elementEachDt:elementsContactDts){
            elCount++;
            String elDt = elementEachDt.html();
            elDt = StringUtils.trim(elDt);
            if(elDt == null && elDt.isEmpty()){
                continue;
            }
            if(StringUtils.contains(elDt,"地址")){
                Element elementEachDd = elementsContactDds.get(elCount);
                if(elementEachDd != null){
                    enterpriseDTO.setAddress(StringUtils.trim(elementEachDd.html()));
                }
            }
            if(StringUtils.contains(elDt,"电话")){
                Element elementEachDd = elementsContactDds.get(elCount);
                if(elementEachDd != null){
                    contactsDTO.setPhone(StringUtils.trim(elementEachDd.html()));
                }
            }
            if(StringUtils.contains(elDt,"手机")){
                Element elementEachDd = elementsContactDds.get(elCount);
                if(elementEachDd != null){
                    contactsDTO.setMobelPhone(StringUtils.trim(elementEachDd.html()));
                }
            }
            if(StringUtils.contains(elDt,"经理")){
                Element elementEachDd = elementsContactDds.get(elCount);
                if(elementEachDd != null){
                    contactsDTO.setPosition(StringUtils.trim(elementEachDd.html()));
                }
            }
            if(StringUtils.contains(elDt,"邮件")){
                Element elementEachDd = elementsContactDds.get(elCount);
                if(elementEachDd != null){
                    contactsDTO.setEmail(StringUtils.trim(elementEachDd.html()));
                }
            }
            if(StringUtils.contains(elDt,"QQ")){
                Element elementEachDd = elementsContactDds.get(elCount);
                if(elementEachDd != null){
                    String htmlQQ = elementEachDd.html();
                    if(htmlQQ != null){
                        Matcher matcherQ = patternQQ.matcher(htmlQQ);
                        if (matcherQ.find()) {
                            contactsDTO.setEmail(StringUtils.trim(matcherQ.group()));
                        }
                    }

                }
            }
        }
        List<ContactsDTO> contactsDTOList = Lists.newArrayList();
        contactsDTOList.add(contactsDTO);
        enterpriseDTO.setContactsDTOs(contactsDTOList);
    }


    private static final String URL_QXB="https://www.qixin.com/search";
    public ResponseData<EnterpriseDTO> crawlerFromQxb(String enterpriseName) {
        Map<String, String> params = new HashMap<>();
        params.put("from", "baidusem10");
        params.put("key", enterpriseName);
        params.put("page", "1");
        Document doc = null;
        try {
            Thread.sleep(1000 * 2);
            doc = Jsoup.connect(URL_QXB).data(params).get();
            if(doc == null){
                throw new RuntimeException("获取document失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("启信宝获取失败!", e);
            return ResponseData.ERROR("启信宝获取失败!");
        }
        Element elBody = doc.selectFirst("div[class=company-title font-18 font-f1]");
        //获取企业详情地址
        Element em = elBody.selectFirst("em");
        if (!StringUtils.equals(StringUtils.trim(em.html()), enterpriseName)) {
            return ResponseData.ERROR("启信宝无法获取该企业");
        }
        String resultUrl = elBody.select("a").attr("href");
//        if (!StringUtils.startsWith(resultUrl, "http://www.11467.com/")) {
//            LOGGER.error("第一个网址不是企业网址！");
//            return ResponseData.ERROR("第一个网址错误！");
//        }
        try {
            resultUrl = URL_QXB+resultUrl;
            doc = Jsoup.connect(resultUrl).get();
            if(doc == null){
                throw new RuntimeException("获取企业基本信息失败！");
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("获取企业基本信息失败！",e);
        }

        Element elContact= doc.selectFirst("table[class=table table1 table-bordered]");
        if(elContact == null){
            return ResponseData.ERROR("获取企业联系人失败！！");
        }
        EnterpriseDTO enterpriseDTO = new EnterpriseDTO();
        enterpriseDTO.setEnterpriseName(enterpriseName);
        //获取联系人方式
        getContentInfoQxb(elContact, enterpriseDTO);
        Element elIc= doc.selectFirst("table[class=table table1 table-bordered]");
        //获取主营产品
        getEnterpriseInfoQxb(elIc, enterpriseDTO);

        return ResponseData.SUCCESS(enterpriseDTO);
    }

    private void getEnterpriseInfoQxb(Element elIc, EnterpriseDTO enterpriseDTO) {

    }

    private void getContentInfoQxb(Element elContact, EnterpriseDTO enterpriseDTO) {

    }
}

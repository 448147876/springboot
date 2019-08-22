package com.example.springboot.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.crawler.PageCrawler;
import com.example.springboot.crawler.PageParserTool;
import com.example.springboot.crawler.RequestAndResponseTool;
import com.example.springboot.dto.OneEnterpriseCrawlerDTO;
import com.example.springboot.entity.EnterpriseInfo;
import com.example.springboot.entity.PersionInfo;
import com.example.springboot.utils.Constants;
import com.example.springboot.utils.G_Code;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        params.put("Cookie", "QCCSESSID=e2cr8uhrpcbf3r70kc23hia5p4; zg_did=%7B%22did%22%3A%20%2216c83dd79c068e-030ca5931dcb03-7373e61-1fa400-16c83dd79c1bdf%22%7D; UM_distinctid=16c83dd7a39a10-078262d8baa9aa-7373e61-1fa400-16c83dd7a3a42c; _uab_collina=156558042587509283636563; acw_tc=73dc081e15655804230666614ec8c0a7f88dde7e505ad744a033ade8d0; hasShow=1; CNZZDATA1254842228=860200595-1565575546-https%253A%252F%252Fwww.baidu.com%252F%7C1565921176; Hm_lvt_3456bee468c83cc63fb5147f119f1075=1565679509,1565762908,1565916361,1565923166; zg_de1d1a35bfa24ce29bbf2c7eb17e6c4f=%7B%22sid%22%3A%201565923166195%2C%22updated%22%3A%201565923257800%2C%22info%22%3A%201565580425672%2C%22superProperty%22%3A%20%22%7B%7D%22%2C%22platform%22%3A%20%22%7B%7D%22%2C%22utm%22%3A%20%22%7B%5C%22%24utm_source%5C%22%3A%20%5C%22baidu%5C%22%2C%5C%22%24utm_medium%5C%22%3A%20%5C%22cpc%5C%22%2C%5C%22%24utm_term%5C%22%3A%20%5C%22%E4%BC%81%E4%B8%9A%E6%9F%A5%E8%AF%A2%E5%A4%9A%E8%AF%8D1%5C%22%7D%22%2C%22referrerDomain%22%3A%20%22www.baidu.com%22%2C%22cuid%22%3A%20%22a8fbd4c3b9268fc47ef96770cfab0758%22%7D; Hm_lpvt_3456bee468c83cc63fb5147f119f1075=1565923258");
        params.put("Connection", "keep-alive");
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
                        params.put("Referer", "https://www.qichacha.com/search?key=" + realCode);
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

    @Async
    public void crawlersqw() {
        int count = 0;
        while (true) {
            count++;
            IPage<EnterpriseInfo> enterpriseListPage = enterpriseInfoService.page(new Page<EnterpriseInfo>(count, 100) {
            }, new QueryWrapper<EnterpriseInfo>().eq("resourceType",10).isNull("enterpriseId"));
            for (EnterpriseInfo enterpriseInfo : enterpriseListPage.getRecords()) {


                String enterpriseName = enterpriseInfo.getEnterpriseName();
                String encoderEnterpriseName = null;
                try {
                    encoderEnterpriseName = URLEncoder.encode(enterpriseName,"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                    continue;
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
                    continue;
                }
                page = RequestAndResponseTool.sendRequstAndGetResponse(resultUrl);
                //初始化参数

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






            }

            if (enterpriseListPage.getCurrent() >= enterpriseListPage.getPages()) {
                logger.info("顺企网爬虫结束");
                break;
            }
        }
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
}

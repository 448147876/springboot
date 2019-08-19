package com.example.springboot.service;


import com.example.springboot.entity.EnterpriseInfo;
import com.example.springboot.service.impl.EnterpriseInfoServiceImpl;
import com.example.springboot.utils.G_Code;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.example.springboot.utils.G_Code.orgCode;

@Service
public class CrawlerByCodeService {
    @Autowired
    IAreainfoService areainfoService;

    @Autowired
    IEnterpriseInfoService enterpriseInfoService;

    private static char [] lastCOde="0123456789QWERTYUPADFGHJKLXCBNM".toCharArray();






    public void start(String areacode) {
        List<String> listIds = Lists.newLinkedList();
//        List<String> listIds = areainfoService.selectIdsByAreaCode(areacode);
        Map<String, String> params = Maps.newHashMap();
        params.put("Cookie", "QCCSESSID=e2cr8uhrpcbf3r70kc23hia5p4; zg_did=%7B%22did%22%3A%20%2216c83dd79c068e-030ca5931dcb03-7373e61-1fa400-16c83dd79c1bdf%22%7D; UM_distinctid=16c83dd7a39a10-078262d8baa9aa-7373e61-1fa400-16c83dd7a3a42c; _uab_collina=156558042587509283636563; acw_tc=73dc081e15655804230666614ec8c0a7f88dde7e505ad744a033ade8d0; hasShow=1; CNZZDATA1254842228=860200595-1565575546-https%253A%252F%252Fwww.baidu.com%252F%7C1565921176; Hm_lvt_3456bee468c83cc63fb5147f119f1075=1565679509,1565762908,1565916361,1565923166; zg_de1d1a35bfa24ce29bbf2c7eb17e6c4f=%7B%22sid%22%3A%201565923166195%2C%22updated%22%3A%201565923257800%2C%22info%22%3A%201565580425672%2C%22superProperty%22%3A%20%22%7B%7D%22%2C%22platform%22%3A%20%22%7B%7D%22%2C%22utm%22%3A%20%22%7B%5C%22%24utm_source%5C%22%3A%20%5C%22baidu%5C%22%2C%5C%22%24utm_medium%5C%22%3A%20%5C%22cpc%5C%22%2C%5C%22%24utm_term%5C%22%3A%20%5C%22%E4%BC%81%E4%B8%9A%E6%9F%A5%E8%AF%A2%E5%A4%9A%E8%AF%8D1%5C%22%7D%22%2C%22referrerDomain%22%3A%20%22www.baidu.com%22%2C%22cuid%22%3A%20%22a8fbd4c3b9268fc47ef96770cfab0758%22%7D; Hm_lpvt_3456bee468c83cc63fb5147f119f1075=1565923258");
        params.put("Connection", "keep-alive");
        List<String> listOrgCode = G_Code.allOrgCode();
        outter:for (String idCode : listIds) {
            String code ="91"+idCode;
            for(String orgCode :listOrgCode){

                String code2 = code+orgCode;

                inner:for(char c : lastCOde){
                    String realCode = code2+c;
                    if(G_Code.checkUSCC(realCode)){
                        params.put("Referer", "https://www.qichacha.com/search?key="+realCode);
                        String url =  "https://www.qichacha.com/gongsi_mindlist?type=mind&searchKey="+realCode+"&searchType=0";
                        try {
                            Thread.sleep(1000*1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Document doc = null;
                        try {
                            doc = Jsoup.connect(url).headers(params).get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(doc.select("span").size()>=2){
                            System.out.println(realCode+"  :  "+doc.select("span").get(1).html());

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
























}

package com.example.springboot.service.impl;

import com.example.springboot.entity.Customer;
import com.example.springboot.entity.Customeruser;
import com.example.springboot.service.IDataFromBaiduService;
import com.example.springboot.utils.ResponseData;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DataFromBaiduServiceImpl implements IDataFromBaiduService {

    @Autowired
    private RestTemplate restTemplate;

    private final static String BAIDU_ENT_URL="https://xin.baidu.com/s?t=1&q=";
    private final static String BASE_BAIDU_ENT_URL="https://xin.baidu.com";


    @Override
    public ResponseData<Customer> getEntInfo(String name) {
        Customer customer = new Customer();
        customer.setName(name);
        String url = BAIDU_ENT_URL+name;
        try {
            Thread.sleep(1000 * ((int) (10 + Math.random() * 5)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String infoList = restTemplate.getForObject(url, String.class);
        Document document = Jsoup.parse(infoList);
        Elements select = document.select("div[class=zx-list-item]");
        if(select == null || select.isEmpty()){
            return ResponseData.ERRORMSG("没有查询到:"+name+";");
        }
        String href = null;
        for(Element element:select){
            String title = element.select("h3>a").attr("title");
            if(StringUtils.equals(name,title)){
                href = element.select("h3>a").attr("href");
                break;
            }
        }
        if(href == null){
            return ResponseData.ERRORMSG("没有查询到:"+name+";");
        }
        try {
            Thread.sleep(1000 * ((int) (10 + Math.random() * 5)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String info = restTemplate.getForObject(BASE_BAIDU_ENT_URL+href, String.class);

        document = Jsoup.parse(info);

        Elements selectBussiness = document.select("#basic-business>div>table>tbody>tr");


        if(selectBussiness.size() >=10){
            //注册资金
            String td = selectBussiness.get(0).select("td").get(1).text();
            td = StringUtils.remove(td, ",");
            if(StringUtils.contains(td,".")){
                td = StringUtils.substringBefore(td,".");
                if(StringUtils.isNumeric(td)){
                    customer.setRegisteredCapital(Integer.parseInt(td));
                }
            }else if(StringUtils.contains(td,"万")){
                td = StringUtils.substringBefore(td,"万");
                if(StringUtils.isNumeric(td)){
                    customer.setRegisteredCapital(Integer.parseInt(td));
                }
            }
            //法定代表人
            td = selectBussiness.get(1).select("td").get(1).text();
            if(StringUtils.isNotBlank(td) && !StringUtils.equals(td,"-")){
                customer.setLegalRepresentative(td);
            }
            //所属行业
            td = selectBussiness.get(2).select("td").get(3).text();
            if(StringUtils.isNotBlank(td) && !StringUtils.equals(td,"-")){
                customer.setBusinessIndustry(td);
            }
            //统一社会信用代码
            td = selectBussiness.get(3).select("td").get(1).text();
            if(StringUtils.isNotBlank(td) && !StringUtils.equals(td,"-")){
                customer.setOrganizationCode(td);
            }
            //	成立日期
            td = selectBussiness.get(5).select("td").get(3).text();
            if(StringUtils.isNotBlank(td) && !StringUtils.equals(td,"-")){
                customer.setCreateTime(td);
            }
            //	企业类型
//            td = selectBussiness.get(6).select("td").get(1).text();
//            if(StringUtils.isNotBlank(td)){
//                customer.setCompanyType(td);
//            }
            //	注册地址
            td = selectBussiness.get(8).select("td").get(1).text();
            if(StringUtils.isNotBlank(td) && !StringUtils.equals(td,"-")){
                customer.setAddress(td);
            }
            //	经营范围
            td = selectBussiness.get(9).select("td").get(1).text();
            if(StringUtils.isNotBlank(td) && !StringUtils.equals(td,"-")){
                customer.setMainProducts(td);
            }
        }
        Elements selectContent = document.select("div[class=zx-detail-company-info]>div");

        Customeruser customeruser = new Customeruser();
        if(selectContent != null && selectContent.size()>=8){
            String text = selectContent.get(2).text();
            if(StringUtils.isNotBlank(text)){
                text = StringUtils.remove(text, "电话：");
                if(StringUtils.isNotBlank(text) && !StringUtils.equals(text,"-")){
                    customeruser.setPhone(text);
                }
            }
            text = selectContent.get(3).select("span").text();
            if(StringUtils.isNotBlank(text)  && !StringUtils.equals(text,"-")){
                customeruser.setEmail(text);
            }
            text = selectContent.get(4).text();
            if(StringUtils.isNotBlank(text)  && !StringUtils.equals(text,"-")){
                text = StringUtils.remove(text, "官网：");
                if(StringUtils.isNotBlank(text) && !StringUtils.equals(text,"-")){
                    customer.setWebSite(text);
                }
            }
            text = selectContent.get(5).text();
            if(StringUtils.isNotBlank(text)){
                text = StringUtils.remove(text, "地址：");
                if(StringUtils.isNotBlank(text)  && !StringUtils.equals(text,"-")){
                    customer.setAddress(text);
                }
            }
            text = selectContent.get(6).select("p").attr("data-content");
            if(StringUtils.isNotBlank(text)  && !StringUtils.equals(text,"-")){
                customer.setCompanyDescript(text);
            }
        }

        List<Customeruser> customeruserList = Lists.newLinkedList();
        customeruserList.add(customeruser);
        customer.setCustomeruserList(customeruserList);
        return ResponseData.SUCCESS(customer);
    }
}

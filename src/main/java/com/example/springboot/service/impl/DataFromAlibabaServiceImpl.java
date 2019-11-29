package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.entity.Customer;
import com.example.springboot.entity.Customeruser;
import com.example.springboot.entity.gaode.GaoDePoiParamsDTO;
import com.example.springboot.entity.gaode.GaoDePoiResultDTO;
import com.example.springboot.entity.gaode.Poi;
import com.example.springboot.service.IDataFromAlibabaService;
import com.example.springboot.service.IDataFromBaiduService;
import com.example.springboot.utils.ResponseData;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DataFromAlibabaServiceImpl implements IDataFromAlibabaService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    CustomerServiceImpl customerService;

    @Autowired
    GaoDeMapService gaoDeMapService;


    private final static String ALIBABA_ENT_URL="https://xinyong.1688.com/credit/publicCreditSearch.htm?key=";
    private final static String BASE_ALIBABA_ENT_URL="https://xin.baidu.com";


    @Override
    public ResponseData<Customer> getEntInfo(String name) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3965.0 Safari/537.36 Edg/80.0.334.2");
        requestHeaders.add("cookie", "cna=4K0uFq7AXiACAXPMIf4e1EnQ; alicnweb=touch_tb_at%3D1574330399523; ali_ab=125.122.91.16.1574330413424.6; h_keys=\"%u7535%u9540#%u6c5f%u82cf%u897f%u5fb7%u7535%u68af%u6709%u9650%u516c%u53f8#%u6c5f%u82cf%u6c5f%u5357%u519c%u5316%u6709%u9650%u516c%u53f8#%u592a%u4ed3%u534e%u60a6%u6c7d%u8f66%u9500%u552e%u670d%u52a1%u6709%u9650%u516c%u53f8\"; ad_prefer=\"2019/11/21 18:02:13\"; EGG_SESS=h-uYXwnluus2BR4D7CDFvHsi5bxtYS5lL2riHlYHkM1267zeBWUihPvxWrqU6exOP2H_P1dkYNSiR0uF7LBB1k9Fgml2bxFcbdB3hs3F-E11EcYa1AHZEQ1be-F7dU4t; _tb_token_=skFD_AYgykGJsyo4xwM3zR8K; locale=zh-cn; UM_distinctid=16e8d9b4dc5a9-0245298f481b96-235f054c-384000-16e8d9b4dc6367; CNZZDATA1257121278=440535684-1574330775-https%253A%252F%252Fwww.baidu.com%252F%7C1574385424; isg=BAMDdupDWr32tBZIRQwN-nuQkseteJe6LhzkrzXgX2LZ9CMWvUgnCuFmbsQfz--y; l=dBIeH7bnqW36v5RLBOCanurza77OSIRYYuPzaNbMi_5CP6T1s8bOkLxxNF96VjWfTOLB4sW4H-J9-etkqRXzDSKTmF8gFMc.");
        HttpEntity<?> entity = new HttpEntity<>(requestHeaders);

        Customer customer = new Customer();
        customer.setName(name);
        String url = ALIBABA_ENT_URL+name;
//        String infoList = restTemplate.getForObject(url, String.class);
//        String infoList = restTemplate.getForObject(url, String.class);
        String infoList = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
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
        String info = restTemplate.getForObject(BASE_ALIBABA_ENT_URL+href, String.class);

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



    public ResponseData<Customer> getLocation(String name) {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",name);
        List<Customer> list = customerService.list(queryWrapper);
        if(list == null || list.isEmpty()){
            return ResponseData.ERRORMSG("企业不存在："+name);
        }
        Customer customer = list.get(0);
        GaoDePoiParamsDTO gaoDePoiParamsDTO = new GaoDePoiParamsDTO();
        //根据名字搜索地址
        gaoDePoiParamsDTO.setKeywords(name);
        gaoDePoiParamsDTO.setTypes("170000");
        getCustonerInfoFromPoi(gaoDePoiParamsDTO,customer);
        //根据地址搜索区域code和坐标
        if(StringUtils.isNotBlank(customer.getAddress()) && customer.getXSide() == null){
            gaoDePoiParamsDTO.setKeywords(customer.getAddress());
            gaoDePoiParamsDTO.setTypes("190100");
            getCustonerInfoFromPoi(gaoDePoiParamsDTO,customer);
        }




        return ResponseData.SUCCESS(customer);
    }

    public Customer getCustonerInfoFromPoi(GaoDePoiParamsDTO gaoDePoiParamsDTO,Customer customer){
        gaoDePoiParamsDTO.setExtensions("all");
        GaoDePoiResultDTO gaoDePoiResultDTO = gaoDeMapService.poiSearch(gaoDePoiParamsDTO);
        if(gaoDePoiResultDTO.getStatus() != 1 || gaoDePoiResultDTO.getPois() == null || gaoDePoiResultDTO.getPois().isEmpty()){
            return customer;
        }
        Poi poi = gaoDePoiResultDTO.getPois().get(0);
        String replace = StringUtils.replace(poi.getName(), " ", "");
         replace = StringUtils.replace(replace, "（", "(");
         replace = StringUtils.replace(replace, "）", ")");
         replace = StringUtils.lowerCase(replace);
        String search = StringUtils.replace(gaoDePoiParamsDTO.getKeywords(), " ", "");
        search = StringUtils.replace(search, "（", "(");
        search = StringUtils.replace(search, "）", ")");
        search = StringUtils.lowerCase(search);
        if(!StringUtils.contains(search,replace)){
            return customer;
        }
        String location = poi.getLocation();
        if(StringUtils.isNotBlank(location) && customer.getXSide() == null){
            String[] split = StringUtils.split(location, ",");
            customer.setXSide(Double.parseDouble(split[0]));
            customer.setYSide(Double.parseDouble(split[1]));
        }
        String address = poi.getAddress();
        if(StringUtils.isNotBlank(address) &&StringUtils.isBlank(customer.getAddress())){
            customer.setAddress(address);
        }
        String tel = poi.getTel();
        if(StringUtils.isNotBlank(tel) && !StringUtils.equals(tel,"[]")){
            Customeruser customeruser = new Customeruser();
            customeruser.setPhone(tel);
            customeruser.setRealName("高德地图");
            List<Customeruser> customeruserList = Lists.newLinkedList();
            customeruserList.add(customeruser);
            customer.setCustomeruserList(customeruserList);
        }
        String adcode = poi.getAdcode();
        if(StringUtils.endsWith(adcode,"0000")){
            adcode = StringUtils.substring(adcode,0,2);
        }else if(StringUtils.endsWith(adcode,"00")){
            adcode = StringUtils.substring(adcode,0,4);
        }
        if(StringUtils.isBlank(customer.getAreaCode()) || StringUtils.length(customer.getAreaCode()) < StringUtils.length(adcode)){
            customer.setAreaCode(adcode);
        }
        return customer;
    }



}

package com.example.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.springboot.entity.Customer;
import com.example.springboot.entity.CustomerImportQs;
import com.example.springboot.entity.CustomerImportSqw;
import com.example.springboot.entity.Customeruser;
import com.example.springboot.service.impl.CustomerImportQsServiceImpl;
import com.example.springboot.service.impl.CustomerImportSqwServiceImpl;
import com.example.springboot.service.impl.CustomerServiceImpl;
import com.example.springboot.service.impl.CustomeruserServiceImpl;
import com.example.springboot.utils.ResponseData;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import javax.xml.ws.Response;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 客户信息 前端控制器
 * </p>
 *
 * @author tzj
 * @since 2019-11-06
 */
@Controller
@RequestMapping("/customer/")
public class CustomerController {


    @Autowired
    CustomerServiceImpl customerService;


    @Autowired
    CustomeruserServiceImpl customeruserService;

    @Autowired
    CustomerImportSqwServiceImpl customerImportSqwService;

    @Autowired
    CustomerImportQsServiceImpl customerImportQsService;


    /**
     * 处理顺企网数据（1.4万条）
     * @return
     */
    @GetMapping("sqw")
    public Response sqwCustomerHandle(){
        List<CustomerImportSqw> list = customerImportSqwService.list();
        for(CustomerImportSqw customerImportSqw:list){
            try{
                handleBySqw(customerImportSqw);
            }catch (Exception e){
                e.printStackTrace();
            }
        }


        return null;
    }

    public  static final Pattern PATTERN_QQ = Pattern.compile("[1-9]([0-9]{5,11})");
    public  static final  Pattern PATTERN_PHONE = Pattern.compile("[0-9-()（）]{7,18}");
    public  static final Pattern PATTERN_MOBEL_PHONE = Pattern.compile("0?(13|14|15|17|18|19)[0-9]{9}");
    public  static final  Pattern PATTERN_EMAIL = Pattern.compile("\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}");

    private void handleBySqw(CustomerImportSqw customerImportSqw) {
        Customer customer = customerService.getOne(new QueryWrapper<Customer>().eq("name", customerImportSqw.getEnterpriseName()));
        if(customer == null){
            return;
        }
        //工商信息获取
        String businessHtml = customerImportSqw.getBusinessHtml();
        Document businessHtmlDoc = Jsoup.parse(businessHtml);
        Elements elementList = businessHtmlDoc.select("tr");
        int busCount = 0;
        for(Element element:elementList){
            Elements elementTds = element.select("td");
            if(elementTds != null && !elementTds.isEmpty()){
                Element elementTdFirst = elementTds.get(0);
                if(StringUtils.isNotBlank(elementTdFirst.text()) &&  StringUtils.contains(elementTdFirst.text(),"主要经营") ){
                    if(StringUtils.isBlank(customer.getMainProducts())){
                        customer.setMainProducts(elementTds.get(1).text());
                        busCount++;
                    }
                }
                if(StringUtils.isNotBlank(elementTdFirst.text()) &&  StringUtils.contains(elementTdFirst.text(),"成立时间") ){
                    if(StringUtils.isBlank(customer.getCreateTime())){
                        customer.setCreateTime(elementTds.get(1).text());
                        busCount++;
                    }
                }
                if(StringUtils.isNotBlank(elementTdFirst.text()) &&  StringUtils.contains(elementTdFirst.text(),"职员人数") ){
                    if(StringUtils.isBlank(customer.getStaffSize())){
                        customer.setStaffSize(elementTds.get(1).text());
                        busCount++;
                    }
                }
                if(StringUtils.isNotBlank(elementTdFirst.text()) &&  StringUtils.contains(elementTdFirst.text(),"注册资本") ){
                    if(customer.getRegisteredCapital() == null){
                        if(StringUtils.isNumeric(elementTds.get(1).text())){
                            customer.setRegisteredCapital(Integer.parseInt(elementTds.get(1).text()));
                            busCount++;
                        }
                    }
                }
                if(StringUtils.isNotBlank(elementTdFirst.text()) &&  StringUtils.contains(elementTdFirst.text(),"所属分类") ){
                    if(StringUtils.isBlank(customer.getBusinessIndustry())){
                        customer.setBusinessIndustry(elementTds.get(1).text());
                        busCount++;
                    }
                }

            }
        }

        if(StringUtils.isBlank(customer.getCompanyDescript())){
            customer.setCompanyDescript(customerImportSqw.getRemark());
            busCount++;
        }


        Customeruser customeruser = new Customeruser();
        customeruser.setCustomerID(customer.getId());

        String contentHtml = customerImportSqw.getContentHtml();
        Document contentHtmlDoc = Jsoup.parse(contentHtml);
        Elements dt = contentHtmlDoc.select("dt");
        Elements dd = contentHtmlDoc.select("dd");
        int i = 0;
        for(Element elementDt: dt){
            String textDt = elementDt.text();
            String textDD = dd.get(i).text();
            if(StringUtils.contains(textDt,"公司地址") && StringUtils.isBlank(customer.getAddress())){
                customer.setAddress(textDD);
            }
            if(StringUtils.contains(textDt,"电话") && !StringUtils.contains(textDD,"未提供")){
                customeruser.setPhone(textDD);
            }
            if(StringUtils.contains(textDt,"经") || StringUtils.contains(textDt,"厂")){
                customeruser.setPosition(textDt);
                customeruser.setRealName(textDD);
            }
            if(StringUtils.contains(textDt,"手机") && !StringUtils.contains(textDD,"未提供")){
                customeruser.setMobilePhone(textDD);
            }
            if(StringUtils.contains(textDt,"邮件")&& !StringUtils.contains(textDD,"未提供")){
                customeruser.setEmail(textDD);
            }
            if(StringUtils.contains(textDt,"QQ") && !StringUtils.contains(textDD,"未提供")){
                Matcher m = PATTERN_QQ.matcher(textDD);
                if(m.find()){
                    customeruser.setEmail(m.group());

                }
            }


            i++;
        }
        if(StringUtils.isBlank(customeruser.getMobilePhone())){
            Matcher matcher = PATTERN_MOBEL_PHONE.matcher(contentHtml);
            if(matcher.find()){
                customeruser.setMobilePhone(matcher.group());
            }
        }
        if(StringUtils.isBlank(customeruser.getPhone())){
            Matcher matcher = PATTERN_PHONE.matcher(contentHtml);
            if(matcher.find()){
                customeruser.setPhone(matcher.group());
            }
        }
        if((StringUtils.isNotBlank(customeruser.getPhone()) || StringUtils.isNotBlank(customeruser.getMobilePhone())) && StringUtils.isNotBlank(customeruser.getRealName())){
            //检查是否重复

            List<Customeruser> customeruserList = customeruserService.listNotContent(customeruser.getCustomerID(),customeruser.getRealName(),customeruser.getMobilePhone(),customeruser.getPhone());
            if(customeruserList == null || customeruserList.isEmpty()){
                customeruserService.save(customeruser);
            }
        }
        if(busCount > 0){
            customerService.updateById(customer);
        }
    }


    //从顺企网上获取注册资金
    @GetMapping("registeredCapital")
    public ResponseData getEntInfo(){
        //随机数，用于批次任务
        int randomJob = (int)(1+Math.random()*1000);
        //注册资金完善
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("RegisteredCapital");
        List<Customer> customerList = customerService.list(queryWrapper);
        for (Customer customer:customerList){
            String name = customer.getName();
            QueryWrapper<CustomerImportQs> qsQueryWrapper = new QueryWrapper<>();
            qsQueryWrapper.eq("enterprise_name", name);
            List<CustomerImportQs> customerImportQsList = customerImportQsService.list(qsQueryWrapper);
            if(customerImportQsList == null || customerImportQsList.isEmpty()){
                continue;
            }
            Integer registeredCapital = null;
            for(CustomerImportQs customerImportQs:customerImportQsList){
                String registeredCapitalEach = customerImportQs.getRegisteredCapital();
                if(StringUtils.isBlank(registeredCapitalEach) || !StringUtils.contains(registeredCapitalEach,"万")){
                    continue;
                }
                try{
                    double parseDouble = Double.parseDouble(StringUtils.substringBefore(registeredCapitalEach, "万"));
                    registeredCapital = (int)parseDouble;
                    break;
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
            if(registeredCapital != null){
                Customer customerNew = new Customer();
                customerNew.setRegisteredCapital(registeredCapital);
                customerNew.setJobID(randomJob);
                UpdateWrapper<Customer> customerUpdateWrapper = new UpdateWrapper<>();
                customerUpdateWrapper.eq("id",customer.getId());
                customerService.update(customerNew,customerUpdateWrapper);
            }

        }


        return ResponseData.SUCCESS();
    }


}


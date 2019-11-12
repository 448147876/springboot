package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Customer;
import com.example.springboot.entity.CustomerImportQs;
import com.example.springboot.mapper.CustomerMapper;
import com.example.springboot.service.ICustomerService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;

import static com.example.springboot.controller.CustomerController.*;

/**
 * <p>
 * 客户信息 服务实现类
 * </p>
 *
 * @author tzj
 * @since 2019-11-06
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

    /**
     * 查询没有完善的客户
     *
     * @return
     */
    @Override
    public List<Customer> findCustomerNotHandle() {
        List<Customer> list = getBaseMapper().findCustomerNotHandle();
        return list;
    }

    /**
     * 获取地址
     *
     * @param customerOld
     * @param customerImportQsEach
     */
    @Override
    public void getBussinessInfo(Customer customerOld, CustomerImportQs customerImportQsEach, Integer jobId) {
        //获取地址
        if (StringUtils.isBlank(customerOld.getAddress())) {
            String address = this.getDataByHtml(customerImportQsEach, 1);
            if (StringUtils.isNotBlank(address)) {
                customerOld.setAddress(address);
            }
        }
        //获取简介
        if (StringUtils.isBlank(customerOld.getCompanyDescript())) {
            String synopsis = this.getDataByHtml(customerImportQsEach, 13);
            if (StringUtils.isNotBlank(synopsis)) {
                customerOld.setCompanyDescript(synopsis);
            }
        }
        //获取成立时间
        if (StringUtils.isBlank(customerOld.getCreateTime())) {
            String registerDate = this.getDataByHtml(customerImportQsEach, 9);
            if (StringUtils.isNotBlank(registerDate)) {
                customerOld.setCreateTime(registerDate);
            }
        }
        //获取主营产品
        if (StringUtils.isBlank(customerOld.getMainProducts())) {
            String mainProduction = this.getDataByHtml(customerImportQsEach, 8);
            if (StringUtils.isNotBlank(mainProduction)) {
                customerOld.setMainProducts(mainProduction);
            }
        }
        //获取行业类别
        if (StringUtils.isBlank(customerOld.getBusinessIndustry())) {
            String industry = this.getDataByHtml(customerImportQsEach, 12);
            if (StringUtils.isNotBlank(industry)) {
                customerOld.setBusinessIndustry(industry);
            }
        }
        //获取注册资金
        if (customerOld.getRegisteredCapital() == null) {
            String registeredCapital = this.getDataByHtml(customerImportQsEach, 11);
            if (StringUtils.isNotBlank(registeredCapital)) {
                try {
                    double parseDouble = Double.parseDouble(StringUtils.substringBefore(registeredCapital, "万"));
                    customerOld.setRegisteredCapital((int) parseDouble);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (StringUtils.contains(registeredCapital, "-")) {
                    customerOld.setRegisteredCapital(0);
                }

            }
        }
        //获取法人
        if (StringUtils.isBlank(customerOld.getLegalRepresentative())) {
            String legalPsnName = this.getDataByHtml(customerImportQsEach, 14);
            if (StringUtils.isNotBlank(legalPsnName)) {
                customerOld.setLegalRepresentative(legalPsnName);
            }
        }
        //获取人员规模
        if (StringUtils.isBlank(customerOld.getStaffSize())) {
            String  managementForms = this.getDataByHtml(customerImportQsEach, 10);
            if (StringUtils.isNotBlank(managementForms)) {
                customerOld.setStaffSize(managementForms);
            }
        }
        customerOld.setJobID(jobId);
        getBaseMapper().updateById(customerOld);
    }


    //type=1:地址,type=2:座机,type=3:职位，type=4:姓名，type=5:手机，type=6:邮箱，type=7：qq
    //type=8:主要经营,type=9:成立时间,type=10:职员人数,type=11:注册资本,type=12:所属分类,type=13:简介,type=14:法人
    public String getDataByHtml(CustomerImportQs customerImportQs, int type) {
        if (StringUtils.equals("顺企网", customerImportQs.getSourceType())) {
            //联系人
            String contentHtmlSqw = customerImportQs.getContantHtml();
            String contantStr = customerImportQs.getContantStr();
            Document contentHtmlDoc = Jsoup.parse(contentHtmlSqw);
            Elements dt = contentHtmlDoc.select("dt");
            Elements dd = contentHtmlDoc.select("dd");
            int i = 0;
            for (Element elementDt : dt) {
                String textDt = elementDt.text();
                String textDD = dd.get(i).text();
                if (type == 1) {
                    if (StringUtils.contains(textDt, "公司地址")) {
                        String address = customerImportQs.getAddress();
                        if (StringUtils.isNotBlank(address)) {
                            return address;
                        } else {
                            if (StringUtils.isNotBlank(StringUtils.substringBetween(contantStr, "联系方式公司地址：", "固定电话"))) {
                                return StringUtils.substringBetween(contantStr, "联系方式公司地址：", "固定电话");
                            }
                        }
                        return textDD;
                    }
                }
                if (type == 2) {
                    if (StringUtils.contains(textDt, "电话") && !StringUtils.contains(textDD, "未提供")) {

                        if (StringUtils.isNotBlank(customerImportQs.getPhone())) {
                            return customerImportQs.getPhone();
                        } else {
                            if (StringUtils.isNotBlank(customerImportQs.getPhoneBak())) {
                                return customerImportQs.getPhoneBak();
                            }
                        }
                        return textDD;

                    }
                }
                if (StringUtils.contains(textDt, "经理：") || StringUtils.contains(textDt, "厂长：") || StringUtils.contains(textDt, "联系人")) {
                    if (type == 3) {
                        if (StringUtils.isNotBlank(customerImportQs.getDept())) {
                            return customerImportQs.getDept();
                        }
                        return textDt;
                    }
                    if (type == 4) {
                        if (StringUtils.isNotBlank(customerImportQs.getPsnName())) {
                            return customerImportQs.getPsnName();
                        }
                        return textDD;
                    }
                }
                if (type == 5) {
                    if (StringUtils.contains(textDt, "手机") && !StringUtils.contains(textDD, "未提供")) {
                        if (StringUtils.isNotBlank(customerImportQs.getMobelPhone())) {
                            return customerImportQs.getMobelPhone();
                        } else {
                            if (StringUtils.isNotBlank(customerImportQs.getMobelPhoneBak())) {
                                return customerImportQs.getMobelPhoneBak();
                            }
                        }
                        return textDD;
                    }
                }
                if (type == 6) {
                    if (StringUtils.contains(textDt, "邮件") && !StringUtils.contains(textDD, "未提供")) {
                        if (StringUtils.isNotBlank(customerImportQs.getEmail())) {
                            return customerImportQs.getEmail();
                        } else {
                            if (StringUtils.isNotBlank(customerImportQs.getEmailBak())) {
                                return customerImportQs.getEmailBak();
                            }
                        }
                        return textDD;
                    }
                }
                if (type == 7) {
                    if (StringUtils.contains(textDt, "QQ") && !StringUtils.contains(textDD, "未提供")) {
                        Matcher m = PATTERN_QQ.matcher(textDD);
                        if (m.find()) {
                            if (StringUtils.isNotBlank(customerImportQs.getQq())) {
                                return customerImportQs.getQq();
                            } else {
                                if (StringUtils.isNotBlank(customerImportQs.getQqBak())) {
                                    return customerImportQs.getQqBak();
                                }
                            }
                            return m.group();
                        }
                    }
                }
                i++;
            }

            //工商信息
            //工商信息获取
            String businessHtml = customerImportQs.getBusinessInfoHtml();
            Document businessHtmlDoc = Jsoup.parse(businessHtml);
            Elements elementList = businessHtmlDoc.select("tr");
            for (Element element : elementList) {
                Elements elementTds = element.select("td");
                if (elementTds != null && !elementTds.isEmpty()) {
                    Element elementTdFirst = elementTds.get(0);
                    if (type == 8) {
                        if(StringUtils.isNotBlank(customerImportQs.getMainProduction())){
                            return customerImportQs.getMainProduction();
                        }
                        if (StringUtils.isNotBlank(elementTdFirst.text()) && StringUtils.contains(elementTdFirst.text(), "主要经营")) {
                            return elementTds.get(1).text();
                        }
                    }
                    if (type == 9) {
                        if(StringUtils.isNotBlank(customerImportQs.getRegisterDate())){
                            return customerImportQs.getRegisterDate();
                        }
                        if (StringUtils.isNotBlank(elementTdFirst.text()) && StringUtils.contains(elementTdFirst.text(), "成立时间")) {
                            return elementTds.get(1).text();
                        }
                    }
                    if (type == 10) {
                        if(StringUtils.isNotBlank(customerImportQs.getManagementForms())){
                            return customerImportQs.getManagementForms();
                        }
                        if (StringUtils.isNotBlank(elementTdFirst.text()) && StringUtils.contains(elementTdFirst.text(), "职员人数")) {
                            return elementTds.get(1).text();
                        }
                    }
                    if (type == 11) {
                        if(StringUtils.isNotBlank(customerImportQs.getRegisteredCapital())){
                            return customerImportQs.getRegisteredCapital();
                        }
                        if (StringUtils.isNotBlank(elementTdFirst.text()) && StringUtils.contains(elementTdFirst.text(), "注册资本")) {
                            return elementTds.get(1).text();
                        }
                    }
                    if (type == 12) {
                        if(StringUtils.isNotBlank(customerImportQs.getIndustry())){
                            return customerImportQs.getIndustry();
                        }
                        if (StringUtils.isNotBlank(elementTdFirst.text()) && StringUtils.contains(elementTdFirst.text(), "所属分类")) {
                            return elementTds.get(1).text();
                        }
                    }

                }
            }


        } else {

            //企查查
            //联系人
            String contantStr = customerImportQs.getContantStr();
            if (type == 1) {
                if(StringUtils.isNotBlank(customerImportQs.getAddress())){
                    return customerImportQs.getAddress();
                }
                return StringUtils.substringBetween(contantStr, "地址：", "附近企业");
            }
            if (type == 2) {
                if(StringUtils.isNotBlank(customerImportQs.getPhone())){
                    return customerImportQs.getPhone();
                }
                if (StringUtils.isNotBlank(customerImportQs.getPhoneBak())) {
                    return customerImportQs.getPhoneBak();
                }
                Matcher m = PATTERN_PHONE.matcher(contantStr);
                if (m.find()) {
                    return m.group();
                }
            }

            if (type == 5) {
                if(StringUtils.isNotBlank(customerImportQs.getMobelPhone())){
                    return customerImportQs.getMobelPhone();
                }
                if (StringUtils.isNotBlank(customerImportQs.getMobelPhoneBak())) {
                    return customerImportQs.getMobelPhoneBak();
                }
                Matcher m = PATTERN_MOBEL_PHONE.matcher(contantStr);
                if (m.find()) {
                    return m.group();
                }
            }

            if (type == 6) {
                if(StringUtils.isNotBlank(customerImportQs.getEmail())){
                    return customerImportQs.getEmail();
                }
                if (StringUtils.isNotBlank(customerImportQs.getEmailBak())) {
                    return customerImportQs.getEmailBak();
                }
                Matcher m = PATTERN_EMAIL.matcher(contantStr);
                if (m.find()) {
                    return m.group();
                }
            }
            if (type == 7) {
                if(StringUtils.isNotBlank(customerImportQs.getQq())){
                    return customerImportQs.getQq();
                }
                if (StringUtils.isNotBlank(customerImportQs.getQqBak())) {
                    return customerImportQs.getQqBak();
                }
                Matcher m = PATTERN_QQ.matcher(contantStr);
                if (m.find()) {
                    return m.group();
                }
            }


            //工商信息
            //工商信息获取
            String businessInfoStr = customerImportQs.getBusinessInfoStr();
            if (type == 9) {
                if(StringUtils.isNotBlank(customerImportQs.getRegisterDate())){
                    return customerImportQs.getRegisterDate();
                }
                if (StringUtils.contains(businessInfoStr, "成立时间")) {
                    String createTime = StringUtils.substring(StringUtils.substringAfter(businessInfoStr, "成立时间"), 0, 10);
                    return createTime;
                }
            }

            if (type == 10) {
                if(StringUtils.isNotBlank(customerImportQs.getManagementForms())){
                    return customerImportQs.getManagementForms();
                }
                if (StringUtils.contains(businessInfoStr, "人员规模")) {
                    String managementForms = StringUtils.substringBetween(businessInfoStr, "人员规模", "人");
                    return managementForms;
                }
            }

            if (type == 11) {
                if(StringUtils.isNotBlank(customerImportQs.getRegisteredCapital())){
                    return customerImportQs.getRegisteredCapital();
                }
                if (StringUtils.contains(businessInfoStr, "注册资本")) {
                    String registeredCapital = StringUtils.substringBetween(businessInfoStr, "注册资本", "万");
                    registeredCapital = StringUtils.remove(registeredCapital,"(");
                    return registeredCapital;
                }
            }
            if (type == 13) {
                if(StringUtils.isNotBlank(customerImportQs.getIndustry())){
                    return customerImportQs.getIndustry();
                }
                if (StringUtils.contains(businessInfoStr, "所属行业")) {
                    String industry = StringUtils.substringBetween(businessInfoStr, "所属行业", "核准日期");
                    return industry;
                }
            }
            if (type == 14) {
                if(StringUtils.isNotBlank(customerImportQs.getLegalPsnName())){
                    return customerImportQs.getLegalPsnName();
                }
                if (StringUtils.contains(businessInfoStr, "法定代表人")) {
                    String legalPsnName = StringUtils.substringBetween(businessInfoStr, "法定代表人", "他关联");
                    return legalPsnName;
                }
            }


        }


        return null;
    }
}

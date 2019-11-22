package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Customer;
import com.example.springboot.entity.CustomerImportQs;
import com.example.springboot.entity.Customeruser;
import com.example.springboot.mapper.CustomerMapper;
import com.example.springboot.service.ICustomerService;
import com.example.springboot.utils.ResponseData;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Autowired
    CustomeruserServiceImpl customeruserService;

    @Autowired
    CustomerImportQsServiceImpl customerImportQsService;

    @Autowired
    CustomerServiceImpl customerService;


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
            String managementForms = this.getDataByHtml(customerImportQsEach, 10);
            if (StringUtils.isNotBlank(managementForms)) {
                customerOld.setStaffSize(managementForms);
            }
        }
        customerOld.setJobID(jobId);
        getBaseMapper().updateById(customerOld);
    }

    @Override
    public List<String> listOne(String sourceName) {
        List<String> stringList = getBaseMapper().listOne(sourceName);
        return stringList;
    }

    /**
     * 从企查查上爬取数据
     *
     * @param htmlStr
     * @return
     */
    @Override
    public ResponseData<Customer> handleDataByQcc(String htmlStr, String name) {
        Customer customer = new Customer();
        customer.setName(name);
        Document doc = Jsoup.parse(htmlStr);
        //企业基本信息
        Elements elements = doc.select("#Cominfo>table>tbody>tr");
        if (elements == null || elements.isEmpty()) {
            return ResponseData.ERRORMSG("抓取到");
        }
        //法定代表人和注册资金
        Element element0 = elements.get(0);
        Elements tds0 = element0.select("td");
        customer.setLegalRepresentative(tds0.get(1).select("a>h2").html());
        String registeredCapitalStr = tds0.get(3).html();
        if (StringUtils.equals(registeredCapitalStr, "-")) {
            customer.setRegisteredCapital(0);
        } else if (StringUtils.contains(registeredCapitalStr, '.')) {
            registeredCapitalStr = StringUtils.substringBefore(registeredCapitalStr, ".");
            if (StringUtils.isNumeric(registeredCapitalStr)) {
                customer.setRegisteredCapital(Integer.parseInt(registeredCapitalStr));
            }
        } else {
            registeredCapitalStr = StringUtils.substringBefore(registeredCapitalStr, "万");
            if (StringUtils.isNumeric(registeredCapitalStr)) {
                customer.setRegisteredCapital(Integer.parseInt(registeredCapitalStr));
            }
        }

        //成立日期
        Element element2 = elements.get(2);
        Elements tds2 = element2.select("td");
        String createTime = tds2.get(3).html();
        if (StringUtils.equals(registeredCapitalStr, "-")) {
            customer.setCreateTime("1970-01-01");
        } else {
            createTime = StringUtils.replace(createTime, "年", "-");
            createTime = StringUtils.replace(createTime, "月", "-");
            createTime = StringUtils.replace(createTime, "日", "-");
            customer.setCreateTime(createTime);
        }
        //统一社会信用代码
        Element element3 = elements.get(3);
        Elements tds3 = element3.select("td");
        String organizationCodeStr = tds3.get(1).html();
        if (StringUtils.isNotBlank(organizationCodeStr)) {
            customer.setOrganizationCode(organizationCodeStr);
        }

        //所属行业
        Element element5 = elements.get(5);
        Elements tds5 = element5.select("td");
        String businessIndustry = tds5.get(3).html();
        if (StringUtils.isNotBlank(businessIndustry)) {
            customer.setBusinessIndustry(businessIndustry);
        }

        //人员规模
        Element element9 = elements.get(9);
        Elements tds9 = element9.select("td");
        String StaffSize = tds9.get(1).html();
        if (StringUtils.isNotBlank(StaffSize)) {
            customer.setStaffSize(StaffSize);
        }
        //公司地址.text()
        Element element10 = elements.get(10);
        Elements tds10 = element10.select("td");
        String address = tds10.get(1).text();
        if (StringUtils.isNotBlank(address)) {
            customer.setStaffSize(address);
        }
        //公司地址
        Element element11 = elements.get(11);
        Elements tds11 = element11.select("td");
        String mainProducts = tds11.get(1).html();
        if (StringUtils.isNotBlank(mainProducts)) {
            customer.setMainProducts(mainProducts);
        }
//        联系人信息
        Customeruser customeruser = new Customeruser();
        Elements elementPsns = doc.select("#company-top>div[class=row]>div[class=content]>div[class=dcontent]>div");
        //电话和官网
        Element elements0 = elementPsns.get(0);
        Elements spansPhone = elements0.select("span[class=fc]>span[class=cvlu]>a");
        if (spansPhone != null && !spansPhone.isEmpty()) {
            String phone = spansPhone.get(0).html();
            if (StringUtils.startsWith(phone, "1") || StringUtils.startsWith(phone, "+") || StringUtils.startsWith(phone, "86")) {
                customeruser.setMobilePhone(phone);
            } else {
                customeruser.setPhone(phone);
            }
        }
        Elements spansWebs = elements0.select("span[class=cvlu] >a");
        if (spansWebs != null && !spansWebs.isEmpty()) {
            String spansWeb = spansWebs.get(2).html();
            if (StringUtils.isNotBlank(spansWeb)) {
                customer.setWebSite(spansWeb);
            }
        }
        //邮箱
        Element emails = elementPsns.get(1);
        String email = emails.select("span[class=fc] >span[class=cvlu]>a").get(0).text();
        if (StringUtils.isNotBlank(email)) {
            customeruser.setEmail(email);
        }
        customeruser.setRealName("法人");
        List<Customeruser> customeruserList = new ArrayList<>();
        customeruserList.add(customeruser);
        customer.setCustomeruserList(customeruserList);
        return ResponseData.SUCCESS(customer);
    }

    @Override
    public ResponseData<Customer> saveInfoToDb(Customer customer) {
        QueryWrapper<Customer> queryWrapperOld = new QueryWrapper<>();
        queryWrapperOld.eq("name", customer.getName());
        Customer customerOld = getBaseMapper().selectOne(queryWrapperOld);
        if (customerOld == null) {
            return ResponseData.ERRORMSG("参数错误，没有查询到 制定客户");
        }
        //企业基本信息
        if (StringUtils.isBlank(customerOld.getAddress()) && StringUtils.isNotBlank(customer.getAddress())) {
            customerOld.setAddress(customer.getAddress());
        }
        if (StringUtils.isBlank(customerOld.getCompanyDescript()) && StringUtils.isNotBlank(customer.getCompanyDescript())) {
            customerOld.setCompanyDescript(customer.getCompanyDescript());
        }
        if (StringUtils.isBlank(customerOld.getWebSite()) && StringUtils.isNotBlank(customer.getWebSite())) {
            customerOld.setWebSite(customer.getWebSite());
        }
        if (StringUtils.isBlank(customerOld.getWebSite()) && StringUtils.isNotBlank(customer.getWebSite())) {
            customerOld.setWebSite(customer.getWebSite());
        }
        if (StringUtils.isBlank(customerOld.getCreateTime()) && StringUtils.isNotBlank(customer.getCreateTime())) {
            customerOld.setCreateTime(customer.getCreateTime());
        }
        if (StringUtils.isBlank(customerOld.getMainProducts()) && StringUtils.isNotBlank(customer.getMainProducts())) {
            customerOld.setMainProducts(customer.getMainProducts());
        }
        if (StringUtils.isBlank(customerOld.getBusinessIndustry()) && StringUtils.isNotBlank(customer.getBusinessIndustry())) {
            customerOld.setBusinessIndustry(customer.getBusinessIndustry());
        }
        if (customerOld.getRegisteredCapital() == null && customer.getRegisteredCapital() != null) {
            customerOld.setRegisteredCapital(customer.getRegisteredCapital());
        }
        if (StringUtils.isBlank(customerOld.getStaffSize()) && StringUtils.isNotBlank(customer.getStaffSize())) {
            customerOld.setStaffSize(customer.getStaffSize());
        }
        if (StringUtils.isBlank(customerOld.getLegalRepresentative()) && StringUtils.isNotBlank(customer.getLegalRepresentative())) {
            customerOld.setLegalRepresentative(customer.getLegalRepresentative());
        }
        if (StringUtils.isBlank(customerOld.getOrganizationCode()) && StringUtils.isNotBlank(customer.getOrganizationCode())) {
            customerOld.setOrganizationCode(customer.getOrganizationCode());
        }
        getBaseMapper().updateById(customerOld);
        //人员信息处理
        List<Customeruser> customeruserList = customer.getCustomeruserList();
        if (customeruserList == null || customeruserList.isEmpty()) {
            return ResponseData.SUCCESSMSG("没有联系人信息");
        }
        QueryWrapper<Customeruser> customeruserQueryWrapper = new QueryWrapper<>();
        customeruserQueryWrapper.eq("CustomerID", customerOld.getId());
        customeruserQueryWrapper.eq("del_flag", 1);
        List<Customeruser> customeruserListOld = customeruserService.list(customeruserQueryWrapper);

        for (Customeruser customeruser : customeruserList) {
            int i = 0;
            for (Customeruser customeruserOld : customeruserListOld) {
                if (StringUtils.equals(customeruserOld.getMobilePhone(), customeruser.getMobilePhone())) {
                    i++;
                    if (StringUtils.isBlank(customeruserOld.getRealName()) && StringUtils.isNotBlank(customeruser.getRealName())) {
                        customeruserOld.setRealName(customeruser.getRealName());
                    }
                    if (StringUtils.isBlank(customeruserOld.getPhone()) && StringUtils.isNotBlank(customeruser.getPhone())) {
                        customeruserOld.setPhone(customeruser.getPhone());
                    }
                    if (StringUtils.isBlank(customeruserOld.getEmail()) && StringUtils.isNotBlank(customeruser.getEmail())) {
                        customeruserOld.setEmail(customeruser.getEmail());
                    }
                    if (StringUtils.isBlank(customeruserOld.getQq()) && StringUtils.isNotBlank(customeruser.getQq())) {
                        customeruserOld.setQq(customeruser.getQq());
                    }
                    if (StringUtils.isBlank(customeruserOld.getPosition()) && StringUtils.isNotBlank(customeruser.getPosition())) {
                        customeruserOld.setPosition(customeruser.getPosition());
                    }
                    customeruserService.updateById(customeruserOld);
                }
            }
            if (i == 0) {
                customeruser.setCustomerID(customerOld.getId());
                customeruser.setDelFlag(true);
                customeruser.setRemarks("处理");
                customeruserService.save(customeruser);
            }
        }

        return ResponseData.SUCCESS();
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
                        if (StringUtils.isNotBlank(customerImportQs.getMainProduction())) {
                            return customerImportQs.getMainProduction();
                        }
                        if (StringUtils.isNotBlank(elementTdFirst.text()) && StringUtils.contains(elementTdFirst.text(), "主要经营")) {
                            return elementTds.get(1).text();
                        }
                    }
                    if (type == 9) {
                        if (StringUtils.isNotBlank(customerImportQs.getRegisterDate())) {
                            return customerImportQs.getRegisterDate();
                        }
                        if (StringUtils.isNotBlank(elementTdFirst.text()) && StringUtils.contains(elementTdFirst.text(), "成立时间")) {
                            return elementTds.get(1).text();
                        }
                    }
                    if (type == 10) {
                        if (StringUtils.isNotBlank(customerImportQs.getManagementForms())) {
                            return customerImportQs.getManagementForms();
                        }
                        if (StringUtils.isNotBlank(elementTdFirst.text()) && StringUtils.contains(elementTdFirst.text(), "职员人数")) {
                            return elementTds.get(1).text();
                        }
                    }
                    if (type == 11) {
                        if (StringUtils.isNotBlank(customerImportQs.getRegisteredCapital())) {
                            return customerImportQs.getRegisteredCapital();
                        }
                        if (StringUtils.isNotBlank(elementTdFirst.text()) && StringUtils.contains(elementTdFirst.text(), "注册资本")) {
                            return elementTds.get(1).text();
                        }
                    }
                    if (type == 12) {
                        if (StringUtils.isNotBlank(customerImportQs.getIndustry())) {
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
                if (StringUtils.isNotBlank(customerImportQs.getAddress())) {
                    return customerImportQs.getAddress();
                }
                return StringUtils.substringBetween(contantStr, "地址：", "附近企业");
            }
            if (type == 2) {
                if (StringUtils.isNotBlank(customerImportQs.getPhone())) {
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
                if (StringUtils.isNotBlank(customerImportQs.getMobelPhone())) {
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
                if (StringUtils.isNotBlank(customerImportQs.getEmail())) {
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
                if (StringUtils.isNotBlank(customerImportQs.getQq())) {
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
                if (StringUtils.isNotBlank(customerImportQs.getRegisterDate())) {
                    return customerImportQs.getRegisterDate();
                }
                if (StringUtils.contains(businessInfoStr, "成立时间")) {
                    String createTime = StringUtils.substring(StringUtils.substringAfter(businessInfoStr, "成立时间"), 0, 10);
                    return createTime;
                }
            }

            if (type == 10) {
                if (StringUtils.isNotBlank(customerImportQs.getManagementForms())) {
                    return customerImportQs.getManagementForms();
                }
                if (StringUtils.contains(businessInfoStr, "人员规模")) {
                    String managementForms = StringUtils.substringBetween(businessInfoStr, "人员规模", "人");
                    return managementForms;
                }
            }

            if (type == 11) {
                if (StringUtils.isNotBlank(customerImportQs.getRegisteredCapital())) {
                    return customerImportQs.getRegisteredCapital();
                }
                if (StringUtils.contains(businessInfoStr, "注册资本")) {
                    String registeredCapital = StringUtils.substringBetween(businessInfoStr, "注册资本", "万");
                    registeredCapital = StringUtils.remove(registeredCapital, "(");
                    return registeredCapital;
                }
            }
            if (type == 13) {
                if (StringUtils.isNotBlank(customerImportQs.getIndustry())) {
                    return customerImportQs.getIndustry();
                }
                if (StringUtils.contains(businessInfoStr, "所属行业")) {
                    String industry = StringUtils.substringBetween(businessInfoStr, "所属行业", "核准日期");
                    return industry;
                }
            }
            if (type == 14) {
                if (StringUtils.isNotBlank(customerImportQs.getLegalPsnName())) {
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


    //根据企业名字组装客户信息
    public ResponseData<Customer> getCUstomerInfoQcc(String name) {
        List<Customeruser> customeruserList = Lists.newLinkedList();
        Customer customer = new Customer();
        customer.setName(name);
        QueryWrapper<CustomerImportQs> qsQueryWrapper = new QueryWrapper<>();
         qsQueryWrapper.eq("source_Type", "企查查");
        qsQueryWrapper.eq("enterprise_Name", name);
        List<CustomerImportQs> list = customerImportQsService.list(qsQueryWrapper);
        if (list == null || list.isEmpty()) {
            return ResponseData.ERRORMSG("企查查没有改企业信息：" + name);
        }
        CustomerImportQs customerImportQs = list.get(0);
        String businessInfoHtml = customerImportQs.getBusinessInfoHtml();
        Document documentBusiness = Jsoup.parse(businessInfoHtml);
        Elements elementsBusinessList = documentBusiness.select("tbody>tr");
        //法定代表人和注册资金
        Element element = elementsBusinessList.get(0);
        String businessPsnH2 = element.select("h2").get(0).text();
        if (StringUtils.isNotBlank(businessPsnH2)) {
            customer.setLegalRepresentative(businessPsnH2);
        }
        String businessPsnca = element.select("td").get(3).text();
        if (StringUtils.isNotBlank(businessPsnca)) {
            if (StringUtils.contains(businessPsnca, ".")) {
                Integer i = Integer.parseInt(StringUtils.substringBefore(businessPsnca, "."));
                customer.setRegisteredCapital(i);
            } else if (StringUtils.contains(businessPsnca, "万")) {
                Integer i = Integer.parseInt(StringUtils.substringBefore(businessPsnca, "万"));
                customer.setRegisteredCapital(i);
            } else if (StringUtils.contains(businessPsnca, "-")) {
                customer.setRegisteredCapital(0);
            }
        }
        //成立时间
        element = elementsBusinessList.get(2);
        String text = element.select("td").get(3).text();
        if (StringUtils.isNotBlank(text)) {
            customer.setCreateTime(text);
        }
        //统一征信代码
        element = elementsBusinessList.get(3);
        text = element.select("td").get(1).text();
        if (StringUtils.isNotBlank(text)) {
            customer.setOrganizationCode(text);
        }
        //所属行业
        element = elementsBusinessList.get(5);
        text = element.select("td").get(3).text();
        if (StringUtils.isNotBlank(text)) {
            customer.setBusinessIndustry(text);
        }
        //人员规模
        element = elementsBusinessList.get(9);
        text = element.select("td").get(1).text();
        if (StringUtils.isNotBlank(text)) {
            customer.setStaffSize(text);
        }
        //企业地址
        element = elementsBusinessList.get(10);
        text = element.select("td").get(1).text();
        if (StringUtils.isNotBlank(text)) {
            customer.setAddress(text);
        }
        //经营范围
        element = elementsBusinessList.get(11);
        text = element.select("td").get(1).text();
        if (StringUtils.isNotBlank(text)) {
            customer.setMainProducts(text);
        }
        //获取人员信息
        Document documentContent = Jsoup.parse(customerImportQs.getContantHtml());
        Elements selectContents = documentContent.select("body>div>div");
        Customeruser customeruser = new Customeruser();
        //电话官网
        element = selectContents.get(0);
        text = element.select(">span>span").get(1).text();
        if (StringUtils.isNotBlank(text)) {
            customeruser.setPhone(text);
        }
        text = element.select(">span").get(2).text();
        if (StringUtils.isNotBlank(text)) {
            customer.setWebSite(text);
        }
        //邮箱地址
        element = selectContents.get(1);
        text = element.select(">span>span").get(1).text();
        if (StringUtils.isNotBlank(text)) {
            customeruser.setEmail(text);
        }
        if (StringUtils.isBlank(customer.getAddress())) {
            text = element.select(">span").get(2).text();
            if (StringUtils.isNotBlank(text)) {
                customer.setAddress(text);
            }
        }
        if (StringUtils.isBlank(customeruser.getRealName())) {
            customeruser.setRealName("法人");
        }
        customeruserList.add(customeruser);
        customer.setCustomeruserList(customeruserList);
        return ResponseData.SUCCESS(customer);
    }

    //根据企业名字组装客户信息
    public ResponseData<Customer> getCUstomerInfoSqw(String name) {
        List<Customeruser> customeruserList = Lists.newLinkedList();
        Customer customer = new Customer();
        //获取顺企网
        customer.setName(name);
        QueryWrapper<CustomerImportQs> qsQueryWrapper = new QueryWrapper<>();
        qsQueryWrapper.eq("enterprise_Name", name);
        qsQueryWrapper.eq("source_Type", "顺企网");

        List<CustomerImportQs> list = customerImportQsService.list(qsQueryWrapper);
        if (list == null || list.isEmpty()) {
            return ResponseData.ERRORMSG("顺企网没有该企业数据");
        }
        CustomerImportQs customerImportQs = list.get(0);
        String businessInfoHtml = customerImportQs.getBusinessInfoHtml();
        Document documentBusiness = Jsoup.parse(businessInfoHtml);
        Elements elementsBusinessList = documentBusiness.select("table>tbody>tr");

        for (Element elementEach : elementsBusinessList) {
            Elements select = elementEach.select("tr>td");
            String text1 = select.get(0).text();
            if (StringUtils.contains(text1, "主要经营产品") && StringUtils.isNotBlank(select.get(1).text())) {
                customer.setMainProducts(select.get(1).text());
            }
            if (StringUtils.contains(text1, "成立时间") && StringUtils.isNotBlank(select.get(1).text())) {
                text1 = StringUtils.replace(select.get(1).text(), "年", "-");
                text1 = StringUtils.replace(text1, "月", "-");
                text1 = StringUtils.replace(text1, "日", "-");
                customer.setCreateTime(text1);
            }
            if (StringUtils.contains(text1, "注册资本") && StringUtils.isNotBlank(select.get(1).text())) {
                text1 = select.get(1).text();
                text1 =StringUtils.replace(text1,"(","");
                text1 =StringUtils.replace(text1,")","");
                text1 =StringUtils.replace(text1,"-","");
                text1 =StringUtils.replace(text1,"_","");
                text1 =StringUtils.replace(text1,":","");
                text1 =StringUtils.replace(text1," ","");
                if (StringUtils.contains(text1, ".")) {
                    int i = Integer.parseInt(StringUtils.substringBefore(text1, "."));
                    customer.setRegisteredCapital(i);
                } else if (StringUtils.contains(text1, "万")) {
                    int i = Integer.parseInt(StringUtils.substringBefore(text1, "万"));
                    customer.setRegisteredCapital(i);
                }
            }
            if (StringUtils.contains(text1, "所属分类") && StringUtils.isNotBlank(select.get(1).text())) {
                customer.setCreateTime(select.get(1).text());
            }
        }


        Customeruser customeruser = new Customeruser();
        Document documentContent = Jsoup.parse(customerImportQs.getContantHtml());

        Elements dt = documentContent.select("body>div>div>dl>dt");
        Elements dd = documentContent.select("body>div>div>dl>dd");
        int i = 0;
        for (Element dtEach : dt) {
            String textDt = dtEach.text();
            String textDd = dd.get(i).text();
            if (StringUtils.contains(textDt, "地址")) {
                if (StringUtils.isNotBlank(textDd) && StringUtils.isBlank(customer.getAddress())) {
                    customer.setAddress(textDd);
                }
            }
            if (StringUtils.contains(textDt, "电话")) {
                if (StringUtils.isNotBlank(textDd) ) {
                    customeruser.setPhone(textDd);
                }
            }
            if (StringUtils.contains(textDt, "手机")) {
                if (StringUtils.isNotBlank(textDd)) {
                    customeruser.setMobilePhone(textDd);
                }
            }
            if (StringUtils.contains(textDt, "厂长") || StringUtils.contains(textDt, "经理")) {
                customeruser.setPosition(textDt);
                if (StringUtils.isNotBlank(textDd)) {
                    customeruser.setRealName(textDd);
                }
            }
            if (StringUtils.contains(textDt, "邮箱") ) {
                if (StringUtils.isNotBlank(textDd)) {
                    customeruser.setEmail(textDd);
                }
            }
            if (StringUtils.contains(textDt, "QQ") ) {
                if (StringUtils.isNotBlank(textDd)) {
                    customeruser.setEmail(textDd);
                }
            }
            i++;
        }


        customeruserList.add(customeruser);
        customer.setCustomeruserList(customeruserList);
        return ResponseData.SUCCESS(customer);
    }



    //保存数据到数据库
    public ResponseData getCUstomerInfoToDb(Customer customer) {

        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",customer.getName());
        List<Customer> list = customerService.list(queryWrapper);
        if(list == null || list.isEmpty()){
            return ResponseData.ERRORMSG("客户不存在"+customer.getName());
        }
        Customer customerOld = list.get(0);
        if(StringUtils.isBlank(customerOld.getAddress()) && StringUtils.isNotBlank(customer.getAddress())){
            customerOld.setAddress(customer.getAddress());
        }
        if(StringUtils.isBlank(customerOld.getCompanyDescript()) && StringUtils.isNotBlank(customer.getCompanyDescript())){
            customerOld.setCompanyDescript(customer.getCompanyDescript());
        }
        if(StringUtils.isBlank(customerOld.getCreateTime()) && StringUtils.isNotBlank(customer.getCreateTime())){
            customerOld.setCreateTime(customer.getCreateTime());
        }
        if(StringUtils.isBlank(customerOld.getMainProducts()) && StringUtils.isNotBlank(customer.getMainProducts())){
            customerOld.setMainProducts(customer.getMainProducts());
        }
        if(StringUtils.isBlank(customerOld.getBusinessIndustry()) && StringUtils.isNotBlank(customer.getBusinessIndustry())){
            customerOld.setBusinessIndustry(customer.getBusinessIndustry());
        }
        if(customerOld.getRegisteredCapital()== null && customer.getRegisteredCapital() !=null){
            customerOld.setRegisteredCapital(customer.getRegisteredCapital());
        }
        if(StringUtils.isBlank(customerOld.getLegalRepresentative()) && StringUtils.isNotBlank(customer.getLegalRepresentative())){
            customerOld.setLegalRepresentative(customer.getLegalRepresentative());
        }
        if(customerOld.getXSide() == null && customer.getXSide() != null){
            customerOld.setXSide(customer.getXSide());
        }
        if(customerOld.getYSide() == null && customer.getYSide() != null){
            customerOld.setYSide(customer.getYSide());
        }
        if(StringUtils.equals(customerOld.getAreaCode(),customer.getAreaCode()) ){
            customerOld.setAreaCode(customer.getAreaCode());
        }
        if(customerOld.getAddress()== null && customer.getAddress() !=null){
            customerOld.setAddress(customer.getAddress());
        }
        customerService.updateById(customerOld);
        QueryWrapper<Customeruser> customeruserQueryWrapper = new QueryWrapper<>();
        customeruserQueryWrapper.eq("CustomerID",customerOld.getId());
        customeruserQueryWrapper.eq("del_flag",true);
        List<Customeruser> customeruserListOld = customeruserService.list(customeruserQueryWrapper);
        List<Customeruser> customeruserList = customer.getCustomeruserList();
        if(customeruserList == null || customeruserList.isEmpty()){
            return ResponseData.SUCCESS();
        }
        Customeruser customeruser = customeruserList.get(0);
        if(customeruserListOld == null || customeruserListOld.isEmpty()){
            //保存
            customeruser.setCustomerID(customerOld.getId());
            customeruser.setDelFlag(true);
            customeruser.setAddTime(LocalDateTime.now());
            customeruserService.save(customeruser);
        }else{
            int i = 0;
            for(Customeruser customeruserOld:customeruserListOld){
                if(StringUtils.equals(customeruser.getMobilePhone(),customeruserOld.getMobilePhone()) || StringUtils.equals(customeruser.getPhone(),customeruserOld.getPhone())) {
                    if(StringUtils.isBlank(customeruserOld.getMobilePhone()) &&StringUtils.isNotBlank(customeruser.getMobilePhone())  ){
                        customeruserOld.setMobilePhone(customeruser.getMobilePhone());
                        i++;
                    }
                    if(StringUtils.isBlank(customeruserOld.getRealName()) &&StringUtils.isNotBlank(customeruser.getRealName())  ){
                        customeruserOld.setRealName(customeruser.getRealName());
                        i++;
                    }
                    if(StringUtils.isBlank(customeruserOld.getRealName()) &&StringUtils.isNotBlank(customeruser.getRealName())  ){
                        customeruserOld.setRealName(customeruser.getRealName());
                        i++;
                    }
                    if(StringUtils.isBlank(customeruserOld.getPosition()) &&StringUtils.isNotBlank(customeruser.getPosition())  ){
                        customeruserOld.setPosition(customeruser.getPosition());
                        i++;
                    }
                    if(StringUtils.isBlank(customeruserOld.getEmail()) &&StringUtils.isNotBlank(customeruser.getEmail())  ){
                        customeruserOld.setEmail(customeruser.getEmail());
                        i++;
                    }
                    if(StringUtils.isBlank(customeruserOld.getQq()) &&StringUtils.isNotBlank(customeruser.getQq())  ){
                        customeruserOld.setQq(customeruser.getQq());
                        i++;
                    }
                }
                if(i == 0){
                    customeruser.setCustomerID(customerOld.getId());
                    customeruser.setDelFlag(true);
                    customeruser.setAddTime(LocalDateTime.now());
                    customeruserService.save(customeruser);
                }else{
                    customeruserService.updateById(customeruserOld);
                }
            }

        }



        return ResponseData.SUCCESS(customer);
    }

    @Override
    public List<Customer> selectNotInPoolCustomer() {
        return getBaseMapper().selectNotInPoolCustomer();
    }

}

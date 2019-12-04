package com.example.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.springboot.entity.*;
import com.example.springboot.service.impl.*;
import com.example.springboot.utils.DateUtil;
import com.example.springboot.utils.HttpClientTool;
import com.example.springboot.utils.MyStringUtil;
import com.example.springboot.utils.ResponseData;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.ws.Response;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Date;
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

    @Autowired
    DataCrawlerServiceImpl dataCrawlerService;

    @Autowired
    CookiesServiceImpl cookiesService;

    @Autowired
    DataFromAlibabaServiceImpl dataFromAlibabaService;


    /**
     * 处理顺企网数据（1.4万条）
     *
     * @return
     */
    @GetMapping("sqw")
    public Response sqwCustomerHandle() {
        List<CustomerImportSqw> list = customerImportSqwService.list();
        for (CustomerImportSqw customerImportSqw : list) {
            try {
                handleBySqw(customerImportSqw);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return null;
    }

    public static final Pattern PATTERN_QQ = Pattern.compile("[1-9]([0-9]{5,11})");
    public static final Pattern PATTERN_PHONE = Pattern.compile("[0-9-()（）]{7,18}");
    public static final Pattern PATTERN_MOBEL_PHONE = Pattern.compile("0?(13|14|15|17|18|19)[0-9]{9}");
    public static final Pattern PATTERN_EMAIL = Pattern.compile("\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}");

    private void handleBySqw(CustomerImportSqw customerImportSqw) {
        Customer customer = customerService.getOne(new QueryWrapper<Customer>().eq("name", customerImportSqw.getEnterpriseName()));
        if (customer == null) {
            return;
        }
        //工商信息获取
        String businessHtml = customerImportSqw.getBusinessHtml();
        Document businessHtmlDoc = Jsoup.parse(businessHtml);
        Elements elementList = businessHtmlDoc.select("tr");
        int busCount = 0;
        for (Element element : elementList) {
            Elements elementTds = element.select("td");
            if (elementTds != null && !elementTds.isEmpty()) {
                Element elementTdFirst = elementTds.get(0);
                if (StringUtils.isNotBlank(elementTdFirst.text()) && StringUtils.contains(elementTdFirst.text(), "主要经营")) {
                    if (StringUtils.isBlank(customer.getMainProducts())) {
                        customer.setMainProducts(elementTds.get(1).text());
                        busCount++;
                    }
                }
                if (StringUtils.isNotBlank(elementTdFirst.text()) && StringUtils.contains(elementTdFirst.text(), "成立时间")) {
                    if (customer.getCreateTime() == null) {
                        Date date = null;
                        try {
                            if (StringUtils.contains(elementTds.get(1).text(), "-")) {
                                date = DateUtils.parseDate(elementTds.get(1).text(), DateUtil.yyyy_MM_dd_EN);
                            }
                            if (StringUtils.contains(elementTds.get(1).text(), "年")) {
                                date = DateUtils.parseDate(elementTds.get(1).text(), DateUtil.yyyy_MM_DD_CN);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        customer.setCreateTime(date);
                        busCount++;
                    }
                }
                if (StringUtils.isNotBlank(elementTdFirst.text()) && StringUtils.contains(elementTdFirst.text(), "职员人数")) {
                    if (StringUtils.isBlank(customer.getStaffSize())) {
                        customer.setStaffSize(elementTds.get(1).text());
                        busCount++;
                    }
                }
                if (StringUtils.isNotBlank(elementTdFirst.text()) && StringUtils.contains(elementTdFirst.text(), "注册资本")) {
                    if (customer.getRegisteredCapital() == null) {
                        if (StringUtils.isNumeric(elementTds.get(1).text())) {
                            customer.setRegisteredCapital(Integer.parseInt(elementTds.get(1).text()));
                            busCount++;
                        }
                    }
                }
                if (StringUtils.isNotBlank(elementTdFirst.text()) && StringUtils.contains(elementTdFirst.text(), "所属分类")) {
                    if (StringUtils.isBlank(customer.getBusinessIndustry())) {
                        customer.setBusinessIndustry(elementTds.get(1).text());
                        busCount++;
                    }
                }

            }
        }

        if (StringUtils.isBlank(customer.getCompanyDescript())) {
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
        for (Element elementDt : dt) {
            String textDt = elementDt.text();
            String textDD = dd.get(i).text();
            if (StringUtils.contains(textDt, "公司地址") && StringUtils.isBlank(customer.getAddress())) {
                customer.setAddress(textDD);
            }
            if (StringUtils.contains(textDt, "电话") && !StringUtils.contains(textDD, "未提供")) {
                customeruser.setPhone(textDD);
            }
            if (StringUtils.contains(textDt, "经") || StringUtils.contains(textDt, "厂")) {
                customeruser.setPosition(textDt);
                customeruser.setRealName(textDD);
            }
            if (StringUtils.contains(textDt, "手机") && !StringUtils.contains(textDD, "未提供")) {
                customeruser.setMobilePhone(textDD);
            }
            if (StringUtils.contains(textDt, "邮件") && !StringUtils.contains(textDD, "未提供")) {
                customeruser.setEmail(textDD);
            }
            if (StringUtils.contains(textDt, "QQ") && !StringUtils.contains(textDD, "未提供")) {
                Matcher m = PATTERN_QQ.matcher(textDD);
                if (m.find()) {
                    customeruser.setEmail(m.group());

                }
            }


            i++;
        }
        if (StringUtils.isBlank(customeruser.getMobilePhone())) {
            Matcher matcher = PATTERN_MOBEL_PHONE.matcher(contentHtml);
            if (matcher.find()) {
                customeruser.setMobilePhone(matcher.group());
            }
        }
        if (StringUtils.isBlank(customeruser.getPhone())) {
            Matcher matcher = PATTERN_PHONE.matcher(contentHtml);
            if (matcher.find()) {
                customeruser.setPhone(matcher.group());
            }
        }
        if ((StringUtils.isNotBlank(customeruser.getPhone()) || StringUtils.isNotBlank(customeruser.getMobilePhone())) && StringUtils.isNotBlank(customeruser.getRealName())) {
            //检查是否重复

            List<Customeruser> customeruserList = customeruserService.listNotContent(customeruser.getCustomerID(), customeruser.getRealName(), customeruser.getMobilePhone(), customeruser.getPhone());
            if (customeruserList == null || customeruserList.isEmpty()) {
                customeruserService.save(customeruser);
            }
        }
        if (busCount > 0) {
            customerService.updateById(customer);
        }
    }


    //从顺企网上获取注册资金
    @GetMapping("registeredCapital")
    public ResponseData getEntInfo() {
        //随机数，用于批次任务
        int randomJob = (int) (1 + Math.random() * 1000);
        //注册资金完善
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("RegisteredCapital");
        List<Customer> customerList = customerService.list(queryWrapper);
        for (Customer customer : customerList) {
            String name = customer.getName();
            QueryWrapper<CustomerImportQs> qsQueryWrapper = new QueryWrapper<>();
            qsQueryWrapper.eq("enterprise_name", name);
            List<CustomerImportQs> customerImportQsList = customerImportQsService.list(qsQueryWrapper);
            if (customerImportQsList == null || customerImportQsList.isEmpty()) {
                continue;
            }
            Integer registeredCapital = null;
            for (CustomerImportQs customerImportQs : customerImportQsList) {
                String registeredCapitalEach = customerImportQs.getRegisteredCapital();
                if (StringUtils.isBlank(registeredCapitalEach) || !StringUtils.contains(registeredCapitalEach, "万")) {
                    continue;
                }
                try {
                    double parseDouble = Double.parseDouble(StringUtils.substringBefore(registeredCapitalEach, "万"));
                    registeredCapital = (int) parseDouble;
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
            if (registeredCapital != null) {
                Customer customerNew = new Customer();
                customerNew.setRegisteredCapital(registeredCapital);
                customerNew.setJobID(randomJob);
                UpdateWrapper<Customer> customerUpdateWrapper = new UpdateWrapper<>();
                customerUpdateWrapper.eq("id", customer.getId());
                customerService.update(customerNew, customerUpdateWrapper);
            }

        }


        return ResponseData.SUCCESS();
    }

    /**
     * 分配已经匹配的客户给用户
     *
     * @return
     */
    @GetMapping("distributionCustomer")
    public ResponseData distributionCustomer() {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("JobID");
        queryWrapper.isNull("handle_user_id");
        List<Customer> list = customerService.list(queryWrapper);
        if (list == null || list.isEmpty()) {
            return ResponseData.SUCCESSMSG("没有需要分配的");
        }
        int countUser = 0;
        List<Integer> listUserId = Lists.newLinkedList();
        listUserId.add(37);
        listUserId.add(38);
        listUserId.add(39);
//        listUserId.add(40);
        for (Customer customer : list) {
            countUser++;
            Integer userId = listUserId.get(countUser % 3);
            Customer customerNew = new Customer();
            customerNew.setHandleUserId(userId);
            QueryWrapper<Customer> customerQueryWrapper = new QueryWrapper<>();
            customerQueryWrapper.eq("id", customer.getId());
            customerService.update(customerNew, customerQueryWrapper);
        }


        return ResponseData.SUCCESSMSG("本次分配完成 " + countUser + " 个客户");
    }

    /**
     * 从抓取的数据中获取数据
     *
     * @return
     */
    @GetMapping("customerData")
    public ResponseData handleQSData() {
        //本次抓取号
        int jobId = (int) (1 + Math.random() * 10000);
        //获取所有新抓取的数据
        QueryWrapper<CustomerImportQs> qsQueryWrapper = new QueryWrapper<>();
        qsQueryWrapper.isNull("job_id");
        List<CustomerImportQs> customerImportQsList = customerImportQsService.list(qsQueryWrapper);
        if (customerImportQsList == null || customerImportQsList.isEmpty()) {
            return ResponseData.SUCCESSMSG("没有新抓取的数据");
        }
        //循环处理
        for (CustomerImportQs customerImportQsEach : customerImportQsList) {
            String enterpriseName = customerImportQsEach.getEnterpriseName();
            enterpriseName = StringUtils.remove(enterpriseName, " ");
            QueryWrapper<Customer> customerQueryWrapper = new QueryWrapper<>();
            customerQueryWrapper.eq("Name", enterpriseName);
            Customer customerOld = customerService.getOne(customerQueryWrapper);
            if (customerOld == null) {
                continue;
            }
            try {
                //工商信息处理
                customerService.getBussinessInfo(customerOld, customerImportQsEach, jobId);
                //联系人处理
                customeruserService.handleContenct(customerImportQsEach, customerOld, jobId);
                customerImportQsEach.setJobId(jobId);
                customerImportQsService.updateById(customerImportQsEach);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ResponseData.SUCCESSMSG("处理完成");
    }


    /**
     * 从抓取的数据中获取数据
     *
     * @return
     */
    @GetMapping("customerDataQcc")
    public void getDataFromQcc() {
        int count = 0;
        while (true) {
            List<String> list = customerService.listOne("企查查");
            //循环处理
            for (String name : list) {
                List<Cookies> listCookie1 = cookiesService.selectCookieOne("企查查");
                List<Cookies> listCookie2 = cookiesService.selectCookieOne("企查查");
                try {
                    String urlList = "https://www.qichacha.com/search?key=" + URLEncoder.encode(StringUtils.trim(name));
                    Thread.sleep(1000 * ((int) (40 + Math.random() * 10)));
                    String infoList = HttpClientTool.doGet(urlList, listCookie1.get(0).getCookie());
                    Document doc = Jsoup.parse(infoList);
                    // 页面属性选择 通过key值取到select的内容
                    Elements links = doc.select("tbody>tr>td>a");
                    // 选取第一个元素就是要访问的公司信息
                    String href = links.get(0).attr("href");
                    String dataUrl = "https://www.qichacha.com/" + href;
                    Thread.sleep(1000 * ((int) (50 + Math.random() * 10)));
                    String dataInfo = HttpClientTool.doGet(dataUrl, listCookie2.get(0).getCookie());
                    if (StringUtils.isNotBlank(dataInfo)) {
                        DataCrawler dataCrawler = new DataCrawler();
                        dataCrawler.setCreateTime(LocalDateTime.now());
                        dataCrawler.setName(name);
                        dataCrawler.setText(dataInfo);
                        dataCrawler.setSource("企查查");
                        dataCrawlerService.save(dataCrawler);
                        count++;
                    }
                } catch (Exception e) {
                    System.out.println("**************************" + listCookie1.get(0).toString());
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 从抓取的数据中获取数据
     *
     * @return
     */
    @GetMapping("customerDataQccOne")
    public ResponseData<String> customerDataQccOne(String name) {
        List<Cookies> listCookie1 = cookiesService.selectCookieOne("企查查");
        List<Cookies> listCookie2 = cookiesService.selectCookieOne("企查查");
        try {
            String urlList = "https://www.qichacha.com/search?key=" + URLEncoder.encode(StringUtils.trim(name));
            Thread.sleep(1000 * ((int) (40 + Math.random() * 10)));
            String infoList = HttpClientTool.doGet(urlList, listCookie1.get(0).getCookie());
            Document doc = Jsoup.parse(infoList);
            // 页面属性选择 通过key值取到select的内容
            Elements links = doc.select("tbody>tr>td>a");
            // 选取第一个元素就是要访问的公司信息
            String href = links.get(0).attr("href");
            String dataUrl = "https://www.qichacha.com/" + href;
            Thread.sleep(1000 * ((int) (50 + Math.random() * 10)));
            String dataInfo = HttpClientTool.doGet(dataUrl, listCookie2.get(0).getCookie());
            if (StringUtils.isNotBlank(dataInfo)) {
                DataCrawler dataCrawler = new DataCrawler();
                dataCrawler.setCreateTime(LocalDateTime.now());
                dataCrawler.setName(name);
                dataCrawler.setText(dataInfo);
                dataCrawler.setSource("企查查");
                dataCrawlerService.save(dataCrawler);
            }
            return ResponseData.SUCCESS(dataInfo);
        } catch (Exception e) {
            System.out.println("**************************" + listCookie1.get(0).toString());
            e.printStackTrace();
            return ResponseData.ERRORMSG(e.getMessage());
        }
    }


    /**
     * 从企查查上爬取数据
     *
     * @param name
     * @return
     */
    @GetMapping("customerDataQccHandelOne")
    public ResponseData<Customer> customerDataQccHandelOne(String name) {
        QueryWrapper<DataCrawler> dataCrawlerQueryWrapper = new QueryWrapper<>();
        dataCrawlerQueryWrapper.eq("source", "企查查");
        dataCrawlerQueryWrapper.eq("name", name);
        DataCrawler one = dataCrawlerService.getOne(dataCrawlerQueryWrapper);
        String htmlStr = null;
        if (one == null) {
            ResponseData<String> responseData = this.customerDataQccOne(name);
            htmlStr = responseData.getData();
        } else {
            htmlStr = one.getText();
        }
        if (StringUtils.isBlank(htmlStr)) {
            return ResponseData.ERRORMSG("企查查没有查询到该客户");
        }

        ResponseData<Customer> responseData = customerService.handleDataByQcc(htmlStr, name);
        if (responseData.getCode() == ResponseData.ERROR_CODE) {
            return responseData;
        }
        return customerService.saveInfoToDb(responseData.getData());


    }

    @GetMapping("customerDatasqw")
    public void customerDatasqw() {
        while (true) {
            List<String> list = customerService.listOne("顺企网");
            //循环处理
            for (String name : list) {
                this.customerDatasqwOne(name);
            }
        }
    }

    @GetMapping("customerDatasqwOne")
    public ResponseData customerDatasqwOne(String name) {
        List<Cookies> listCookie1 = cookiesService.selectCookieOne("顺企网");
        List<Cookies> listCookie2 = cookiesService.selectCookieOne("顺企网");
        try {
            String urlList = "http://so.11467.com/cse/search?s=662286683871513660&ie=utf-8&q=" + URLEncoder.encode(StringUtils.trim(name));
            Thread.sleep(1000 * ((int) (3 + Math.random() * 2)));
            String infoList = HttpClientTool.doGet(urlList, listCookie1.get(0).getCookie());
            Document doc = Jsoup.parse(infoList);
            // 页面属性选择 通过key值取到select的内容
            Elements links = doc.select("#results>div>h3>a");
            // 选取第一个元素就是要访问的公司信息
            String href = null;
            for (Element element : links) {
                String em = element.select("em").html().trim();

                em = StringUtils.remove(em,"(");
                em = StringUtils.remove(em,")");
                em = StringUtils.remove(em,"（");
                em = StringUtils.remove(em,"）");
                em = MyStringUtil.replaceAllChar(em);
                String nameNew =StringUtils.remove(name,"(");
                nameNew =StringUtils.remove(nameNew,")");
                nameNew =StringUtils.remove(nameNew,"（");
                nameNew =StringUtils.remove(nameNew,"）");
                nameNew = MyStringUtil.replaceAllChar(nameNew);
                if (StringUtils.equals(em, nameNew) || StringUtils.contains(nameNew,em) || StringUtils.contains(em,nameNew)) {
                    href = element.attr("href");
                    break;
                }
            }
            if (StringUtils.isBlank(href)) {
                return ResponseData.ERRORMSG("没有查询到");
            }
            String dataUrl = href;
            Thread.sleep(1000 * ((int) (7 + Math.random() * 8)));
            String dataInfo = HttpClientTool.doGet(dataUrl, listCookie2.get(0).getCookie());
            if (StringUtils.isNotBlank(dataInfo)) {
                DataCrawler dataCrawler = new DataCrawler();
                dataCrawler.setCreateTime(LocalDateTime.now());
                dataCrawler.setName(name);
                dataCrawler.setText(dataInfo);
                dataCrawler.setSource("顺企网");
                dataCrawlerService.save(dataCrawler);
            }
            return ResponseData.SUCCESS(dataInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseData.ERRORMSG("没有查询到");

    }

    @GetMapping("customerDataSqwHandelOne")
    public ResponseData customerDataSqwHandelOne(String name) {
        QueryWrapper<DataCrawler> dataCrawlerQueryWrapper = new QueryWrapper<>();
        dataCrawlerQueryWrapper.eq("source", "顺企网");
        dataCrawlerQueryWrapper.eq("name", name);
        DataCrawler one = dataCrawlerService.getOne(dataCrawlerQueryWrapper);
        String htmlStr = null;
        if (one == null) {
            ResponseData<String> responseData = this.customerDatasqwOne(name);
            htmlStr = responseData.getData();
        } else {
            htmlStr = one.getText();
        }
        if (StringUtils.isBlank(htmlStr)) {
            return ResponseData.ERRORMSG("企查查没有查询到该客户");
        }

        ResponseData<Customer> responseData = customerService.handleDataByQcc(htmlStr, name);
        if (responseData.getCode() == ResponseData.ERROR_CODE) {
            return responseData;
        }
        return customerService.saveInfoToDb(responseData.getData());


    }


    /**
     * 从抓取的数据中获取数据
     *
     * @return
     */
    @GetMapping("customerDataQxb")
    public void getDataFromQxb() {
        int count = 0;
        while (true) {
            List<String> list = customerService.listOne("启信宝");
            //循环处理
            for (String name : list) {
                List<Cookies> listCookie1 = cookiesService.selectCookieOne("启信宝");
                List<Cookies> listCookie2 = cookiesService.selectCookieOne("启信宝");
                try {
                    String urlList = "https://www.qixin.com/search?from=baidusem8&page=1&key=" + URLEncoder.encode(StringUtils.trim(name));
//                    Thread.sleep(1000 * ((int) (40 + Math.random() * 10)));
                    String infoList = HttpClientTool.doGet(urlList, listCookie1.get(0).getCookie());
                    Document doc = Jsoup.parse(infoList);
                    // 页面属性选择 通过key值取到select的内容
                    Elements links = doc.select("div[class=col-2-1]>div[class=company-title font-18 font-f1]>a");
                    // 选取第一个元素就是要访问的公司信息
                    String href = links.get(0).attr("href");
                    String dataUrl = "https://www.qixin.com" + href;
//                    Thread.sleep(1000 * ((int) (50 + Math.random() * 10)));
                    String dataInfo = HttpClientTool.doGet(dataUrl, listCookie2.get(0).getCookie());
                    if (StringUtils.isNotBlank(dataInfo)) {
                        DataCrawler dataCrawler = new DataCrawler();
                        dataCrawler.setCreateTime(LocalDateTime.now());
                        dataCrawler.setName(name);
                        dataCrawler.setText(dataInfo);
                        dataCrawler.setSource("启信宝");
                        dataCrawlerService.save(dataCrawler);
                        count++;
                    }
                } catch (Exception e) {
                    System.out.println("**************************" + listCookie1.get(0).toString());
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 从抓取的数据中获取数据
     *
     * @return
     */
    @GetMapping("customerDataTyc")
    public void getDataFromTyc() {
        int count = 0;
        while (true) {
            List<String> list = customerService.listOne("天眼查");
            //循环处理
            for (String name : list) {
                List<Cookies> listCookie1 = cookiesService.selectCookieOne("天眼查");
                List<Cookies> listCookie2 = cookiesService.selectCookieOne("天眼查");
                try {
                    String urlList = "https://www.tianyancha.com/search?key=" + URLEncoder.encode(StringUtils.trim(name));
//                    Thread.sleep(1000 * ((int) (40 + Math.random() * 10)));
                    String infoList = HttpClientTool.doGet(urlList, listCookie1.get(0).getCookie());
                    Document doc = Jsoup.parse(infoList);
                    // 页面属性选择 通过key值取到select的内容
                    Elements links = doc.select("div[class=header]>a");
                    // 选取第一个元素就是要访问的公司信息
                    String href = links.get(0).attr("href");
                    String dataUrl = href;
//                    Thread.sleep(1000 * ((int) (50 + Math.random() * 10)));
                    String dataInfo = HttpClientTool.doGet(dataUrl, listCookie2.get(0).getCookie());
                    if (StringUtils.isNotBlank(dataInfo)) {
                        DataCrawler dataCrawler = new DataCrawler();
                        dataCrawler.setCreateTime(LocalDateTime.now());
                        dataCrawler.setName(name);
                        dataCrawler.setText(dataInfo);
                        dataCrawler.setSource("天眼查");
                        dataCrawlerService.save(dataCrawler);
                        count++;
                    }
                } catch (Exception e) {
                    System.out.println("**************************" + listCookie1.get(0).toString());
                    e.printStackTrace();
                }
            }
        }
    }


    @GetMapping("startHandle11")
    public ResponseData<Customer> startHandle(String name) {

        ResponseData<Customer> cUstomerInfoQcc = customerService.getCUstomerInfoQcc(name);
        ResponseData<Customer> cUstomerInfo2 = customerService.getCUstomerInfoSqw(name);

        System.out.println(cUstomerInfoQcc.toString());
        System.out.println(cUstomerInfo2.toString());

        return ResponseData.ERROR();
    }

    @Autowired
    DataFromBaiduServiceImpl dataFromBaiduService;

    @GetMapping("startHandleQccSqwAll")
    public void startHandleAll() {

        List<Customer> customerList = customerService.selectNotInPoolCustomer();
        for (Customer customer : customerList) {
            try {
                //从企查查上获取
                ResponseData<Customer> cUstomerInfoQcc = customerService.getCUstomerInfoQcc(customer.getName());
                if (cUstomerInfoQcc.getCode() == ResponseData.SUCCESS_CODE) {
                    customerService.getCUstomerInfoToDb(cUstomerInfoQcc.getData());
                }


                //从顺企网上获取
                ResponseData<Customer> cUstomerInfo2 = customerService.getCUstomerInfoSqw(customer.getName());
                if (cUstomerInfo2.getCode() == ResponseData.SUCCESS_CODE) {
                    customerService.getCUstomerInfoToDb(cUstomerInfo2.getData());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @GetMapping("startHandleBaiduAlibabaAll")
    public void startHandleBaiduAlibabaAll(String name) {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            queryWrapper.eq("name", name);
        } else {
            queryWrapper.eq("SourceType", 104009);
            queryWrapper.isNull("OrganizationCode");
        }
//        List<Customer> list = customerService.list(queryWrapper);
        while (true) {
            try {
                List<Customer> list = customerService.selectAllNotPool();
                for (Customer customer : list) {
                    //从百度征信上获取
                    ResponseData<Customer> cUstomerInfoBaidu = dataFromBaiduService.getEntInfo(customer.getName());
                    if (cUstomerInfoBaidu.getCode() == ResponseData.SUCCESS_CODE) {
                        Customer data = cUstomerInfoBaidu.getData();
//                    data.setCustomeruserList(null);
                        customerService.getCUstomerInfoToDb(data);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }



        }
//        this.startHandleBaiduAlibabaAddress("sss");

    }

    @GetMapping("startHandleBaiduAlibabaAddress")
    public void startHandleBaiduAlibabaAddress(String name) {
//        ResponseData<Customer> cUstomerInfoBaidu = dataFromAlibabaService.getLocation(name);
//        System.out.printf(cUstomerInfoBaidu.toString());
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SourceType", 104009);
        queryWrapper.isNull("OrganizationCode");
        List<Customer> list = customerService.list(queryWrapper);
        for (Customer customer : list) {
            try {

                ResponseData<Customer> cUstomerInfoBaidu = dataFromAlibabaService.getLocation(customer.getName());
                if (cUstomerInfoBaidu.getCode() == ResponseData.SUCCESS_CODE) {
                    Customer data = cUstomerInfoBaidu.getData();
                    customerService.getCUstomerInfoToDb(data);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}


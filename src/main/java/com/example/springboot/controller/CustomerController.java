package com.example.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.entity.*;
import com.example.springboot.service.*;
import com.example.springboot.service.impl.*;
import com.example.springboot.utils.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 客户信息 前端控制器
 * </p>
 *
 * @author tzj
 * @since 2020-03-18
 */
@RestController
@RequestMapping("/customer/")
@Slf4j
public class CustomerController {


    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ICustomeruserService customeruserService;
    @Autowired
    private ICustomerhwYearYieldService customerhwYearYieldService;
    @Autowired
    private LogisticcompanyServiceImpl logisticcompanyService;
    @Autowired
    private ProcessedlogServiceImpl processedlogService;
    @Autowired
    private ProcessorfactoryServiceImpl processorfactoryService;
    @Autowired
    private CustomerhwServiceImpl customerhwService;


    @Autowired
    private ICustomerPoolService customerPoolService;
    @Autowired
    private ICustomeruserPoolService customeruserPoolService;
    @Autowired
    private ICustomerhwYearYieldPoolService customerhwYearYieldPoolService;
    @Autowired
    private LogisticcompanyPoolServiceImpl logisticcompanyPoolService;
    @Autowired
    private ProcessedlogPoolServiceImpl processedlogPoolService;
    @Autowired
    private ProcessorfactoryPoolServiceImpl processorfactoryPoolService;
    @Autowired
    private CustomerhwPoolServiceImpl customerhwPoolService;


    @GetMapping("data")
    public ResponseData start() {
        List<CustomerPool> list = customerPoolService.selectAllPreforCustomer();
        for (CustomerPool customerPoolEach : list) {
            Integer customerId = null;
            try {
                QueryWrapper<Customer> customerQueryWrapper = new QueryWrapper<>();
                customerQueryWrapper.eq("Name", customerPoolEach.getName());
                List<Customer> customerList = customerService.list(customerQueryWrapper);
                if (customerList.isEmpty()) {
                    Customer customer = new Customer();
                    BeanUtils.copyProperties(customerPoolEach, customer);
                    customerService.save(customer);
                    customerId = customer.getId();
                    QueryWrapper<CustomeruserPool> customeruserPoolQueryWrapper = new QueryWrapper<>();
                    customeruserPoolQueryWrapper.eq("CustomerID", customerPoolEach.getId());
                    List<CustomeruserPool> customeruserPoolList = customeruserPoolService.list(customeruserPoolQueryWrapper);
                    for (CustomeruserPool customeruserPool : customeruserPoolList) {
                        Customeruser customeruser = new Customeruser();
                        BeanUtils.copyProperties(customeruserPool, customeruser);
                        customeruser.setCustomerID(customerId);
                        customeruser.setDelFlag(1);
                        customeruserService.save(customeruser);
                    }
                    QueryWrapper<CustomerhwYearYieldPool> customerhwYearYieldPoolQueryWrapper = new QueryWrapper<>();
                    customerhwYearYieldPoolQueryWrapper.eq("customer_id", customerPoolEach.getId());
                    List<CustomerhwYearYieldPool> customerhwYearYieldPoolList = customerhwYearYieldPoolService.list(customerhwYearYieldPoolQueryWrapper);
                    for (CustomerhwYearYieldPool customerhwYearYieldPool : customerhwYearYieldPoolList) {
                        CustomerhwYearYield customerhwYearYield = new CustomerhwYearYield();
                        BeanUtils.copyProperties(customerhwYearYieldPool, customerhwYearYield);
                        customerhwYearYield.setCustomerId(customerId);
                        customerhwYearYield.setDelFlag(1);
                        customerhwYearYieldService.save(customerhwYearYield);
                    }
                    QueryWrapper<Customerhw> customerhwQueryWrapper = new QueryWrapper<>();
                    customerhwQueryWrapper.eq("CustomerID", customerPoolEach.getId());
                    List<Customerhw> customerhwList = customerhwPoolService.list(customerhwQueryWrapper);
                    if (!customerhwList.isEmpty()) {
                        for (Customerhw customerhw : customerhwList) {
                            customerhw.setCustomerID(customerId);
                        }
                        customerhwService.saveBatch(customerhwList);
                    }
                    QueryWrapper<Processedlog> processedlogQueryWrapper = new QueryWrapper<>();
                    processedlogQueryWrapper.eq("CustomerID", customerPoolEach.getId());
                    List<Processedlog> processedlogList = processedlogPoolService.list(processedlogQueryWrapper);
                    if (!processedlogList.isEmpty()) {
                        for (Processedlog processedlog : processedlogList) {
                            processedlog.setCustomerID(customerId);
                            Integer processorID = processedlog.getProcessorID();
                            if (processorID != null) {
                                Processorfactory processorfactory = processorfactoryPoolService.getById(processorID);
                                QueryWrapper<Processorfactory> processorfactoryQueryWrapper = new QueryWrapper<>();
                                processorfactoryQueryWrapper.eq("Name", processorfactory.getName());
                                List<Processorfactory> processorfactoryList = processorfactoryPoolService.list(processorfactoryQueryWrapper);
                                if (processorfactoryList.isEmpty()) {
                                    processorfactoryService.save(processorfactory);
                                    processedlog.setProcessorID(processorfactory.getId());
                                } else {
                                    Processorfactory processorfactoryNew = processorfactoryList.get(0);
                                    processedlog.setProcessorID(processorfactoryNew.getId());
                                }
                            }
                            Integer lcid = processedlog.getLcid();
                            if (lcid != null) {
                                Logisticcompany logisticcompany = logisticcompanyPoolService.getById(lcid);
                                QueryWrapper<Logisticcompany> logisticcompanyQueryWrapper = new QueryWrapper<>();
                                logisticcompanyQueryWrapper.eq("Name", logisticcompany.getName());
                                List<Logisticcompany> logisticcompanyList = logisticcompanyService.list(logisticcompanyQueryWrapper);
                                if (logisticcompanyList.isEmpty()) {
                                    logisticcompanyService.save(logisticcompany);
                                    processedlog.setLcid(logisticcompany.getId());
                                } else {
                                    Logisticcompany logisticcompanyNew = logisticcompanyList.get(0);
                                    processedlog.setLcid(logisticcompanyNew.getId());
                                }
                            }
                            processedlogService.save(processedlog);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("数据导入报错", e);
            }
        }

        return ResponseData.SUCCESS();
    }


    @GetMapping("demo")
    public ResponseData customer(Customer customer) {
        System.out.printf(customer.toString());
        customer.setAddress(null);
        return ResponseData.SUCCESS(customer);
    }


}


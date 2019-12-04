package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.springboot.entity.Customer;
import com.example.springboot.entity.CustomerhwYearYield;
import com.example.springboot.entity.Fuyang;
import com.example.springboot.service.impl.AreainfoServiceImpl;
import com.example.springboot.service.impl.CustomerServiceImpl;
import com.example.springboot.service.impl.CustomerhwYearYieldServiceImpl;
import com.example.springboot.service.impl.FuyangServiceImpl;
import com.example.springboot.utils.ResponseData;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/import/")
@Log4j2
public class ImportCusstomerController {


    @Autowired
    CustomerServiceImpl customerService;

    @Autowired
    CustomerhwYearYieldServiceImpl customerhwYearYieldService;

    @Autowired
    FuyangServiceImpl fuyangService;

    @Autowired
    AreainfoServiceImpl areainfoService;


    @GetMapping("fuyang")
    public ResponseData importCustomer() {
        int jobId = (int) (1 + Math.random() * 10000);
        List<Fuyang> list = fuyangService.list();
        for (Fuyang fuyang : list) {
            try {
                QueryWrapper<Customer> customerQueryWrapper = new QueryWrapper<>();
                customerQueryWrapper.eq("name", fuyang.getName());
                List<Customer> customerList = customerService.list(customerQueryWrapper);
                if (customerList == null || customerList.isEmpty()) {
                    Customer customer = new Customer();
                    customer.setName(fuyang.getName());
                    customer.setAreaCode(fuyang.getAreaCode());
                    customer.setAddTime(LocalDateTime.now());
                    customer.setJobID(jobId);
                    customer.setSourceType(104010);
                    customer.setClaimType(103002);
                    customer.setAdduser(18);
                    customer.setCustomerType(100001);
                    customer.setUserLevel(1);
                    customer.setPutStorageTime(LocalDateTime.now());
                    customer.setPutStorageCount(1);
                    customerService.save(customer);
                    Integer id = customer.getId();
                    CustomerhwYearYield customerhwYearYield = new CustomerhwYearYield();
                    customerhwYearYield.setCreateTime(LocalDateTime.now());
                    customerhwYearYield.setCreateUserId(18);
                    customerhwYearYield.setDelFlag(1);
                    customerhwYearYield.setCustomerId(id);
                    customerhwYearYield.setHwCode(fuyang.getHwCode());
                    customerhwYearYield.setHwName(fuyang.getHwName());
                    customerhwYearYield.setHwType(fuyang.getHwType());
                    customerhwYearYield.setYearYield(fuyang.getYeild());
                    customerhwYearYield.setRemork("富阳环保厅数据");
                    customerhwYearYield.setYear(2018);
                    customerhwYearYieldService.save(customerhwYearYield);
                    customerhwYearYield.setId(null);
                    customerhwYearYield.setYear(2019);
                    customerhwYearYieldService.save(customerhwYearYield);

                } else {
                    Customer customer = customerList.get(0);
                    customer.setSourceType(104010);
                    customer.setUpdateTime(LocalDateTime.now());
                    customer.setAreaCode(fuyang.getAreaCode());
                    customerService.updateById(customer);
                    if (StringUtils.isNotBlank(fuyang.getHwCode()) || StringUtils.isNotBlank(fuyang.getHwType())) {
                        UpdateWrapper<CustomerhwYearYield> customerhwYearYieldUpdateWrapper = new UpdateWrapper<>();
                        customerhwYearYieldUpdateWrapper.eq("customer_Id", customer.getId());
                        customerhwYearYieldUpdateWrapper.eq("del_Flag", 1);
                        customerhwYearYieldUpdateWrapper.isNull("remork");
                        customerhwYearYieldUpdateWrapper.set("del_Flag", 0);
                        customerhwYearYieldService.update(customerhwYearYieldUpdateWrapper);
                        CustomerhwYearYield customerhwYearYield = new CustomerhwYearYield();
                        customerhwYearYield.setCustomerId(customer.getId());
                        customerhwYearYield.setYearYield(fuyang.getYeild());
                        customerhwYearYield.setHwType(fuyang.getHwType());
                        customerhwYearYield.setHwName(fuyang.getHwName());
                        customerhwYearYield.setHwCode(fuyang.getHwCode());
                        customerhwYearYield.setDelFlag(1);
                        customerhwYearYield.setCreateUserId(18);
                        customerhwYearYield.setCreateTime(LocalDateTime.now());
                        customerhwYearYield.setRemork("富阳环保厅数据");
                        customerhwYearYield.setYear(2018);
                        customerhwYearYieldService.save(customerhwYearYield);
                        customerhwYearYield.setId(null);
                        customerhwYearYield.setYear(2019);
                        customerhwYearYieldService.save(customerhwYearYield);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("*********************" + fuyang.getName() + ":处理失败", e);
            }
        }


        return ResponseData.SUCCESS();
    }


    private void removeChar(Fuyang fuyang) {
        fuyang.setName(StringUtils.remove(fuyang.getName(), " "));
        fuyang.setAreaName(StringUtils.remove(fuyang.getAreaName(), " "));
        fuyang.setHandleName(StringUtils.remove(fuyang.getHandleName(), " "));
        fuyang.setHwCode(StringUtils.remove(fuyang.getHwCode(), " "));
        fuyang.setHwType(StringUtils.remove(fuyang.getHwType(), " "));
        fuyang.setHwName(StringUtils.remove(fuyang.getHwName(), " "));
    }


}

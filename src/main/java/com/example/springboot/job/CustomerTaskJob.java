package com.example.springboot.job;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.springboot.entity.Customer;
import com.example.springboot.service.impl.CustomerServiceImpl;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/customer/")
public class CustomerTaskJob {

    private static Logger logger = LoggerFactory.getLogger(CustomerTaskJob.class);

    @Autowired
    CustomerServiceImpl customerService;


    /**
     * 定时分配任务给各个行政人员
     */
//  @Scheduled(cron = "0 0 1 * * ? ") //每天1点执行
//    @Scheduled(cron = "0 0/2 * * * ? ") //每2分钟执行一次点执行
    public void customerPoolForTask() {
        List<Customer> customerList =  customerService.findCustomerNotHandle();
        if(customerList == null || customerList.isEmpty()){
            logger.error("查询数据为空！");
            return;
        }



        int count = 0;
        List<Integer> userList = new LinkedList<>();
        userList.add(37);
        userList.add(38);
        userList.add(39);
        for (Customer customer : customerList) {
            count++;
            int handleUser = count % 3;
            Integer userId = userList.get(handleUser);
            Customer customerUpdate = new Customer();
            customerUpdate.setHandleUserId(userId);
            UpdateWrapper<Customer> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",customer.getId());
            customerService.update(customerUpdate,updateWrapper);
        }


    }


}

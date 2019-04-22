package com.example.springboot.controller;


import com.example.springboot.service.impl.ReportInfoServiceImpl;
import com.example.springboot.utils.ResponseData;
import com.example.springboot.utils.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 报表 前端控制器
 * </p>
 *
 * @author 童志杰
 * @since 2019-04-02
 */
@RestController
@RequestMapping("/reportInfo")
@PropertySource("classpath:scheduledTask.yml")
public class ReportInfoController {

    @Autowired
    ReportInfoServiceImpl reportInfoService;

    //获取对接数据统计报表
    @RequestMapping(value = "/startStaticial")
//    @Scheduled(cron  = "0 0 0 * * ? *")
    @Scheduled(cron = "${jobs.abut.report}")
    public ResponseData startStaticial(){
        State state =  null;
        try {
            state =  reportInfoService.startStaticial();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.ERROR(e.getMessage());
        }
        return ResponseData.SUCCESS(state);
    }


}


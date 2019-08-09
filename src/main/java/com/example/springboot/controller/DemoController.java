package com.example.springboot.controller;


import com.example.springboot.entity.UserInfo;
import com.example.springboot.prefix.DemoRedisPrefix;
import com.example.springboot.service.IUserInfoService;
import com.example.springboot.utils.RedisUtils;
import com.example.springboot.utils.ResponseData;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 演示类
 * @author: Tong
 * @date: 2019/8/8
 */
@RestController
@RequestMapping("/demo")
@Api(tags = "测试API接口")
public class DemoController {


    @Autowired
    IUserInfoService userInfoService;


    /**
     * 新增
     *
     * @return
     */
    @PostMapping
    public ResponseData insertDemo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("张三");
        userInfo.setAge(20);
        userInfo.setId(3);

        PageHelper.startPage(1,5);
        List<UserInfo> list = userInfoService.list();
        PageInfo<UserInfo> page = new PageInfo<UserInfo>(list);

        return ResponseData.SUCCESS(page);
    }

    /**
     * 修改
     *
     * @return
     */
    @PutMapping
    public ResponseData updateDemo() {

        return ResponseData.SUCCESS(1);
    }

    /**
     * 修改(部分更新)
     *
     * @return
     */
    @PatchMapping
    public ResponseData updateKeyDemo() {

        return ResponseData.SUCCESS(1);
    }

    /**
     * 修改(部分更新)
     *
     * @return
     */
    @DeleteMapping
    public ResponseData deleteDemo() {

        return ResponseData.SUCCESS(1);
    }

    /**
     * 获取redis缓存
     *
     * @return
     */
    @GetMapping("redis")
    @ApiOperation(value = "获取redis数据", notes = "从缓存中获取对象")
    public ResponseData redisDemo1() {

        UserInfo userInfo = RedisUtils.get(DemoRedisPrefix.demoById, "1", UserInfo.class);
        return ResponseData.SUCCESS(userInfo);
    }

    /**
     * 添加redis缓存
     *
     * @return
     */
    @PostMapping("redis")
    @ApiOperation(value = "新增redis数据", notes = "对象写入redis中")
    public ResponseData redisDemo2() {

        UserInfo userInfo = new UserInfo();
        userInfo.setName("张三");
        userInfo.setAge(20);
        RedisUtils.set(DemoRedisPrefix.demoById, "1", userInfo);
        return ResponseData.SUCCESS(userInfo);
    }


}

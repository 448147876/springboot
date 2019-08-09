package com.example.springboot.controller;


import com.example.springboot.entity.SysUser;
import com.example.springboot.prefix.DemoRedisPrefix;
import com.example.springboot.service.ISysUserService;
import com.example.springboot.utils.RedisUtils;
import com.example.springboot.utils.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @Description: 演示类
 *
 * @author: Tong
 * @date:  2019/8/8
 */
@RestController
@RequestMapping("/demo")
@Api(tags = "测试API接口")
public class DemoController {




    @Autowired
    ISysUserService sysUserService;



    /**
     * 新增
     * @return
     */
    @PostMapping
    public ResponseData insertDemo(){
        SysUser sysUser = new SysUser();
        sysUser.setUserName("张三");
        sysUser.setUserAge(20);
        sysUser.setDeptId(10);
        sysUser.setCreateDate(LocalDateTime.now());
        boolean save = sysUserService.save(sysUser);
        return ResponseData.SUCCESS(save);
    }
    /**
     * 修改
     * @return
     */
    @PutMapping
    public ResponseData updateDemo(){

        return ResponseData.SUCCESS(1);
    }
    /**
     * 修改(部分更新)
     * @return
     */
    @PatchMapping
    public ResponseData updateKeyDemo(){

        return ResponseData.SUCCESS(1);
    }
    /**
     * 修改(部分更新)
     * @return
     */
    @DeleteMapping
    public ResponseData deleteDemo(){

        return ResponseData.SUCCESS(1);
    }

    /**
     * 获取redis缓存
     * @return
     */
    @GetMapping("redis")
    @ApiOperation(value="获取redis数据", notes="从缓存中获取对象")
    public ResponseData redisDemo1(){

        SysUser sysUser = RedisUtils.get(DemoRedisPrefix.demoById, "1",SysUser.class);
        return ResponseData.SUCCESS(sysUser);
    }

    /**
     * 添加redis缓存
     * @return
     */
    @PostMapping("redis")
    @ApiOperation(value="新增redis数据", notes="对象写入redis中")
    public ResponseData redisDemo2(){

        SysUser sysUser = new SysUser();
        sysUser.setUserId(1);
        sysUser.setUserName("自己鞍山");
        RedisUtils.set(DemoRedisPrefix.demoById,"1",sysUser);
        return ResponseData.SUCCESS(sysUser);
    }








}

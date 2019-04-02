package com.example.springboot.controller;


import com.example.springboot.entity.SysUser;
import com.example.springboot.service.impl.SysUserServiceImpl;
import com.example.springboot.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 童志杰
 * @since 2019-03-28
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Autowired
    SysUserServiceImpl sysUserService;

    @RequestMapping("/user")
    public ResponseData getUser() {
        SysUser byId = sysUserService.getById(1);
        return ResponseData.SUCCESS(byId);
    }

    @RequestMapping("/saveUser")
    public ResponseData saveUser() {
        SysUser sysUser = new SysUser();
        sysUser.setUserName("张三");
        sysUser.setUserAge(20);
        sysUser.setDeptId(10);
        sysUser.setCreateDate(LocalDateTime.now());
        boolean save = sysUserService.save(sysUser);
        return ResponseData.SUCCESS(save);
    }

    @RequestMapping("/save")
    public ResponseData save(@Validated SysUser sysUser) {
        boolean save = sysUserService.save(sysUser);
        return ResponseData.SUCCESS(save);
    }
    @RequestMapping("/saveValidation")
    public ResponseData saveValidation() {
        SysUser sysUser = new SysUser();
        sysUser.setUserAge(20);
        sysUser.setDeptId(10);
        sysUser.setCreateDate(LocalDateTime.now());
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<SysUser>> validate = validator.validate(sysUser);


        boolean save = sysUserService.save(sysUser);
        return ResponseData.SUCCESS(save);
    }

}


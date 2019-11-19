package com.example.springboot.controller;


import com.example.springboot.service.KaptchaService;
import com.example.springboot.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kaptcha")
public class KaptchaController {
    @Autowired
    private KaptchaService kaptcha;

    /**
     * @名称 render
     * @描述 生成验证码
     * @参数 []
     * @返回值 com.alibaba.fastjson.JSONObject
     * @作者
     * @时间
     */
    @GetMapping("/render")
    public ResponseData render() {

        try {
            String code = kaptcha.render();
            if(null != code && !"".equals(code)){
                return ResponseData.SUCCESS("验证渲染成功");
            }else{
               return ResponseData.ERRORMSG("验证渲染失败");
            }
        } catch (Exception e) {
            return ResponseData.ERRORMSG("验证渲染失败");
        }
    }

    /**
     * @名称 validDefaultTime
     * @描述 验证码校验
     * @参数 [code]
     * @返回值 com.alibaba.fastjson.JSONObject
     * @作者
     * @时间
     */
    @PostMapping("/valid")
    public ResponseData validDefaultTime(@RequestParam String code) {
        //default timeout 900 seconds
        try {
            if(kaptcha.validate(code)){
                return  ResponseData.SUCCESS("验证成功");
            }else{
                return ResponseData.ERRORMSG("验证码有误");
            }
        } catch (Exception e) {
            return ResponseData.ERRORMSG("验证失败");
        }
    }

    @PostMapping("/validTime")
    public void validWithTime(@RequestParam String code) {
        kaptcha.validate(code, 60);
    }
}

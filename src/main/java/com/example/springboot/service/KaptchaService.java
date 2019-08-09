package com.example.springboot.service;


import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_DATE;
import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

@Service
public class KaptchaService {

    private static Logger logger = LoggerFactory.getLogger(KaptchaService.class);

    private DefaultKaptcha kaptcha;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;



    public KaptchaService(DefaultKaptcha kaptcha) {
        this.kaptcha = kaptcha;
    }

    /**
     * @名称 render
     * @描述 生成验证码
     * @参数 []
     * @返回值 java.lang.String
     * @作者
     * @时间
     */
    public String render() {
        response.setDateHeader(HttpHeaders.EXPIRES, 0L);
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-store, no-cache, must-revalidate");
        response.addHeader(HttpHeaders.CACHE_CONTROL, "post-check=0, pre-check=0");
        response.setHeader(HttpHeaders.PRAGMA, "no-cache");
        response.setHeader("ccccccc", "no-3243253245");
        response.setContentType("image/jpeg");
        String sessionCode = kaptcha.createText();
        try (ServletOutputStream out = response.getOutputStream()) {
            request.getSession().setAttribute(KAPTCHA_SESSION_KEY, sessionCode);
            request.getSession().setAttribute(KAPTCHA_SESSION_DATE, System.currentTimeMillis());
            ImageIO.write(kaptcha.createImage(sessionCode), "jpg", out);
            return sessionCode;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @名称 validate
     * @描述 验证码校验, 默认超时15分钟（900s）
     * @参数 [code]
     * @返回值 boolean
     * @作者
     * @时间
     */
    public boolean validate(String code) throws Exception {
        boolean success = false;
        success = validate(code, 900);

        return success;
    }

    /**
     * @名称 validate
     * @描述 验证码校验, 默认超时15分钟（900s）
     * @参数 [code, second]
     * @返回值 boolean
     * @作者
     * @时间
     */
    public boolean validate(String code, long second) {
        HttpSession httpSession = request.getSession(false);
        String sessionCode;
        if (httpSession != null && (sessionCode = (String) httpSession.getAttribute(KAPTCHA_SESSION_KEY)) != null) {
            if (sessionCode.equalsIgnoreCase(code)) {
                long sessionTime = (long) httpSession.getAttribute(KAPTCHA_SESSION_DATE);
                long duration = (System.currentTimeMillis() - sessionTime) / 1000;
                if (duration < second) {
                    httpSession.removeAttribute(KAPTCHA_SESSION_KEY);
                    httpSession.removeAttribute(KAPTCHA_SESSION_DATE);
                    return true;
                } else {
                    logger.error("验证码过期");
                }
            } else {
                logger.error("验证码不正确");
            }
        } else {
            logger.error("验证码未找到");
        }
        return false;
    }

}

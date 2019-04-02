package com.example.springboot.aop;


import com.example.springboot.entity.AopLog;
import com.example.springboot.service.impl.AopLogServiceImpl;
import com.example.springboot.utils.JsonUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Configuration
public class LogRecordAopController {


    @Autowired
    AopLogServiceImpl aopLogService;

    private static final Logger logger = LoggerFactory.getLogger(LogRecordAopController.class);
    private static final AopLog AOP_LOG = new AopLog();
    public static AopLog getAopLog(){
        return AOP_LOG;
    }


    // 定义切点Pointcut
    @Pointcut("execution(* com.example.springboot.controller.SysUserController.*(..))")
    public void excudeService() {

    }


    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        Object[] args = pjp.getArgs();
        String params = "";
        //获取请求参数集合并进行遍历拼接
        if(args.length>0){
            if("POST".equals(method)){
                Object object = args[0];
                Map map = getKeyAndValue(object);
                params = JsonUtils.obj2json(map);
            }else if("GET".equals(method)){
                params = queryString;
            }
        }

        String classType = pjp.getTarget().getClass().getName();
        Class<?> clazz = Class.forName(classType);
        String clazzName = clazz.getName();
        String methodName = pjp.getSignature().getName();

        AopLog aopLog = getAopLog();
        aopLog.setRequestUrl(uri);
        aopLog.setRequestType(method);
        aopLog.setUrl(url);
        aopLog.setType(0);
        aopLog.setLogTime(LocalDateTime.now());
        aopLog.setClassName(clazzName);
        aopLog.setMethodName(methodName);
        aopLog.setMethodParams(params);


        // result的值就是被拦截方法的返回值
        Object result = null;
        try{
            result = pjp.proceed();
            aopLog.setIsException(0);
            aopLog.setResultValue(JsonUtils.obj2json(result));
        }catch (Exception e){
            result = e.getMessage();
            aopLog.setIsException(1);
            aopLog.setExceptionInfo(JsonUtils.obj2json(result));
        }
        aopLog.setFlag(0);
        aopLogService.save(aopLog);

        return result;
    }

    public static Map<String, Object> getKeyAndValue(Object obj) {
        Map<String, Object> map = new HashMap<>();
        // 得到类对象
        Class userCla = (Class) obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true); // 设置些属性是可以访问的
            Object val = new Object();
            try {
                val = f.get(obj);
                // 得到此属性的值
                map.put(f.getName(), val);// 设置键值
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return map;
    }



}
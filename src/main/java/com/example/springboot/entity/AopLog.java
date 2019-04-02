package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 童志杰
 * @since 2019-04-02
 */
public class AopLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 请求/springboot/sysUser/save
     */
    private String requestUrl;

    /**
     * 请求类型
     */
    private String requestType;

    /**
     * 地址http://127.0.0.1:8081/springboot/sysUser/save
     */
    private String url;

    /**
     * 类型：,0，系统级别，。。。。。。
     */
    private Integer type;

    /**
     * 发生时间
     */
    private LocalDateTime logTime;

    /**
     * 类名
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 参数JSON格式
     */
    private String methodParams;

    /**
     * 返回值JSON格式
     */
    private String resultValue;

    /**
     * 是否报错0：成功，1：报错
     */
    private Integer isException;

    /**
     * 报错信息
     */
    private String exceptionInfo;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否有效（逻辑删除）
     */
    private Integer flag;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public LocalDateTime getLogTime() {
        return logTime;
    }

    public void setLogTime(LocalDateTime logTime) {
        this.logTime = logTime;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodParams() {
        return methodParams;
    }

    public void setMethodParams(String methodParams) {
        this.methodParams = methodParams;
    }

    public String getResultValue() {
        return resultValue;
    }

    public void setResultValue(String resultValue) {
        this.resultValue = resultValue;
    }

    public Integer getIsException() {
        return isException;
    }

    public void setIsException(Integer isException) {
        this.isException = isException;
    }

    public String getExceptionInfo() {
        return exceptionInfo;
    }

    public void setExceptionInfo(String exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "AopLog{" +
        "id=" + id +
        ", requestUrl=" + requestUrl +
        ", requestType=" + requestType +
        ", url=" + url +
        ", type=" + type +
        ", logTime=" + logTime +
        ", className=" + className +
        ", methodName=" + methodName +
        ", methodParams=" + methodParams +
        ", resultValue=" + resultValue +
        ", isException=" + isException +
        ", exceptionInfo=" + exceptionInfo +
        ", remark=" + remark +
        ", flag=" + flag +
        "}";
    }
}

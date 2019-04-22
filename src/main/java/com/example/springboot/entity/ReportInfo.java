package com.example.springboot.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 报表
 * </p>
 *
 * @author 童志杰
 * @since 2019-04-02
 */
public class ReportInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 报表类型：1，对接公司情况报表
     */
    private String type;

    /**
     * 内容（map的json格式字符串）
     */
    private String json;

    /**
     * 统计时间
     */
    private LocalDateTime statisticsDate;

    /**
     * 统计状态：0：完成，1：进行中，-1：失败
     */
    private String statisticsStatus;

    /**
     * 数据状态：0：最新，1：覆盖过时
     */
    private String status;

    /**
     * 数据状态：0：有效，1：删除
     */
    private String flag;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public LocalDateTime getStatisticsDate() {
        return statisticsDate;
    }

    public void setStatisticsDate(LocalDateTime statisticsDate) {
        this.statisticsDate = statisticsDate;
    }

    public String getStatisticsStatus() {
        return statisticsStatus;
    }

    public void setStatisticsStatus(String statisticsStatus) {
        this.statisticsStatus = statisticsStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "ReportInfo{" +
        "id=" + id +
        ", type=" + type +
        ", json=" + json +
        ", statisticsDate=" + statisticsDate +
        ", statisticsStatus=" + statisticsStatus +
        ", status=" + status +
        ", flag=" + flag +
        "}";
    }
}

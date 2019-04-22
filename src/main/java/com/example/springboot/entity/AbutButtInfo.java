package com.example.springboot.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 对接平台信息表
 * </p>
 *
 * @author 童志杰
 * @since 2019-04-02
 */
public class AbutButtInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 手机号码
     */
    private String phoneNum;

    /**
     * 厂商code
     */
    private String producerCode;

    /**
     * 运营单位全称
     */
    private String producerName;

    /**
     * 运营单位简称
     */
    private String producerNameSimple;

    /**
     * 运营单位联系人姓名
     */
    private String producerContactPsn;

    /**
     * 运营单位联系人职务
     */
    private String producerContactPsnJob;

    /**
     * 运营单位联系人手机
     */
    private String producerContactPsnMobile;

    /**
     * 运营单位地址
     */
    private String producerAddress;

    /**
     * 接入社会单位数量
     */
    private Integer producerJoinCount;

    /**
     * 平台code
     */
    private String esbCode;

    /**
     * 平台名称
     */
    private String esbName;

    /**
     * 区域id
     */
    private String areaId;

    /**
     * 开发单位code
     */
    private String devCode;

    /**
     * 开发单位名称
     */
    private String devName;

    /**
     * 开发单位联系人姓名
     */
    private String devContactPsn;

    /**
     * 开发单位联系人职务
     */
    private String devContactPsnJob;

    /**
     * 开发单位联系人手机
     */
    private String devContactPsnMobile;

    /**
     * 开发单位地址
     */
    private String devAddress;

    /**
     * 处理类型：1，无需新建平台，2：新建平台，3，新建厂商平台
     */
    private Integer handleType;

    /**
     * 进度- 1：注册审核，2：本地测试，3：线上测试，4：对接审核，5：完成
     */
    private Integer progress;

    /**
     * 状态-10：注册审核中，11：审核不通过，12：审核通过，20：内部测试中，30：线上测试中，31：线上测试不通过，32：线上测试通过，40：提交审核中，41：提交审核不通过。
     */
    private Integer status;

    /**
     * 测试用户名
     */
    private String checkUserName;

    /**
     * 测试密码
     */
    private String checkUserPassword;

    /**
     * 测试地址
     */
    private String checkUrl;

    /**
     * 修改时间
     */
    private LocalDateTime updateDate;

    /**
     * 修改者
     */
    private String updateBy;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 图片地址
     */
    private String imagePath;

    /**
     * 审核说明
     */
    private String checkInfo;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 删除标识
     */
    private Integer delFlag;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getProducerCode() {
        return producerCode;
    }

    public void setProducerCode(String producerCode) {
        this.producerCode = producerCode;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getProducerNameSimple() {
        return producerNameSimple;
    }

    public void setProducerNameSimple(String producerNameSimple) {
        this.producerNameSimple = producerNameSimple;
    }

    public String getProducerContactPsn() {
        return producerContactPsn;
    }

    public void setProducerContactPsn(String producerContactPsn) {
        this.producerContactPsn = producerContactPsn;
    }

    public String getProducerContactPsnJob() {
        return producerContactPsnJob;
    }

    public void setProducerContactPsnJob(String producerContactPsnJob) {
        this.producerContactPsnJob = producerContactPsnJob;
    }

    public String getProducerContactPsnMobile() {
        return producerContactPsnMobile;
    }

    public void setProducerContactPsnMobile(String producerContactPsnMobile) {
        this.producerContactPsnMobile = producerContactPsnMobile;
    }

    public String getProducerAddress() {
        return producerAddress;
    }

    public void setProducerAddress(String producerAddress) {
        this.producerAddress = producerAddress;
    }

    public Integer getProducerJoinCount() {
        return producerJoinCount;
    }

    public void setProducerJoinCount(Integer producerJoinCount) {
        this.producerJoinCount = producerJoinCount;
    }

    public String getEsbCode() {
        return esbCode;
    }

    public void setEsbCode(String esbCode) {
        this.esbCode = esbCode;
    }

    public String getEsbName() {
        return esbName;
    }

    public void setEsbName(String esbName) {
        this.esbName = esbName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getDevCode() {
        return devCode;
    }

    public void setDevCode(String devCode) {
        this.devCode = devCode;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getDevContactPsn() {
        return devContactPsn;
    }

    public void setDevContactPsn(String devContactPsn) {
        this.devContactPsn = devContactPsn;
    }

    public String getDevContactPsnJob() {
        return devContactPsnJob;
    }

    public void setDevContactPsnJob(String devContactPsnJob) {
        this.devContactPsnJob = devContactPsnJob;
    }

    public String getDevContactPsnMobile() {
        return devContactPsnMobile;
    }

    public void setDevContactPsnMobile(String devContactPsnMobile) {
        this.devContactPsnMobile = devContactPsnMobile;
    }

    public String getDevAddress() {
        return devAddress;
    }

    public void setDevAddress(String devAddress) {
        this.devAddress = devAddress;
    }

    public Integer getHandleType() {
        return handleType;
    }

    public void setHandleType(Integer handleType) {
        this.handleType = handleType;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public String getCheckUserPassword() {
        return checkUserPassword;
    }

    public void setCheckUserPassword(String checkUserPassword) {
        this.checkUserPassword = checkUserPassword;
    }

    public String getCheckUrl() {
        return checkUrl;
    }

    public void setCheckUrl(String checkUrl) {
        this.checkUrl = checkUrl;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCheckInfo() {
        return checkInfo;
    }

    public void setCheckInfo(String checkInfo) {
        this.checkInfo = checkInfo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "AbutButtInfo{" +
        "id=" + id +
        ", phoneNum=" + phoneNum +
        ", producerCode=" + producerCode +
        ", producerName=" + producerName +
        ", producerNameSimple=" + producerNameSimple +
        ", producerContactPsn=" + producerContactPsn +
        ", producerContactPsnJob=" + producerContactPsnJob +
        ", producerContactPsnMobile=" + producerContactPsnMobile +
        ", producerAddress=" + producerAddress +
        ", producerJoinCount=" + producerJoinCount +
        ", esbCode=" + esbCode +
        ", esbName=" + esbName +
        ", areaId=" + areaId +
        ", devCode=" + devCode +
        ", devName=" + devName +
        ", devContactPsn=" + devContactPsn +
        ", devContactPsnJob=" + devContactPsnJob +
        ", devContactPsnMobile=" + devContactPsnMobile +
        ", devAddress=" + devAddress +
        ", handleType=" + handleType +
        ", progress=" + progress +
        ", status=" + status +
        ", checkUserName=" + checkUserName +
        ", checkUserPassword=" + checkUserPassword +
        ", checkUrl=" + checkUrl +
        ", updateDate=" + updateDate +
        ", updateBy=" + updateBy +
        ", createDate=" + createDate +
        ", createBy=" + createBy +
        ", imagePath=" + imagePath +
        ", checkInfo=" + checkInfo +
        ", remarks=" + remarks +
        ", delFlag=" + delFlag +
        "}";
    }
}

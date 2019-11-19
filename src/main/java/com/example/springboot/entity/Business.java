package com.example.springboot.entity;

import lombok.Data;

@Data
public class Business {
    //法人
    private String LegalPerson;
    //注册资本
    private String RegisteredCapital;
    //实缴资本
    private String PaidInCapital;
    //经营状态
    private String BusinessStatus;
    //成立日期
    private String DateOfEstablishment;
    //统一社会信用代码
    private String ID;
    //纳税人识别号
    private String Code;
    //注册号
    private String RegistrationNumber;
    //组织机构代码
    private String OrganizationCode;
    //公司类型
    private String companyType;
    @Override
    public String toString() {
        return "法人:"+LegalPerson+";注册资本:"+RegisteredCapital+";实缴资本:"+PaidInCapital+
                ";经营状态:"+BusinessStatus+";成立日期:"+DateOfEstablishment+
                ";统一社会信用代码:"+ID+";纳税人识别号:"+Code+";注册号:"+RegistrationNumber+";组织机构代码:"+OrganizationCode+
                ";公司类型:"+companyType+";所属行业:"+Industry+";核准日期:"+ApprovalDate+";登记机关:"+RegistrationAuthority+
                ";所属地区:"+districtBelong+";英文名:"+EnglishName+
                ";曾用名:"+usedName+";经营方式:"+type+";人员规模:"+StaffSize+";营业期限:"+OperatingPeriod+";企业地址:"+address+";经营范围:"+BusinessScope;
    }
    //所属行业
    private String Industry;
    //核准日期
    private String ApprovalDate;
    //登记机关
    private String RegistrationAuthority;
    //所属地区
    private String districtBelong;
    //英文名
    private String EnglishName;
    //曾用名
    private String usedName;
    //经营方式
    private String type;
    //人员规模
    private String StaffSize;
    //营业期限
    private String OperatingPeriod;
    //企业地址
    private String address;
    //经营范围
    private String BusinessScope;

}

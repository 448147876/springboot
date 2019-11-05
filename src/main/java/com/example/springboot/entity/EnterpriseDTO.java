package com.example.springboot.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ToString
public class EnterpriseDTO implements Serializable {





    private String sourceType;

    private String enterpriseName;
    private String address;
    private String legalPsnName;
    private Integer registeredCapital;
    private Date registerDate;
    private String managementForms;
    private String UnifiedSocialCreditCode;
    private String enterpriseType;
    private String industry;
    private Integer staffSize;
    private String businessScope;
    private String mainProduction;
    private String synopsis;

    private List<ContactsDTO> contactsDTOs;

    private Double lon;
    private Double lat;

    private String areaCode;


}

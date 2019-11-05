package com.example.springboot.entity;


import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class ContactsDTO implements Serializable {


    private String enterpriseName;
    private String userName;
    private String mobelPhone;
    private String phone;
    private String email;
    private String QQ;
    private String position;

    private String sourceType;


}

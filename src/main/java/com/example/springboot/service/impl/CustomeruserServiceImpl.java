package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.entity.Customer;
import com.example.springboot.entity.CustomerImportQs;
import com.example.springboot.entity.Customeruser;
import com.example.springboot.mapper.CustomeruserMapper;
import com.example.springboot.service.ICustomeruserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tzj
 * @since 2019-11-06
 */
@Service
public class CustomeruserServiceImpl extends ServiceImpl<CustomeruserMapper, Customeruser> implements ICustomeruserService {

    @Autowired
    CustomeruserServiceImpl customeruserService;

    @Autowired
    CustomerServiceImpl customerService;

    @Override
    public List<Customeruser> listNotContent(Integer customerId,String realName, String mobilePhone, String phone) {
        List<Customeruser>  customeruserList= getBaseMapper().listNotContent(customerId,realName, mobilePhone, phone);
        return customeruserList;
    }

    public Customeruser getUserByImport(CustomerImportQs customerImportQsEach) {
        Customeruser customeruser = new Customeruser();
        String mobelPhone = customerService.getDataByHtml(customerImportQsEach,5);
        if(StringUtils.isNotBlank(mobelPhone)){
            customeruser.setMobilePhone(mobelPhone);
        }
        String pbone = customerService.getDataByHtml(customerImportQsEach,2);
        if(StringUtils.isNotBlank(pbone)){
            customeruser.setPhone(pbone);
        }
        String psnName = customerService.getDataByHtml(customerImportQsEach,4);
        if(StringUtils.isNotBlank(psnName)){
            customeruser.setRealName(psnName);
        }
        String position = customerService.getDataByHtml(customerImportQsEach,3);
        if(StringUtils.isNotBlank(position)){
            customeruser.setPosition(position);
        }
        String email = customerService.getDataByHtml(customerImportQsEach,6);
        if(StringUtils.isNotBlank(email)){
            customeruser.setEmail(email);
        }
        String qq = customerService.getDataByHtml(customerImportQsEach,7);
        if(StringUtils.isNotBlank(qq)){
            customeruser.setQq(qq);
        }
        return customeruser;
    }


    @Override
    public void handleContenct(CustomerImportQs customerImportQsEach, Customer customerOld,Integer jobId) {
        QueryWrapper<Customeruser> customeruserQueryWrapper = new QueryWrapper<>();
        customeruserQueryWrapper.eq("CustomerID",customerOld.getId());
        List<Customeruser> customeruserList = customeruserService.list(customeruserQueryWrapper);
        Customeruser customeruserNew = customeruserService.getUserByImport(customerImportQsEach);
        if(customeruserNew == null){
            return;
        }
        //存在，则匹配是否能够匹配到，如果匹配到了，就更新，匹配不到了就新增
        if(customeruserList == null || customeruserList.isEmpty()){
            customeruserNew.setCustomerID(customerOld.getId());
        }else{
            for(Customeruser customeruserEach:customeruserList){
                if(customeruserEach.getMobilePhone() == customeruserNew.getMobilePhone()){
                    if(StringUtils.isNotBlank(customeruserNew.getRealName()) && StringUtils.isBlank(customeruserEach.getRealName())){
                        customeruserEach.setRealName(customeruserNew.getRealName());
                    }
                    if(StringUtils.isNotBlank(customeruserNew.getPhone()) && StringUtils.isBlank(customeruserEach.getPhone())){
                        customeruserEach.setPhone(customeruserNew.getPhone());
                    }
                    if(StringUtils.isNotBlank(customeruserNew.getQq()) && StringUtils.isBlank(customeruserEach.getQq())){
                        customeruserEach.setQq(customeruserNew.getQq());
                    }
                    if(StringUtils.isNotBlank(customeruserNew.getPosition()) && StringUtils.isBlank(customeruserEach.getQq())){
                        customeruserEach.setPosition(customeruserNew.getQq());
                    }
                    if(StringUtils.isNotBlank(customeruserNew.getEmail()) && StringUtils.isBlank(customeruserEach.getEmail())){
                        customeruserEach.setEmail(customeruserNew.getEmail());
                    }

                    customeruserNew = customeruserEach;
                    break;
                }
            }
        }
        customeruserNew.setJobID(jobId);
        if(customeruserNew.getCustomerID() == null){
            customeruserNew.setCustomerID(customerOld.getId());
        }
        if(customeruserNew.getId() == null){
            customeruserService.save(customeruserNew);
        }else{
            customeruserService.updateById(customeruserNew);
        }
    }
}

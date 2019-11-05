package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.entity.Customer;
import com.example.springboot.entity.CustomerImportQs;
import com.example.springboot.entity.Customeruser;
import com.example.springboot.mapper.CustomerMapper;
import com.example.springboot.service.ICustomerImportQsService;
import com.example.springboot.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.service.ICustomeruserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 客户信息 服务实现类
 * </p>
 *
 * @author tzj
 * @since 2019-10-29
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    ICustomerService customerService;

    @Autowired
    ICustomeruserService customeruserService;

    @Autowired
    ICustomerImportQsService customerImportQsService;

    /**
     * 从顺企网上获取信息
     * @param customerImportQs
     * @param jobId
     */
    @Override
    public void handleCustomerSqw(CustomerImportQs customerImportQs, Integer jobId) {

        String enterpriseName = customerImportQs.getEnterpriseName();
        if(StringUtils.isEmpty(enterpriseName)){
            return;
        }

        Customer customer = customerService.getOne(new QueryWrapper<Customer>().eq("name",enterpriseName));
        if(customer == null){
            return;
        }
        //获取顺气网上的联系方式
        Customeruser customeruser = new Customeruser();
        customeruser.setSourcType(104001);
        customeruser.setCustomerID(customer.getId());
        String psnName = customerImportQs.getPsnName();
        if(StringUtils.isNotBlank(psnName)){
            customeruser.setRealName(StringUtils.trim(psnName));
        }
        String dept = customerImportQs.getDept();
        if(StringUtils.isNotBlank(dept)){
            customeruser.setPosition(StringUtils.trim(dept).replaceAll("：",""));
        }
        String phone = customerImportQs.getPhone();
        if(StringUtils.isBlank(phone) || StringUtils.contains(phone,"未提供") || StringUtils.length(phone) < 7){
            String phoneBak = customerImportQs.getPhoneBak();
            if(StringUtils.isNotBlank(phoneBak) && StringUtils.length(phoneBak)>= 7){
                customeruser.setPhone(StringUtils.trim(phoneBak));
            }
        }else{
            customeruser.setPhone(StringUtils.trim(phone));
        }

        String mobelPhone = customerImportQs.getMobelPhone();
        if(StringUtils.isBlank(mobelPhone) || StringUtils.contains(mobelPhone,"未提供") || StringUtils.length(mobelPhone) < 11){
            String mobelPhoneBak = customerImportQs.getMobelPhoneBak();
            if(StringUtils.isNotBlank(mobelPhoneBak) && StringUtils.length(mobelPhoneBak)>= 11){
                customeruser.setPhone(StringUtils.trim(mobelPhoneBak));
            }
        }else{
            customeruser.setMobilePhone(StringUtils.trim(mobelPhone));
        }

        String qq = customerImportQs.getQq();
        if(StringUtils.isBlank(qq)  || StringUtils.length(mobelPhone) < 5 || StringUtils.contains(qq,"点击")){
            String qqBak = customerImportQs.getQqBak();
            if(StringUtils.isNotBlank(qqBak) && StringUtils.length(qqBak)>= 5){
                customeruser.setQq(StringUtils.trim(qqBak));
            }
        }else{
            customeruser.setQq(StringUtils.trim(qq));
        }

        String email = customerImportQs.getEmail();
        if(StringUtils.isBlank(email)  || StringUtils.length(email) < 5 || !StringUtils.contains(email,"@") || !(StringUtils.contains(email,".com") || StringUtils.contains(email,".cn"))){
            String emailBak = customerImportQs.getEmailBak();
            if(StringUtils.isNotBlank(emailBak) && StringUtils.length(emailBak)>= 5){
                customeruser.setEmail(StringUtils.trim(emailBak));
            }
        }else{
            customeruser.setEmail(StringUtils.trim(email));
        }
        customeruser.setSex(1);
        customeruser.setAddTime(LocalDateTime.now());
        customeruser.setJobID(jobId);
        customeruser.setDelFlag(1);
        customeruser.setAdduser(0);
        customeruser.setIsUser(0);

        //是否已经存在
        QueryWrapper<Customeruser> customeruserQueryWrapper = new QueryWrapper<>();
        customeruserQueryWrapper.eq("customerId",customer.getId());
        customeruserQueryWrapper.eq("MobilePhone",mobelPhone);
        List<Customeruser> list = customeruserService.list(customeruserQueryWrapper);
        Customeruser customeruserOld = null;
        if(list != null && list.size()>0){
            customeruserOld = list.get(0);
        }
        if(customeruserOld == null){
            //确认是否需要保存，姓名必须要有，电话和手机必须要有一个
            if(StringUtils.isNotBlank(psnName) && (StringUtils.isNotBlank(phone) || StringUtils.isNotBlank(mobelPhone))){
                customeruserService.save(customeruser);
            }
        }else{
            if(StringUtils.isBlank(customeruserOld.getPhone()) && StringUtils.isNotBlank(customeruser.getPhone())){
                customeruserOld.setPhone(customeruser.getPhone());
            }
            if(StringUtils.isBlank(customeruserOld.getEmail()) && StringUtils.isNotBlank(customeruser.getEmail())){
                customeruserOld.setEmail(customeruser.getEmail());
            }
            if(StringUtils.isBlank(customeruserOld.getPosition()) && StringUtils.isNotBlank(customeruser.getPosition())){
                customeruserOld.setPosition(customeruser.getPosition());
            }
            if(StringUtils.isBlank(customeruserOld.getQq()) && StringUtils.isNotBlank(customeruser.getQq())){
                customeruserOld.setQq(customeruser.getQq());
            }
            customeruserService.updateById(customeruserOld);

        }



        //维护客户信息
        String companyDescript = customer.getCompanyDescript();
        if(StringUtils.isBlank(companyDescript)){
            companyDescript = customerImportQs.getSynopsis();
            customer.setCompanyDescript(companyDescript);
        }
        String address = customer.getAddress();
        if(StringUtils.isBlank(address)){
            address = customerImportQs.getAddress();
            customer.setAddress(address);
        }
        String mainProducts = customer.getMainProducts();
        if(StringUtils.isBlank(mainProducts)){
            mainProducts = customerImportQs.getMainProduction();
            customer.setMainProducts(mainProducts);
        }
        customer.setUpdateTime(LocalDateTime.now());
        customer.setJobID(jobId);
        customerService.updateById(customer);
        customerImportQs.setJobId(jobId);

        customerImportQsService.updateById(customerImportQs);
    }



    /**
     * 从企查查上获取信息
     * @param customerImportQs
     * @param jobId
     */
    @Override
    public void handleCustomerQcc(CustomerImportQs customerImportQs, Integer jobId) {

        String enterpriseName = customerImportQs.getEnterpriseName();
        if(StringUtils.isEmpty(enterpriseName)){
            return;
        }

        Customer customer = customerService.getOne(new QueryWrapper<Customer>().eq("name",enterpriseName));
        if(customer == null){
            return;
        }
        //获取企查查上的联系方式
        Customeruser customeruser = new Customeruser();
        customeruser.setSourcType(104003);
        customeruser.setCustomerID(customer.getId());
        String psnName = customerImportQs.getLegalPsnName();
        if(StringUtils.isNotBlank(psnName)){
            customeruser.setRealName(StringUtils.trim(psnName));
        }
        String dept = customerImportQs.getDept();
        if(StringUtils.isBlank(dept)){
            customeruser.setPosition("法人");
        }
        String phone = customerImportQs.getPhone();
        if(StringUtils.isBlank(phone) || StringUtils.contains(phone,"更多号码") || StringUtils.length(phone) < 7){
            phone = customerImportQs.getPhoneBak();
            if(StringUtils.isNotBlank(phone) && StringUtils.length(phone)>= 7){
                customeruser.setPhone(StringUtils.trim(phone));
            }
        }else{
            customeruser.setPhone(StringUtils.trim(phone));
        }

        String mobelPhone = customerImportQs.getMobelPhone();
        if(StringUtils.isBlank(mobelPhone) || StringUtils.contains(mobelPhone,"更多号码") || StringUtils.length(mobelPhone) < 11){
            mobelPhone = customerImportQs.getMobelPhoneBak();
            if(StringUtils.isNotBlank(mobelPhone) && StringUtils.length(mobelPhone)>= 11){
                customeruser.setPhone(StringUtils.trim(mobelPhone));
            }
        }else{
            customeruser.setMobilePhone(StringUtils.trim(mobelPhone));
        }

        String qq = customerImportQs.getQq();
        if(StringUtils.isBlank(qq)  || StringUtils.length(qq) < 5 || StringUtils.contains(qq,"点击")){
            String qqBak = customerImportQs.getQqBak();
            if(StringUtils.isNotBlank(qqBak) && StringUtils.length(qqBak)>= 5){
                customeruser.setQq(StringUtils.trim(qqBak));
            }
        }else{
            customeruser.setQq(StringUtils.trim(qq));
        }

        String email = customerImportQs.getEmail();
        if(StringUtils.isBlank(email)  || StringUtils.length(email) < 5 || !StringUtils.contains(email,"@")){
            String emailBak = customerImportQs.getEmailBak();
            if(StringUtils.isNotBlank(emailBak) && StringUtils.length(emailBak)>= 5){
                customeruser.setEmail(StringUtils.trim(emailBak));
            }
        }else{
            customeruser.setEmail(StringUtils.trim(email));
        }
        customeruser.setSex(1);
        customeruser.setAddTime(LocalDateTime.now());
        customeruser.setJobID(jobId);
        customeruser.setDelFlag(1);
        customeruser.setAdduser(0);
        customeruser.setIsUser(0);

        //是否已经存在
        QueryWrapper<Customeruser> customeruserQueryWrapper = new QueryWrapper<>();
        customeruserQueryWrapper.eq("customerId",customer.getId());
        if(StringUtils.isNotBlank(mobelPhone)){
            customeruserQueryWrapper.eq("MobilePhone",mobelPhone);
        }else{
            if(StringUtils.isNotBlank(phone)){
                customeruserQueryWrapper.eq("MobilePhone",phone);
            }
        }

        List<Customeruser> list = null;
        if(StringUtils.isNotBlank(mobelPhone) || StringUtils.isNotBlank(phone)){
            list = customeruserService.list(customeruserQueryWrapper);

        }


        Customeruser customeruserOld = null;
        if(list != null && list.size()>0){
            customeruserOld = list.get(0);
        }
        if(customeruserOld == null){
            //确认是否需要保存，姓名必须要有，电话和手机必须要有一个
            if(StringUtils.isNotBlank(psnName) && (StringUtils.isNotBlank(phone) || StringUtils.isNotBlank(mobelPhone))){
                customeruserService.save(customeruser);
            }
        }else{
            if(StringUtils.isBlank(customeruserOld.getPhone()) && StringUtils.isNotBlank(customeruser.getPhone())){
                customeruserOld.setPhone(customeruser.getPhone());
            }
            if(StringUtils.isBlank(customeruserOld.getMobilePhone()) && StringUtils.isNotBlank(customeruser.getMobilePhone())){
                customeruserOld.setPhone(customeruser.getMobilePhone());
            }
            if(StringUtils.isBlank(customeruserOld.getEmail()) && StringUtils.isNotBlank(customeruser.getEmail())){
                customeruserOld.setEmail(customeruser.getEmail());
            }
            if(StringUtils.isBlank(customeruserOld.getPosition()) && StringUtils.isNotBlank(customeruser.getPosition())){
                customeruserOld.setPosition(customeruser.getPosition());
            }
            if(StringUtils.isBlank(customeruserOld.getQq()) && StringUtils.isNotBlank(customeruser.getQq())){
                customeruserOld.setQq(customeruser.getQq());
            }
            customeruserService.updateById(customeruserOld);

        }



        //维护客户信息
        String businessIndustry = customer.getBusinessIndustry();
        if(StringUtils.isBlank(businessIndustry)){
            businessIndustry = customerImportQs.getIndustry();
            customer.setBusinessIndustry(businessIndustry);
        }
        String mainProducts = customer.getMainProducts();
        if(StringUtils.isBlank(mainProducts)){
            mainProducts = customerImportQs.getMainProduction();
            customer.setMainProducts(mainProducts);
        }
        String staffSize = customer.getStaffSize();
        if(StringUtils.isBlank(staffSize)){
            String managementForms = customerImportQs.getManagementForms();
            customer.setStaffSize(managementForms);
        }
        String legalRepresentative = customer.getLegalRepresentative();
        if(StringUtils.isBlank(legalRepresentative)){
            legalRepresentative = customerImportQs.getLegalPsnName();
            customer.setLegalRepresentative(legalRepresentative);
        }

        Integer registeredCapital = customer.getRegisteredCapital();
        if(registeredCapital == null || registeredCapital == 0){
            String registeredCapitalStr = StringUtils.trim(customerImportQs.getRegisteredCapital());
            if(StringUtils.isNotEmpty(registeredCapitalStr) && !StringUtils.contains(registeredCapitalStr,"-")){
                if(StringUtils.contains(registeredCapitalStr,"万")){
                    registeredCapitalStr = StringUtils.substringBefore(registeredCapitalStr,"万");
                    if(StringUtils.isNumeric(registeredCapitalStr)){
                        customer.setRegisteredCapital(Integer.parseInt(registeredCapitalStr));
                    }
                }
            }
        }
        String createTime = customer.getCreateTime();
        if(StringUtils.isBlank(createTime)){
            String registerDate = customerImportQs.getRegisterDate();
            customer.setCreateTime(registerDate);
        }

        String webSite = customer.getWebSite();
        if(StringUtils.isBlank(webSite)){
            String webUrl = customerImportQs.getWebUrl();
            customer.setWebSite(webUrl);
        }

        customer.setUpdateTime(LocalDateTime.now());
        customer.setJobID(jobId);
        customerService.updateById(customer);
        customerImportQs.setJobId(jobId);

        customerImportQsService.updateById(customerImportQs);
    }

    @Override
    public List<Customer> listSideNull() {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("X_Side");
        List<Customer> customerList = customerMapper.selectList(queryWrapper);
        return customerList;
    }
}

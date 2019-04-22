package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.entity.AbutButtInfo;
import com.example.springboot.entity.AopLog;
import com.example.springboot.entity.ReportInfo;
import com.example.springboot.entity.SysDict;
import com.example.springboot.mapper.AopLogMapper;
import com.example.springboot.mapper.ReportInfoMapper;
import com.example.springboot.service.IReportInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>
 * 报表 服务实现类
 * </p>
 *
 * @author 童志杰
 * @since 2019-04-02
 */
@Service
public class ReportInfoServiceImpl extends ServiceImpl<ReportInfoMapper, ReportInfo> implements IReportInfoService {
    private static final Logger log = LoggerFactory.getLogger(ReportInfoServiceImpl.class);

    @Autowired
    AbutButtInfoServiceImpl abutButtInfoService;

    @Autowired
    ReportInfoServiceImpl reportInfoService;
    @Autowired
    ReportInfoMapper reportInfoMapper;
    @Autowired
    SysDictServiceImpl sysDictService;

    @Autowired
    AopLogServiceImpl aopLogService;

    @Override
    @Transient
    public State startStaticial() throws Exception {
        //将原来的结果变为旧数据
        ReportInfo reportInfoOld = new ReportInfo();
        reportInfoOld.setStatus("1");
        QueryWrapper<ReportInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("type","1");
        reportInfoService.update(reportInfoOld, wrapper);

        //获取所有注册企业
        QueryWrapper<AbutButtInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AbutButtInfo::getDelFlag,"0").isNotNull(AbutButtInfo::getEsbCode);
        List<AbutButtInfo> abutButtInfoList = abutButtInfoService.list(queryWrapper);
        List<Map<String,Object>> mapList = new ArrayList<>();


        for (AbutButtInfo abutButtInfoEach : abutButtInfoList) {
            Map<String, Object> resultMap = new HashMap<>();
            //账号
            resultMap.put("phoneNum", abutButtInfoEach.getPhoneNum());
            //企业名称
            resultMap.put("producerName", abutButtInfoEach.getProducerName());
            //联系人电话
            resultMap.put("producerContactPsnMobile", abutButtInfoEach.getProducerContactPsnMobile());
            //注册时间
            QueryWrapper<AopLog> aopLogQueryWrapper = new QueryWrapper<>();
            aopLogQueryWrapper.lambda()
                    .eq(AopLog::getType,"ZCXXBC")
                    .eq(AopLog::getIsException,"0")
                    .like(AopLog::getResultValue,"\"code\":0")
                    .like(AopLog::getMethodParams,"\"phoneNum\":\""+abutButtInfoEach.getPhoneNum()+"\"")
                    .orderByAsc(AopLog::getLogTime)
                    .last("LIMIT 1");
            List<AopLog> aopLogList = aopLogService.list(aopLogQueryWrapper);
            LocalDateTime startDate = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            if(aopLogList!= null && aopLogList.size()>0){
                LocalDateTime logTime = aopLogList.get(0).getLogTime();
                startDate = logTime;
                resultMap.put("registerDate", logTime.format(formatter));
            }else{
                resultMap.put("registerDate",LocalDateTime.now().format(formatter));
            }


            //登陆次数
            int loginCount = reportInfoMapper.countLogin(abutButtInfoEach.getPhoneNum());
            resultMap.put("loginCount", loginCount);
            //当前状态
            resultMap.put("registerProgress", abutButtInfoEach.getProgress());

            //默认没有对接完成或者错误
            resultMap.put("electricity", 1);
            resultMap.put("water", 1);
            resultMap.put("remoteControl", 1);
            resultMap.put("warning", 1);
            resultMap.put("piles", 1);
            resultMap.put("wasteDay", 0);
            resultMap.put("company", 0);
            resultMap.put("parts", 0);

            if (abutButtInfoEach.getProgress() == 5) {
                //获取注册时间
                QueryWrapper<AopLog> queryWrapperEndtDate = new QueryWrapper<>();
                queryWrapperEndtDate.lambda()
                        .eq(AopLog::getType,"XSCSTJTG")
                        .eq(AopLog::getIsException,"0")
                        .like(AopLog::getResultValue,"\"code\":0")
                        .like(AopLog::getMethodParams,"\"phoneNum\":\""+abutButtInfoEach.getPhoneNum()+"\"")
                        .orderByDesc(AopLog::getLogTime)
                        .last("LIMIT 1");
                List<AopLog> listEnd = aopLogService.list(queryWrapperEndtDate);
                LocalDateTime endDate = LocalDateTime.now();
                if(listEnd!= null && listEnd.size()>0){
                    endDate = listEnd.get(0).getLogTime();
                    long wasteDay = endDate.toLocalDate().toEpochDay() - startDate.toLocalDate().toEpochDay();
                    resultMap.put("wasteDay", wasteDay);
                }else{
                    resultMap.put("wasteDay", 0);
                }
                Map<String,String> mapParams = new HashMap<>(1);
                mapParams.put("companyCode",abutButtInfoEach.getEsbCode());
                String contents = HttpRequestUtil.get(UrlConstants.ABUT_GET_COMPANY_COUNT,mapParams);
                if(StringUtils.isBlank(contents)){
                    resultMap.put("company", 0);
                }else{
                    Map<String,Object> statusResultMap = JsonUtils.json2pojo(contents,Map.class);
                    if("1".equals(statusResultMap.get("code")) ){
                        log.error((String) statusResultMap.get("message"));
                    }else{
                        resultMap.put("company", statusResultMap.get("data"));
                    }
                }
                contents = HttpRequestUtil.get(UrlConstants.ABUT_GET_DEVICE_COUNT,mapParams);
                if(StringUtils.isBlank(contents)){
                    resultMap.put("parts", 0);
                }else{
                    Map<String,Object> statusResultMap = JsonUtils.json2pojo(contents,Map.class);
                    if("1".equals(statusResultMap.get("code")) ){
                        log.error((String) statusResultMap.get("message"));
                    }else{
                        resultMap.put("parts", statusResultMap.get("data"));
                    }
                }
                contents = HttpRequestUtil.get(UrlConstants.ABUT_GET_DEVICE_TYPE,mapParams);
                if(StringUtils.isNotBlank(contents)){
                    Map<String,Object> statusResultMap = JsonUtils.json2pojo(contents,Map.class);
                    if("1".equals(statusResultMap.get("code")) ){
                        log.error((String) statusResultMap.get("message"));
                    }else{
                        Map<String,String> map = (Map<String, String>) statusResultMap.get("data");
                        resultMap.put("electricity", map.get("electricity") != null ?map.get("electricity") : 0);
                        resultMap.put("water", map.get("water")!= null ?map.get("water") : 0);
                        resultMap.put("remoteControl", map.get("remoteControl")!= null ?map.get("remoteControl") : 0);
                        resultMap.put("warning", map.get("warning")!= null ?map.get("warning") : 0);
                        resultMap.put("piles", map.get("piles")!= null ?map.get("piles") : 0);
                    }
                }


            }
            mapList.add(resultMap);
        }

        ReportInfo reportInfo = new ReportInfo();
        reportInfo.setType("1");
        reportInfo.setJson(JsonUtils.obj2json(mapList));
        reportInfo.setStatisticsDate(LocalDateTime.now());
        reportInfo.setStatisticsStatus("0");
        reportInfo.setStatus("0");
        reportInfo.setFlag("0");
        reportInfo.setId(UUID.randomUUID().toString().replaceAll("-",""));
        boolean save = reportInfoService.save(reportInfo);
        if(save){
            return State.SUCCESS();
        }else{
            return State.ERROR();
        }

    }
}

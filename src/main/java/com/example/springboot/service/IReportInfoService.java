package com.example.springboot.service;

import com.example.springboot.entity.ReportInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.utils.State;

/**
 * <p>
 * 报表 服务类
 * </p>
 *
 * @author 童志杰
 * @since 2019-04-02
 */
public interface IReportInfoService extends IService<ReportInfo> {

    State startStaticial() throws Exception;
}

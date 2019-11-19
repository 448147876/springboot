package com.example.springboot.service.impl;

import com.example.springboot.entity.DataCrawler;
import com.example.springboot.mapper.DataCrawlerMapper;
import com.example.springboot.service.IDataCrawlerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tzj
 * @since 2019-11-15
 */
@Service
public class DataCrawlerServiceImpl extends ServiceImpl<DataCrawlerMapper, DataCrawler> implements IDataCrawlerService {

}

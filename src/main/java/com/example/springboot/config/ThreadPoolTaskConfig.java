package com.example.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description: 线程池配置
 *
 * @author: Tong
 * @date:  2019/8/20
 */
@Configuration
@EnableAsync
public class ThreadPoolTaskConfig {

    private static final int corePoolSize = 20;       		// 核心线程数（默认线程数）
    private static final int maxPoolSize = 200;			    // 最大线程数
    private static final int keepAliveTime = 100;			// 允许线程空闲时间（单位：默认为秒）
    private static final int queueCapacity = 2000;			// 缓冲队列数
    private static final String threadNamePrefix = "Async-default-"; // 线程池名前缀

    @Bean
    public ThreadPoolTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveTime);
        executor.setThreadNamePrefix(threadNamePrefix);

        // 线程池对拒绝任务的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        return executor;
    }

}

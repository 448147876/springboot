package com.example.springboot.rabbitmq;

import com.example.springboot.config.RabbitConfig;
import com.example.springboot.utils.G_Code;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Component
@RequestMapping("/rabbitmq")
public class MsgCrawlerProducer implements RabbitTemplate.ConfirmCallback {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
    private RabbitTemplate rabbitTemplate;

    /**
     * 构造方法注入rabbitTemplate
     */
    @Autowired
    public MsgCrawlerProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this); //rabbitTemplate如果为单例的话，那回调就是最后设置的内容
    }

    @GetMapping("/sendcrawler")
    public void sendMsg() {
        boolean flag = true;
        while(flag){
            String resultStr = G_Code.orgCode();
            if(StringUtils.startsWith(resultStr,"ZZZZZZZZ")){
                flag = false;
            }
            CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_A, RabbitConfig.ROUTINGKEY_A, resultStr, correlationId);
        }
    }

    /**
     * 回调
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        logger.info(" 回调id:" + correlationData);
        if (b) {
            logger.info("消息成功消费");
        } else {
            logger.info("消息消费失败:" + s);
        }

    }
}

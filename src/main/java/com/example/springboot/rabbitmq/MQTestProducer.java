package com.example.springboot.rabbitmq;

import com.example.springboot.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping("/mqtest")
public class MQTestProducer {

    @Autowired
    RabbitMqUtil rabbitMqUtil;

    @GetMapping("/sendcrawler")
    public void sendMsg(String content) {

        rabbitMqUtil.send(content);
    }

    @GetMapping("/sendcrawlerQueue")
    public void sendMsg(String queue,String content) {

        rabbitMqUtil.send(queue,content);
    }

}

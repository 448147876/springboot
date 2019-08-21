package com.example.springboot.rabbitmq;


import com.example.springboot.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MQTestReceiver {


    @RabbitListener(queues = RabbitConfig.QUEUE_DEFAULT)
    public void getMsg(String content){
        System.out.println(content);
    }

}

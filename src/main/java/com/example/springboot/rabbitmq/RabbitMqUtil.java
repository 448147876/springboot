package com.example.springboot.rabbitmq;


import com.example.springboot.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.springboot.config.RabbitConfig.EXCHANGE_DEFAULT;

@Component
public class RabbitMqUtil {

    @Autowired
    RabbitTemplate rabbitTemplate;


    public void send(String content) {
        rabbitTemplate.convertAndSend(EXCHANGE_DEFAULT, RabbitConfig.ROUTINGKEY_DEFAULT, content);
    }

    public void send(String queueName, String content) {

        rabbitTemplate.convertAndSend(EXCHANGE_DEFAULT, queueName, content);
    }

    public void send(String exchange, String bindingKey, String content) {
        rabbitTemplate.convertAndSend(exchange, bindingKey, content);
    }


}

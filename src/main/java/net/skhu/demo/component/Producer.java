package net.skhu.demo.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ds on 2018-04-16.
 */

/**
 * routingKey와 message를 받는다.
 * routungKey가 queue이름이 된다.
 * 메시지는 Exchanger로 전송되고, Exchager가 queue로 전송한다.
 */

@Component
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendTo(String routingKey, String message){
        logger.info("send message....");

        this.rabbitTemplate.convertAndSend(routingKey, message);
    }

}


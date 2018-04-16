package net.skhu.demo.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by ds on 2018-04-16.
 */

/**
 * RabbitListener에 정의되어있는 Queue를 감시하다가 메시지가 들어오면 handler로 전해준다.
 */
@Component
public class Consumer {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @RabbitListener(queues = "${myqueue}")
    public void handler(String message){
        logger.info("consumer....> " + message);
    }

}

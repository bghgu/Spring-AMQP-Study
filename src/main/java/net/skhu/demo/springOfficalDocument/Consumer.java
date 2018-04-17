package net.skhu.demo.springOfficalDocument;

import net.skhu.demo.config.RabbitMqConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by ds on 2018-04-17.
 */

/**
 * 메시지를 받는 자
 */
public class Consumer {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(RabbitMqConfig.class);
        AmqpTemplate amqpTemplate = context.getBean(AmqpTemplate.class);
        System.out.println("Received: " + amqpTemplate.receiveAndConvert());
    }
}

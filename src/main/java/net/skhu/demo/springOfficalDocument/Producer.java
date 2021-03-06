package net.skhu.demo.springOfficalDocument;

import net.skhu.demo.config.RabbitMqConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by ds on 2018-04-17.
 */

/**
 * 메시지를 보내는 자
 */
public class Producer {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(RabbitMqConfig.class);
        AmqpTemplate amqpTemplate = context.getBean(AmqpTemplate.class);
        amqpTemplate.convertAndSend("Hello World");
        System.out.println("Sent: Hello World");
    }
}

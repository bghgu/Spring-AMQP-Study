package net.skhu.demo.RabbitMQ;

import net.skhu.demo.config.RabbitMqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by ds on 2018-04-16.
 */


@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(classes = {RabbitMqConfig.class})
public class ProducerTest {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void productTest(){
        rabbitTemplate.convertAndSend("Hello World");
    }
}

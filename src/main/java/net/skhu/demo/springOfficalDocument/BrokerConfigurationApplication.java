package net.skhu.demo.springOfficalDocument;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by ds on 2018-04-17.
 */

public class BrokerConfigurationApplication {

    /**
     *  AMQP broker만 사용하는 예제
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("rabbitConfiguration.xml");
        AmqpAdmin amqpAdmin = context.getBean(AmqpAdmin.class);
        Queue helloWorldQueue = new Queue("hello.world.queue");

        amqpAdmin.declareQueue(helloWorldQueue);

    }
}

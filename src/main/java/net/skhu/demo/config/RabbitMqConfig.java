package net.skhu.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ds on 2018-04-10.
 */

@Configuration
@EnableRabbit
public class RabbitMqConfig {

    private final Logger logger = LoggerFactory.getLogger(RabbitMqConfig.class);

    //@Value("${spring.rabbitmq.host}")
    private String rabbitHost = "127.0.0.1";

    //@Value("${spring.rabbitmq.port}")
    private int rabbitPort = 5672;

    //@Value("${spring.rabbitmq.username}")
    private String rabbitUsername = "guest";

    //@Value("${spring.rabbitmq.password}")
    private String rabbitPassword = "guest";

    private final String helloWorldQueueName = "hello.world.queue";

    /**
     * rabbit의 연결 관리
     * @return RabbitMQConnectFactory 설정 객체
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        logger.info("create MQ connectionFactory");
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setHost(rabbitHost);
        connectionFactory.setPort(rabbitPort);
        connectionFactory.setUsername(rabbitUsername);
        connectionFactory.setPassword(rabbitPassword);
        return connectionFactory;
    }

    /**
     * RQ 초기화?
     * @return 초기화된 RabbitMQConnectFaceory객체와 Rabbit Template
     */
    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    /**
     * 메시지 송, 수신을 위한 abstraction 템플릿 제공
     * @return Rabbit 사용을 위한 템플릿
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        //라우팅 키는 기본 교환을 위해 브로커에서 큐 이름으로 설정된다.
        template.setRoutingKey(this.helloWorldQueueName);
        //어디 큐로부터 동기 메시지를 받을지
        template.setQueue(this.helloWorldQueueName);
        return template;
    }

    /**
     * 모든 큐는 defalut 값으로 direct exchange에 바인딩 된다.
     */
    @Bean
    public Queue helloWorldQueue() {
        return new Queue(this.helloWorldQueueName);
    }

}

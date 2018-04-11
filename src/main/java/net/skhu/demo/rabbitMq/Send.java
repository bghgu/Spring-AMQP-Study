package net.skhu.demo.rabbitMq;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * Created by ds on 2018-04-10.
 */

public class Send {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] aegv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        //큐 이름, 데이터 지속성 부여 여부, ?, ?, ?
        //메시지 큐가 죽더라도 데이터는 보존
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        String message = "Hello World!";
        //producer측에서도 데이터 지속성 부여
        //MessageProperties.PERSISTENT_TEXT_PLAIN
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}

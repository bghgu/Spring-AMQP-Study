package net.skhu.demo.rabbitMq;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by ds on 2018-04-10.
 */
public class Recv {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //여러개의 큐를 사용하는 상황에서 메시지에 대한 순차적인 처리가 중요하다면 1로 설정
        //prefetchCount=1(channel.basicQos(1))
        channel.basicQos(1);
        //큐 이름, 데이터 지속성 부여 여부, ?, ?, ?
        //메시지 큐가 죽더라도 데이터는 보존
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                //ack를 명시적으로 보내자.
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        //큐 이름, autoAck
        //autoAck = true일 경우 recv가 죽어도 ack는 리턴되기 때문에 큐의 남은 데이터가 삭제된다
        //따라서 false로 ack가 무조건 리턴되지 않게 한다.
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}

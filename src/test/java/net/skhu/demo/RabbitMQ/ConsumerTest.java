package net.skhu.demo.RabbitMQ;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import net.skhu.demo.config.RabbitMqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ds on 2018-04-16.
 */

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(classes = {RabbitMqConfig.class})
public class ConsumerTest{

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    ConnectionFactory connectionFactory;

   /* @Test
    public void consumTest(){
        int maxthread =20;
        ExecutorService es = Executors.newFixedThreadPool(maxthread);
        try {
            Connection conn = connectionFactory.newConnection(es);

            // Thread 당 다른 Channel 을 사용하기 위해서 Thread수 만큼 별도의 채널을 생성하낟.
            for(int i=0;i<maxthread;i++){
                Channel channel = conn.createChannel();
                channel.basicQos(1);
                channel.basicConsume("test",false,new MyQueueConsumer(channel));
            }
            System.out.println("Invoke "+maxthread+" thread and wait for listening");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println(rabbitTemplate.receiveAndConvert());
        //System.exit(1);
    }*/

    public class MyQueueConsumer extends DefaultConsumer {

        Channel channel;
        public MyQueueConsumer(Channel channel) {
            super(channel);
            // TODO Auto-generated constructor stub
            this.channel = channel;
        }
        @Override
        public void handleDelivery(String consumeTag,
                                   Envelope envelope,
                                   AMQP.BasicProperties properties,
                                   byte[] body)
                throws IOException
        {
            String routingKey = envelope.getRoutingKey();
            String contentType = properties.getContentType();
            long deliveryTag = envelope.getDeliveryTag();

            // message handling logic here
            String msg = new String(body);
            UUID uuid = UUID.randomUUID();
            System.out.println(uuid+" S Channel :"+channel+" Thread:"+Thread.currentThread()+" msg:"+msg);

            // multiple - false if we are acknowledging multiple messages with the same delivery tag
            this.channel.basicAck(deliveryTag, false);
        }
    }
}
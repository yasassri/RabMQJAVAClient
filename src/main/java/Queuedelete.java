/**
 * Created by yasassri on 10/21/15.
 */

import com.rabbitmq.client.*;
import java.io.IOException;

public class Queuedelete {

        private static int counter;
        private static final String QUEUE_NAME = "DeadLetterChannel";

        public static void main(String[] argv) throws Exception {

            ConnectionFactory factory = new ConnectionFactory();
            factory.setVirtualHost("/carbon");
            factory.setUsername("admin");
            factory.setPassword("admin");
            factory.setHost("10.100.7.72");
            factory.setPort(5672);
            //factory.setUri("amqp://admin:admin@localhost/carbon?brokerlist='tcp://localhost:5672'");
            Connection connection = factory.newConnection();
            final Channel channel = connection.createChannel();

            System.out.println("Deleting Queue : " +QUEUE_NAME);
            //channel.queuePurge(QUEUE_NAME);
            channel.queueDelete(QUEUE_NAME,true,true);
//        channel.queueDelete(queueName2);
            channel.close();
        }

}

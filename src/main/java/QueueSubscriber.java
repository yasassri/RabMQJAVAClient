import com.rabbitmq.client.*;
import java.io.IOException;

/**
 * Created by yasassri on 10/20/15.
 */
public class QueueSubscriber {

    private static int counter;
    private static final String EXCHANGE_NAME = "amq.direct";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost("/carbon");
        factory.setUsername("user01");
        factory.setPassword("admin");
        factory.setHost("localhost");
        factory.setPort(5672);
        //factory.setUri("amqp://admin:admin@localhost/carbon?brokerlist='tcp://localhost:5672'");
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        String routingKey = "TEST";

        String queueName2 = "TEST";
        channel.queueDeclare(queueName2,true,false,false,null);

//      String queueName2 = channel.queueDeclare().getQueue();

        System.out.println("QUEUE NAME : " +queueName2);
        // Q Name , EXCHANGE, Routing Key
        channel.queueBind(queueName2, EXCHANGE_NAME, routingKey);

        System.out.println(" [*] Waiting for messages.");

        counter= 0;

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                counter++;
                String message = new String(body, "UTF-8");
                System.out.println(counter + " [x] Received '" + message + "'");
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        channel.basicConsume(queueName2, false, consumer);
//        Thread.sleep(10000);
//        System.out.println("Deleting the Queue");
//        channel.queueDelete(queueName2);
//        channel.close();
    }

}




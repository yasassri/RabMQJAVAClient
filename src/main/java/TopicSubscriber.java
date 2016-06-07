import com.rabbitmq.client.*;

import java.io.IOException;

public class TopicSubscriber {
    private static final String EXCHANGE_NAME = "amq.topic";

    // Set your Properties here
    private static final String userName = "admin";
    private static final String password = "admin";
    private static final String host = "localhost";
    private static final int amqpPort = 5672;

    private static final String routingKey = "yasas2";
    private static final String queueName2 = "yasas2";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost("/carbon");
        factory.setUsername(userName);
        factory.setPassword(password);
        factory.setHost(host);
        factory.setPort(amqpPort);
        //factory.setUri("amqp://admin:admin@localhost/carbon?brokerlist='tcp://localhost:5672'");
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        channel.queueDeclare(queueName2,true,false,false,null);

        System.out.println("TEMP NAME : " +queueName2);
        // Q Name , EXCHANGE, Routing Key
        channel.queueBind(queueName2, EXCHANGE_NAME, routingKey);

        System.out.println(" [*] Waiting for messages.");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        channel.basicConsume(queueName2, false, consumer);
    }
}
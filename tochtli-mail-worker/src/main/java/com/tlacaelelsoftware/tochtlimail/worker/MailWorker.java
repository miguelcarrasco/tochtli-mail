package com.tlacaelelsoftware.tochtlimail.worker;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.tlacaelelsoftware.tochtlimail.common.MailMessage;
import com.tlacaelelsoftware.tochtlimail.common.ValidatorHelper;
import org.codehaus.jackson.map.ObjectMapper;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Properties;

/**
 * Obtain mail send orders messages form queue and process it
 */
public class MailWorker {
    private Properties properties;

    private String host;
    private String password;
    private int port;
    private String taskQueueName;

    private ObjectMapper mapper = new ObjectMapper();

    public MailWorker(Properties properties) throws IOException {
        this.properties = properties;
        host = properties.getProperty("rabbitmq.host");
        taskQueueName = properties.getProperty("rabbitmq.taskQueueName");

        StringUtils.checkIsNotEmpty(host);
        StringUtils.checkIsNotEmpty(taskQueueName);

        port = Integer.valueOf(properties.getProperty("rabbitmq.port", "5672"));
        password = properties.getProperty("rabbitmq.password");
    }


    public void start() throws IOException, InterruptedException, MessagingException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        if (password != null && !"".equals(password)) {
            factory.setPassword(password);
        }

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(taskQueueName, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        channel.basicQos(1);

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(taskQueueName, false, consumer);


        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();

            System.out.println(" [x] Received ");
            doWork(delivery.getBody());
            System.out.println(" [x] Done");

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }

    private void doWork(byte[] body) throws IOException, MessagingException {

        MailMessage message = mapper.readValue(body, MailMessage.class);

        ValidatorHelper validatorHelper = new ValidatorHelper();
        validatorHelper.validate(message);

        System.out.println("   - Sending mail from " + message.getFromAddress() + "...");
        new Mailer(properties).send(message);

    }
}

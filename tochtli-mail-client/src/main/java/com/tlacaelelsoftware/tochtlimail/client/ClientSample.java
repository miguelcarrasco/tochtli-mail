package com.tlacaelelsoftware.tochtlimail.client;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.tlacaelelsoftware.tochtlimail.common.MailMessage;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class ClientSample {

    private static final String TASK_QUEUE_NAME = "tochtlimail";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);


        channel.basicPublish("", TASK_QUEUE_NAME,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                getMessageDTOBytes());
        System.out.println(" [x] Mail sent");

        channel.close();
        connection.close();
    }

    public static byte[] getMessageDTOBytes() throws IOException {

        MailMessage mailMessage = new MailMessage();
        mailMessage.setFromAddress("clesel@gmail.com");
        mailMessage.setToRecipients("clesel+test@gmail.com");
        mailMessage.setSubject("otro mas");
        mailMessage.setText("vamos a ver");

        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsBytes(mailMessage);
    }
}

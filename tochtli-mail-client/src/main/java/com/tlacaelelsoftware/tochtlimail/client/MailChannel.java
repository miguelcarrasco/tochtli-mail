package com.tlacaelelsoftware.tochtlimail.client;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.tlacaelelsoftware.tochtlimail.common.MailMessage;
import com.tlacaelelsoftware.tochtlimail.common.ValidatorHelper;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 *
 */
public class MailChannel {
    private String taskQueueName;
    private String host;
    private ConnectionFactory factory = new ConnectionFactory();

    public MailChannel(String taskQueueName, String host) {
        this.taskQueueName = taskQueueName;
        this.host = host;
        factory.setHost(host);
    }

    public void send(MailMessage mail) throws IOException {
        validate(mail);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(taskQueueName, true, false, false, null);

        channel.basicPublish("", taskQueueName,
                MessageProperties.PERSISTENT_TEXT_PLAIN, getBytes(mail));

        channel.close();
        connection.close();
    }

    private byte[] getBytes(MailMessage mail) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsBytes(mail);
    }

    private void validate(MailMessage mail) {
        ValidatorHelper<MailMessage> validator = new ValidatorHelper<MailMessage>();
        validator.validate(mail);
    }


}

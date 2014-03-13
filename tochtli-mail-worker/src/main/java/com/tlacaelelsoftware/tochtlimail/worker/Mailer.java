package com.tlacaelelsoftware.tochtlimail.worker;

import com.tlacaelelsoftware.tochtlimail.common.MailMessage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mailer {

    private Properties props;

    private String username;
    private String password;

    public Mailer(Properties props) {
        this.props = props;
        username = props.getProperty("auth.mail.username");
        password = props.getProperty("auth.mail.password");
    }

    public void send(MailMessage mailMessage) throws MessagingException {
        //TODO validate mailMessage

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(mailMessage.getFromAddress()));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailMessage.getToRecipients()));
        message.setSubject(mailMessage.getSubject());
        message.setContent(mailMessage.getText(), mailMessage.getContentType());

        Transport.send(message);
    }
}

package com.tlacaelelsoftware.tochtlimail.sample;

import com.tlacaelelsoftware.tochtlimail.client.MailChannel;
import com.tlacaelelsoftware.tochtlimail.common.MailMessage;

import java.io.IOException;

/**
 * Tochtli Mail Client Example
 *
 */
public class ClientSample
{
    public static void main( String[] args )
    {
        MailChannel channel = new MailChannel("tochtlimail","localhost");
        MailMessage message = new MailMessage("clesel@gmail.com","clesel+test@gmail.com","Tochtli ejemplo 2", "Este es el contenido");

        try {
            channel.send(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

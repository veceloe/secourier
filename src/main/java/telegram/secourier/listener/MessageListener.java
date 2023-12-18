package telegram.secourier.listener;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;

public interface MessageListener {
    void startListening() throws Exception;

    void handleNewMessage(MimeMessage message) throws MessagingException, IOException;

    void stopListening() throws Exception;

}

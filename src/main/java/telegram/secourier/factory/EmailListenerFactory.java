package telegram.secourier.factory;

import org.springframework.stereotype.Component;
import telegram.secourier.sender.MessageSender;
import telegram.secourier.listener.EmailListener;

@Component
public class EmailListenerFactory {
    public EmailListener create(String email, String password, MessageSender sender, Long chatId) {
        return new EmailListener(email, password, sender, chatId);
    }
}

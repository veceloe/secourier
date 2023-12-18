package telegram.secourier.manager;

import jakarta.mail.MessagingException;
import org.springframework.stereotype.Component;
import telegram.secourier.sender.MessageSender;
import telegram.secourier.factory.EmailListenerFactory;
import telegram.secourier.listener.EmailListener;

@Component
public class EmailListenerManager {

    private final EmailListenerFactory emailListenerFactory;
    private final MessageSender messageSender;

    public EmailListenerManager(EmailListenerFactory emailListenerFactory, MessageSender messageSender) {
        this.emailListenerFactory = emailListenerFactory;
        this.messageSender = messageSender;
    }

    public void startEmailListener(String email, String password, Long chatId) throws MessagingException {
        EmailListener emailListener = emailListenerFactory.create(email, password, messageSender, chatId);
        emailListener.startListening(); // Запуск слушателя новых сообщений
        messageSender.sendMessage(chatId, "Я буду присылать тебе коды подтверждения из писем, которые ты получаешь на свой почтовый ящик.");
    }
}

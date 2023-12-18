package telegram.secourier.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.secourier.manager.EmailListenerManager;
import telegram.secourier.handler.InputHandler;
import telegram.secourier.sender.MessageSender;

@Component
public class EmailCommand implements BotCommand {

    private final InputHandler inputHandler;
    private final EmailListenerManager emailListenerManager;
    private final MessageSender messageSender;

    @Autowired
    public EmailCommand(InputHandler inputHandler, EmailListenerManager emailListenerManager, MessageSender messageSender) {
        this.inputHandler = inputHandler;
        this.emailListenerManager = emailListenerManager;
        this.messageSender = messageSender;
    }

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        String messageText = update.getMessage().getText();
        try {
            String email = messageText.split(" ")[0];
            String password = messageText.split(" ")[1];

            if (!inputHandler.isValidEmail(email)) {
                throw new IllegalArgumentException("Неверный формат email. Попробуй еще раз.");
            }

            if (!inputHandler.isValidPassword(password)) {
                throw new IllegalArgumentException("Пароль должен содержать не менее 8 символов. Попробуй еще раз.");
            }

            if (!inputHandler.isValid(messageText)) {
                throw new IllegalArgumentException("Неверный ввод. Используйте формат: [email] [пароль].");
            }

            emailListenerManager.startEmailListener(email, password, chatId);

        } catch (Exception e) {
            messageSender.sendMessage(chatId, e.getMessage());
        }
    }
}

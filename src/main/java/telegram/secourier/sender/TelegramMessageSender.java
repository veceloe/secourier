package telegram.secourier.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import telegram.secourier.bot.TelegramBot;

@Component
public class TelegramMessageSender implements MessageSender {

    private final TelegramBot telegramBot;

    @Autowired
    public TelegramMessageSender(@Lazy TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void sendMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(Long.toString(chatId));
        sendMessage.setText(message);
        try {
            telegramBot.execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

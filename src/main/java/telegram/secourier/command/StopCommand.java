package telegram.secourier.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.secourier.sender.MessageSender;
import telegram.secourier.sender.TelegramMessageSender;

@Component
public class StopCommand implements BotCommand {
    private final TelegramMessageSender sender;

    @Autowired
    public StopCommand(MessageSender sender) {
        this.sender = (TelegramMessageSender) sender;
    }

    @Override
    public void execute(Update update) {
        String answer = "Бот остановлен. Для возобновления работы введи команду /start";
        sender.sendMessage(update.getMessage().getChatId(), answer);
    }
}
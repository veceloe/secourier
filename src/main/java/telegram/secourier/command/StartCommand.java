package telegram.secourier.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.secourier.sender.MessageSender;

@Component
public class StartCommand implements BotCommand {
    private final MessageSender sender;

    @Autowired
    public StartCommand(MessageSender sender) {
        this.sender = sender;
    }

    @Override
    public void execute(Update update) {
        String username = update.getMessage().getChat().getFirstName();
        String answer = "Привет, "+ username +"! Это бот Secourier. Я помогу тебе получать коды" +
                        " подтверждения из писем, которые ты получаешь на свой почтовый ящик." + "\n"
                        + "Для начала работы введи свой email и пароль приложения через пробел." +
                        " Его можно получить в настройках почты." + "\n"
                        + "Буду ждать твоего сообщения.";
        sender.sendMessage(update.getMessage().getChatId(), answer);
    }
}
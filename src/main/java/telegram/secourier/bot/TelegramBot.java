package telegram.secourier.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.secourier.command.BotCommand;
import telegram.secourier.command.EmailCommand;
import telegram.secourier.command.StartCommand;
import telegram.secourier.command.StopCommand;
import telegram.secourier.config.BotConfig;
import telegram.secourier.factory.EmailListenerFactory;
import telegram.secourier.handler.InputHandler;
import telegram.secourier.manager.EmailListenerManager;
import telegram.secourier.sender.TelegramMessageSender;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final TelegramMessageSender messageSender;

    @Autowired
    public TelegramBot(BotConfig botConfig, TelegramMessageSender messageSender) {
        this.botConfig = botConfig;
        this.messageSender = messageSender;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            switch (messageText) {
                case "/start":
                    BotCommand startCommand = new StartCommand(messageSender);
                    startCommand.execute(update);

                    break;
                case "/stop":
                    BotCommand stopCommand = new StopCommand(messageSender);
                    stopCommand.execute(update);
                    break;
                default:
                    BotCommand emailCommand = new EmailCommand(new InputHandler(),
                            new EmailListenerManager(new EmailListenerFactory(), messageSender),
                            messageSender);
                    emailCommand.execute(update);
                    break;
            }
        }
    }
}
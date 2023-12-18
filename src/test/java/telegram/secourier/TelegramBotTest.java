package telegram.secourier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.secourier.bot.TelegramBot;
import telegram.secourier.command.BotCommand;
import telegram.secourier.command.EmailCommand;
import telegram.secourier.command.StartCommand;
import telegram.secourier.command.StopCommand;
import telegram.secourier.config.BotConfig;
import telegram.secourier.sender.TelegramMessageSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TelegramBotTest {

    @Mock
    private BotConfig mockBotConfig;

    @Mock
    private TelegramMessageSender mockMessageSender;

    private TelegramBot telegramBot;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        telegramBot = new TelegramBot(mockBotConfig, mockMessageSender);
    }

    @Test
    void testGetBotUsername() {
        when(mockBotConfig.getBotName()).thenReturn(System.getProperty("telegram.bot.name"));
        assertEquals(System.getProperty("telegram.bot.name"), telegramBot.getBotUsername());
    }

    @Test
    void testGetBotToken() {
        when(mockBotConfig.getToken()).thenReturn(System.getProperty("telegram.bot.name"));
        assertEquals(System.getProperty("telegram.bot.token"), telegramBot.getBotToken());
    }

}


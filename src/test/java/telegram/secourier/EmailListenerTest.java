package telegram.secourier;
import jakarta.mail.Address;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import telegram.secourier.listener.EmailListener;
import telegram.secourier.sender.MessageSender;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

public class EmailListenerTest {

    private EmailListener emailListener;
    private MessageSender mockMessageSender;

    @BeforeEach
    void setUp() {
        mockMessageSender = mock(MessageSender.class);
        emailListener = new EmailListener("test@example.com", "password", mockMessageSender, 123);
    }

    @Test
    void testHandleNewMessage() throws Exception {
        MimeMessage mockMessage = mock(MimeMessage.class);
        when(mockMessage.getSubject()).thenReturn("Test Subject");
        when(mockMessage.getFrom()).thenReturn(new Address[]{new InternetAddress("sender@example.com")});
        when(mockMessage.getContent()).thenReturn("Test Content");

        emailListener.handleNewMessage(mockMessage);

        // Verify that sendMessage is called with the expected arguments
        verify(mockMessageSender).sendMessage(eq(123L), anyString());
    }

    @Test
    void testStopListening() {
        emailListener.stopListening();
        assertFalse(emailListener.isListening());
    }

    // Add more test cases as needed for your specific requirements
}


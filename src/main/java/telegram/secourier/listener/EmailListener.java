package telegram.secourier.listener;

import jakarta.mail.*;
import jakarta.mail.event.MessageCountAdapter;
import jakarta.mail.event.MessageCountEvent;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.angus.mail.imap.IMAPFolder;
import telegram.secourier.sender.MessageSender;
import telegram.secourier.config.MailProperties;

import java.io.IOException;
import java.util.Properties;

import static telegram.secourier.service.EmailService.getTextFromMimeMultipart;
import static telegram.secourier.service.EmailService.parseServiceCode;
@Setter
@Getter
public class EmailListener implements MessageListener{
    private final MessageSender messageSender;
    private final long chatId;
    private final String email;
    private final String password;
    private final Properties properties;
    private boolean listening;

    public EmailListener(String email, String password,
                         MessageSender messageSender, long chatId) {
        this.email = email;
        this.password = password;
        this.properties = new Properties();
        this.listening = true;
        this.messageSender = messageSender;
        this.chatId = chatId;
        new MailProperties(email, password);
    }

    @Override
    public void startListening() throws MessagingException {
        Session session = Session.getInstance(properties, null);
        Store store = session.getStore("imaps");
        store.connect("imap." + email.split("@")[1], email, password);
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);

        inbox.addMessageCountListener(new MessageCountAdapter() {
            public void messagesAdded(MessageCountEvent e) {
                Message[] messages = e.getMessages();
                for (Message message : messages) {
                    try {
                        handleNewMessage((MimeMessage) message);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        Thread idleThread = new Thread(() -> {
            while (listening) {
                try {
                    ensureOpen(inbox);
                    ((IMAPFolder)inbox).idle();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        idleThread.start();
    }

    public void ensureOpen(Folder folder) throws MessagingException {
        if (!folder.isOpen()) {
            folder.open(Folder.READ_WRITE);
        }
    }
    @Override
    public void handleNewMessage(MimeMessage message) throws MessagingException, IOException {
        String subject = message.getSubject();
        String sender = message.getFrom()[0].toString().substring(0, message.getFrom()[0].toString().indexOf("<"));
        String content = getTextFromMimeMultipart((MimeMultipart) message.getContent());
        String code = parseServiceCode(subject, content, sender);
        System.out.println("New email");
        if (code != null) {
            messageSender.sendMessage(chatId, code);
        }
    }
    @Override
    public void stopListening() {
        this.listening = false;
    }

}

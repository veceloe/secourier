package telegram.secourier.sender;

public interface MessageSender {
    void sendMessage(long chatId, String message);
}

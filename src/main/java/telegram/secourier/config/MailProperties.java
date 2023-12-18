package telegram.secourier.config;

import lombok.Getter;
import lombok.Setter;

import java.util.Properties;
@Getter
@Setter
public class MailProperties {
    private final String email;
    private final String password;
    private final Properties properties;

    public MailProperties(String email, String password) {
        this.email = email;
        this.password = password;
        this.properties = System.getProperties();
        setMailProperties();
    }

    private void setMailProperties() {
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", "imap." + email.split("@")[1]);
        properties.put("mail.imaps.port", "993");
        properties.put("mail.imaps.timeout", "10000");
        properties.put("mail.imaps.connectiontimeout", "10000");
        properties.put("mail.imaps.ssl.trust", "*");
        properties.put("mail.imaps.ssl.enable", "true");
        properties.put("mail.imaps.ssl.socketFactory", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.imaps.ssl.socketFactory.fallback", "false");
        properties.put("mail.imaps.ssl.socketFactory.port", "993");
    }

}

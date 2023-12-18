package telegram.secourier.service;

import jakarta.mail.BodyPart;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMultipart;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailService {

    public static String getTextFromMimeMultipart(MimeMultipart multipart) throws MessagingException, IOException {
        StringBuilder result = new StringBuilder();
        int count = multipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = multipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result.append(html);
                break; // Found the HTML part, no need to look further
            }
        }
        return result.toString();
    }

    public static String parseServiceCode(String subject, String htmlContent, String sender) {
        // Список ключевых слов для поиска в теме письма
        String[] keywords = {
                "verification", "verify", "confirm", "confirmation", "code",
                "верификация", "подтверждение", "код", "активация", "валидация"
        };
        // Проверяем наличие ключевых слов в теме письма (игнорируем регистр букв)
        for (String keyword : keywords) {
            if (subject.toLowerCase().contains(keyword.toLowerCase())) {
                // Если ключевое слово найдено, ищем код в теле письма с помощью JSoup
                Document doc = Jsoup.parse(htmlContent);
                Elements elements = doc.getAllElements();
                for (Element element : elements) {
                    // Получаем текст из элемента
                    String text = element.text();
                    // Проверяем, что текст содержит только 6 символов
                    Pattern pattern = Pattern.compile("^[0-9]{6}$");
                    Matcher matcher = pattern.matcher(text);
                    if (matcher.matches()) {
                        // Если код найден, возвращаем его
                        return "Новый код верификации от " + sender + " - " + text;
                    }
                }
                break;
            }
        }
        // Если ключевые слова не найдены или код не был найден, возвращаем null
        return null;
    }

}

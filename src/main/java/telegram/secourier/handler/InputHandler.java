package telegram.secourier.handler;

import org.springframework.stereotype.Component;

@Component
public class InputHandler {

    public boolean isValid(String input) {
        String[] parts = input.split(" ");
        if (parts.length != 2) {
            return false;
        }
        return isValidEmail(parts[0]) && isValidPassword(parts[1]);
    }

    public boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    public boolean isValidPassword(String password) {
        return password.length() >= 8;
    }


}

package telegram.secourier;
import org.junit.jupiter.api.Test;
import telegram.secourier.handler.InputHandler;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InputHandlerTest {

    @Test
    void testIsValid() {
        InputHandler inputHandler = new InputHandler();

        // Valid input
        assertTrue(inputHandler.isValid("test@example.com password"));

        // Invalid input (missing password)
        assertFalse(inputHandler.isValid("test@example.com"));

        // Invalid input (more than two parts)
        assertFalse(inputHandler.isValid("test@example.com password extra"));

        // Invalid input (invalid email)
        assertFalse(inputHandler.isValid("invalidemail password"));

        // Invalid input (invalid password)
        assertFalse(inputHandler.isValid("test@example.com short"));
    }

    @Test
    void testIsValidEmail() {
        InputHandler inputHandler = new InputHandler();

        // Valid email
        assertTrue(inputHandler.isValidEmail("test@example.com"));

        // Invalid email (missing @)
        assertFalse(inputHandler.isValidEmail("testexample.com"));

        // Invalid email (missing dot)
        assertFalse(inputHandler.isValidEmail("test@examplecom"));
    }

    @Test
    void testIsValidPassword() {
        InputHandler inputHandler = new InputHandler();

        // Valid password
        assertTrue(inputHandler.isValidPassword("password123"));

        // Invalid password (less than 8 characters)
        assertFalse(inputHandler.isValidPassword("pass"));

        // Invalid password (exactly 8 characters)
        assertFalse(inputHandler.isValidPassword("eightchr"));

        // Valid password (exactly 9 characters)
        assertTrue(inputHandler.isValidPassword("ninecharac"));
    }
}


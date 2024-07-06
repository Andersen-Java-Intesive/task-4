package org.example.service;

import org.example.model.User;
import org.example.util.ValidateUserUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidateUserTest {


    @Test
    public void FirstNameNull() {
        User user = new User(null, "Schwarzenegger", 76);
        assertEquals("Empty First Name field", ValidateUserUtils.validate(user));
    }

    @Test
    public void FirstNameBlank() {
        User user = new User("    ", "Schwarzenegger", 76);
        assertEquals("Empty First Name field", ValidateUserUtils.validate(user));
    }

    @Test
    public void FirstNameEmpty() {
        User user = new User("", "Schwarzenegger", 76);
        assertEquals("Empty First Name field", ValidateUserUtils.validate(user));
    }

    @Test
    public void SecondNameNull() {
        User user = new User("Arnold", null, 76);
        assertEquals("Empty Second Name field", ValidateUserUtils.validate(user));
    }

    @Test
    public void SecondNameBlank() {
        User user = new User("Arnold", "   ", 76);
        assertEquals("Empty Second Name field", ValidateUserUtils.validate(user));
    }

    @Test
    public void SecondNameEmpty() {
        User user = new User("Arnold", "", 76);
        assertEquals("Empty Second Name field", ValidateUserUtils.validate(user));
    }

    @Test
    public void FirstNamePunctuation() {
        User user = new User("Arnold'", "Schwarzenegger", 54);
        assertEquals("No punctuation allowed", ValidateUserUtils.validate(user));
    }

    @Test
    public void SecondNamePunctuation() {
        User user = new User("Arnold", "Schwarzenegger'", 45);
        assertEquals("No punctuation allowed", ValidateUserUtils.validate(user));
    }

    @Test
    public void FirstNameNonLatin() {
        User user = new User("Арнольд", "Schwarzenegger", 76);
        assertEquals("No non-latin characters allowed", ValidateUserUtils.validate(user));
    }

    @Test
    public void SecondNameNonLatin() {
        User user = new User("Arnold", "Шварценеггер", 76);
        assertEquals("No non-latin characters allowed", ValidateUserUtils.validate(user));
    }

    @Test
    public void FirstNameTooLong() {
        User user = new User("Arnold".repeat(10), "Schwarzenegger", 76);
        assertEquals("First Name field must be no more than 50 characters long", ValidateUserUtils.validate(user));
    }

    @Test
    public void SecondNameTooLong() {
        User user = new User("Arnold", "Schwarzenegger".repeat(5), 76);
        assertEquals("Second Name field must be no more than 50 characters long", ValidateUserUtils.validate(user));
    }

    @Test
    public void AgeTooHigh() {
        User user = new User("Immortal", "Man", 1000);
        assertEquals("Age is improbable", ValidateUserUtils.validate(user));
    }

    @Test
    public void AgeTooLow() {
        User user = new User("The", "Unborn", -1);
        assertEquals("Age is improbable", ValidateUserUtils.validate(user));
    }

    @Test
    public void ValidUser() {
        User user = new User("Arnold", "Schwarzenegger", 76);
        assertEquals("", ValidateUserUtils.validate(user));
    }
}

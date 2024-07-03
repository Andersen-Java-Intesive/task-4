package org.example.validation;

import org.example.model.User;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUser {
    public static String validate (User user) {
        if (Objects.equals(null, user.getFirstName()) || user.getFirstName().isBlank() || user.getFirstName().isEmpty()) {
            return "Empty First Name field";
        }
        if (Objects.equals(null, user.getSecondName()) || user.getSecondName().isBlank() || user.getSecondName().isEmpty()) {
            return "Empty Second Name field";
        }
        if (containsPunctuation(user.getFirstName()) || containsPunctuation(user.getSecondName())) {
            return "No punctuation allowed";
        }
        if (user.getFirstName().length() > 50) {
            return "First Name field must be no more than 50 characters long";
        }
        if (user.getSecondName().length() > 50) {
            return "Second Name field must be no more than 50 characters long";
        }
        if (user.getAge() > 999 || user.getAge() < 0) {
            return "Age is improbable";
        }
        return "";
    }

    private static boolean containsPunctuation(String input) {

        String regex = "[\\p{Punct}]";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(input);

        return matcher.find();
    }
}

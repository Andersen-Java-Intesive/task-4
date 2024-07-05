package org.example.util;

import org.example.dto.UserDto;
import org.example.model.User;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUserUtils {
    public static String validate (UserDto userDto) {
        if (Objects.equals(null, userDto.getFirstName()) || userDto.getFirstName().isBlank() || userDto.getFirstName().isEmpty()) {
            return "Empty First Name field";
        }
        if (Objects.equals(null, userDto.getSecondName()) || userDto.getSecondName().isBlank() || userDto.getSecondName().isEmpty()) {
            return "Empty Second Name field";
        }
        if (containsPunctuation(userDto.getFirstName()) || containsPunctuation(userDto.getSecondName())) {
            return "No punctuation allowed";
        }
        if (containsNonLatin(userDto.getFirstName()) || containsNonLatin(userDto.getSecondName())) {
            return "No non-latin characters allowed";
        }
        if (userDto.getFirstName().length() > 50) {
            return "First Name field must be no more than 50 characters long";
        }
        if (userDto.getSecondName().length() > 50) {
            return "Second Name field must be no more than 50 characters long";
        }
        if (userDto.getAge() > 999 || userDto.getAge() < 0) {
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

    private static boolean containsNonLatin(String input) {

        String regex = "[^A-Za-z\\s]";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(input);

        return matcher.find();
    }
}

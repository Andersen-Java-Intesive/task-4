package org.example.validation.util;

import org.example.dto.UserDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class ValidateUserUtils {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static boolean validate(UserDto userDto) {
        HashSet<ConstraintViolation<UserDto>> errors = new LinkedHashSet<>(validator.validate(userDto));
        StringBuilder errorMessage = new StringBuilder();
        if (!errors.isEmpty()) {
            errors.forEach(userConstraintViolation -> errorMessage.append(userConstraintViolation.getMessage()).append(" "));
        }
        if (!errorMessage.toString().isEmpty()) {
            // TODO change to UserValidationException
            throw new RuntimeException(errorMessage.toString());
        }
        return true;
    }

}

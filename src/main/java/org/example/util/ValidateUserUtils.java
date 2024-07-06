package org.example.util;

import org.example.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUserUtils {
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static String validate(User user) {
        HashSet<ConstraintViolation<User>> errors = new LinkedHashSet<>(validator.validate(user));
        StringBuilder errormessage = new StringBuilder();
        if (!errors.isEmpty()) {
            errors.forEach(userConstraintViolation -> errormessage.append(userConstraintViolation.getMessage()).append(" "));
        }
        return errormessage.toString();
    }
}
package org.example.validation.util;

import org.example.dto.MarkDto;
import org.example.exception.MarkValidationException;

import javax.validation.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class ValidateMarkUtils {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static boolean validate(MarkDto markDto) {
        Set<ConstraintViolation<MarkDto>> errors = new LinkedHashSet<>(validator.validate(markDto));
        StringBuilder errorMessage = new StringBuilder();
        if (!errors.isEmpty()) {
            errors.forEach(markConstraintViolation -> errorMessage.append(markConstraintViolation.getMessage()).append(" "));
        }
        if (!errorMessage.toString().isEmpty()) {
            throw new MarkValidationException(errorMessage.toString());
        }
        return true;
    }
}

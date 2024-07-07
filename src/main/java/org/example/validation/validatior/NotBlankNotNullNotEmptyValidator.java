package org.example.validation.validatior;

import org.example.validation.annotation.NotBlankNotNullNotEmpty;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotBlankNotNullNotEmptyValidator implements ConstraintValidator<NotBlankNotNullNotEmpty, String> {
    @Override
    public void initialize(NotBlankNotNullNotEmpty constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty() || value.isBlank()) {
            return false;
        }
        return true;
    }
}

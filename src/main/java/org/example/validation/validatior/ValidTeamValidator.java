package org.example.validation.validatior;

import org.example.model.enums.Team;
import org.example.validation.annotation.ValidTeam;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidTeamValidator implements ConstraintValidator<ValidTeam, String> {

    @Override
    public void initialize(ValidTeam constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        try {
            Team.valueOf(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

package org.example.validation.validatior;

import org.example.model.enums.Team;
import org.example.validation.annotation.ValidTeam;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidTeamValidator implements ConstraintValidator<ValidTeam, Team> {

    @Override
    public void initialize(ValidTeam constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Team value, ConstraintValidatorContext context) {
        for (Team team : Team.values()) {
            if (team.equals(value)) {
                return true;
            }
        }
        return false;
    }
}

package org.example.validation.annotation;

import org.example.validation.validatior.NotBlankNotNullNotEmptyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = { NotBlankNotNullNotEmptyValidator.class })
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankNotNullNotEmpty {
    String message() default "Empty First/Second Name field";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

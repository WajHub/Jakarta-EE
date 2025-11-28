package org.example.pokemon.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = LevelValidator.class)
@Documented
public @interface ValidateLevel {
    String message() default "Invalid level (must be greater than evolution level requirements)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

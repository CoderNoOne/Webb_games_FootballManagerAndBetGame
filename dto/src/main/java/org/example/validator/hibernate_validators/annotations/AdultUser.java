package org.example.validator.hibernate_validators.annotations;


import org.example.validator.hibernate_validators.impl.BirthDateValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BirthDateValidatorImpl.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AdultUser {

    String message() default "Birth date should indicate you are an adult";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

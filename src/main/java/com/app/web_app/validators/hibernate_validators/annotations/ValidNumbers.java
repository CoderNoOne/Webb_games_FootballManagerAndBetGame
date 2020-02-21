package com.app.web_app.validators.hibernate_validators.annotations;


import com.app.web_app.validators.hibernate_validators.impl.ValidNumbersValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidNumbersValidatorImpl.class)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidNumbers {

    String message() default "Player numbers must be unique and in the range 1-99";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package org.example.validator.hibernate_validators.annotations;



import org.example.validator.hibernate_validators.impl.FormationValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FormationValidatorImpl.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFormation {

    String message() default "Players must be unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

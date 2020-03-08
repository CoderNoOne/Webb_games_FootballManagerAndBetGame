package org.example.validator.hibernate_validators.annotations;


import org.example.validator.hibernate_validators.impl.UniquePlayersValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniquePlayersValidatorImpl.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniquePlayers {

    String message() default "Not unique players selected";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

package org.example.validator.hibernate_validators.annotations;



import org.example.validator.hibernate_validators.impl.ValidPhotoTypeValidatorImpl;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidPhotoTypeValidatorImpl.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhotoType {

    String message() default "Possible photo extensions are .jpg, .jpeg, .png, .gif";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

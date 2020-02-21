package com.app.web_app.validators.hibernate_validators.annotations;

import com.app.web_app.validators.hibernate_validators.impl.PasswordValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidatorImpl.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    String regex() default "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s)[0-9a-zA-Z!@#$%^&*()]*$";

    int minLength() default 8;

    int maxLength() default 30;

    boolean useDetailedMessage() default false;
    boolean useCustomMessage() default false;

    String message() default "Provided password: ${validatedValue.password} is not correct." +
            " Password must contain at least one lowercase letter, one digit and one uppercase letter." +
            " Minimum length: {minLength}. Maximum length: {maxLength} characters." +
            " Password confirmation must be identical as well";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

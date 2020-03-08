package org.example.validator.hibernate_validators.impl;



import org.example.model.core.PasswordDto;
import org.example.validator.hibernate_validators.annotations.ValidPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PasswordValidatorImpl implements ConstraintValidator<ValidPassword, PasswordDto> {

    private String message;
    private String regex;
    private int minLength;
    private int maxLength;
    private boolean useDetailedMessage;
    private boolean useCustomMessage;
    private Map<String, String> errors;

    public void initialize(ValidPassword constraint) {
        this.regex = constraint.regex();
        this.minLength = constraint.minLength();
        this.maxLength = constraint.maxLength();
        this.message = constraint.message();
        this.useDetailedMessage = constraint.useDetailedMessage();
        this.useCustomMessage = constraint.useCustomMessage();
        this.errors = new HashMap<>();
    }

    private boolean isPasswordAppropriateLength(PasswordDto passwordDto) {
        return passwordDto.getPassword() != null && passwordDto.getPassword().length() >= minLength && passwordDto.getPassword().length() <= maxLength;
    }

    public boolean isValid(PasswordDto passwordDto, ConstraintValidatorContext context) {

        errors.clear();

        if (passwordDto != null) {

            if (!arePasswordsSet(passwordDto)) {
                errors.put("Passwords not present", "Password and his confirmation must be present");
            } else {

                if (!arePasswordsTheSame(passwordDto)) {
                    errors.put("Not valid confirmation", "Password confirmation isn't the same as the original one");
                }

                if (!isPasswordAppropriateLength(passwordDto)) {
                    errors.put("Not valid password length", String.format("Password length should be %d-%d", minLength, maxLength));
                }

                if (!isPasswordAppropriatePattern(passwordDto)) {
                    errors.put("Not valid password pattern", "Password must contain at least 1 uppercase letter, 1 lowercase letter and 1 digit");
                }
            }
        } else {
            errors.put("Null password object", "Something went wrong with password validation");

        }

        setMessage(context);

        return isPasswordValid();
    }

    private boolean arePasswordsSet(PasswordDto passwordDto) {
        return !passwordDto.getPassword().equals("") && !passwordDto.getConfirmationPassword().equals("");
    }

    private boolean isPasswordValid() {
        return errors.isEmpty();
    }

    private boolean isPasswordAppropriatePattern(PasswordDto passwordDto) {
        return passwordDto.getPassword() != null && passwordDto.getPassword().matches(regex);
    }

    private void setMessage(ConstraintValidatorContext context) {

        if (useDetailedMessage && !useCustomMessage) {

            String generatedErrorMessage = String.join(", ", errors.values());

            context.buildConstraintViolationWithTemplate(generatedErrorMessage)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
    }

    private boolean arePasswordsTheSame(PasswordDto passwordDto) {

        return Objects.requireNonNull(passwordDto.getPassword()).equals(passwordDto.getConfirmationPassword());
    }
}

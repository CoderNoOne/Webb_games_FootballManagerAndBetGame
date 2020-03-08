package org.example.validator.hibernate_validators.impl;



import org.example.validator.hibernate_validators.annotations.HourFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HourValidatorImpl implements ConstraintValidator<HourFormat, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        if (value.isBlank()) {
            return true;
        }

        boolean isValidPattern = value.matches("[\\d]{1,2}:[\\d]{1,2}");

        if (isValidPattern) {
            String[] values = value.split("[:]");

            int hour = Integer.parseInt(values[0]);
            int minutes = Integer.parseInt(values[1]);

            if (hour >= 0 && hour <= 23) {
                return minutes >= 0 && minutes <= 59;
            }
        }

        return false;
    }
}

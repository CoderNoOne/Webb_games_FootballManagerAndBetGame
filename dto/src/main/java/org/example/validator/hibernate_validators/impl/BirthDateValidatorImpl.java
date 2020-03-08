package org.example.validator.hibernate_validators.impl;


import org.example.validator.hibernate_validators.annotations.AdultUser;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class BirthDateValidatorImpl implements ConstraintValidator<AdultUser, LocalDate> {

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext constraintValidatorContext) {
        return birthDate != null && Period.between(birthDate, LocalDate.now()).getYears() >= 18;
    }
}

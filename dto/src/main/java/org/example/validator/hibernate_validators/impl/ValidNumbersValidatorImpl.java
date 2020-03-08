package org.example.validator.hibernate_validators.impl;

import org.example.model.fm.PlayersNumberDto;
import org.example.validator.hibernate_validators.annotations.ValidNumbers;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

@Component
public class ValidNumbersValidatorImpl implements ConstraintValidator<ValidNumbers, PlayersNumberDto> {

    @Override
    public boolean isValid(PlayersNumberDto playersNumberDto, ConstraintValidatorContext constraintValidatorContext) {

        Map<Integer, Integer> playersNumber = playersNumberDto.getPlayersNumber();

        return playersNumber
                .values()
                .stream()
                .allMatch(number -> number >= 1 && number <= 99)
                && playersNumber.values().stream().distinct().count() == playersNumber.size();

    }

}

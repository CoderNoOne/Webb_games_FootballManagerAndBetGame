package org.example.validator.hibernate_validators.impl;

import org.example.model.fm.FormationDto;
import org.example.validator.hibernate_validators.annotations.ValidFormation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class FormationValidatorImpl implements ConstraintValidator<ValidFormation, FormationDto> {

    @Override
    public boolean isValid(FormationDto formationDto, ConstraintValidatorContext constraintValidatorContext) {

        var substitutions = formationDto.getSubstitutions();
        var players = formationDto.getPlayers();

        var nonNullSubs = substitutions != null;
        var nonNullPlayers = players != null;

        if (nonNullPlayers && nonNullSubs) {
            var allPlayers = List.of(substitutions.values(), players.values());
            var differentPlayersNumber = allPlayers.stream()
                    .distinct()
                    .count();
            return differentPlayersNumber == allPlayers.size();
        }

        return false;
    }
}

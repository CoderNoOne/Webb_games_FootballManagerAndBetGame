package com.app.web_app.validators.hibernate_validators.impl;

import com.app.web_app.model.manager_game.FormationDto;
import com.app.web_app.validators.hibernate_validators.annotations.ValidFormation;

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

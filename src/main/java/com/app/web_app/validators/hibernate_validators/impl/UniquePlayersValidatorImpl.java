package com.app.web_app.validators.hibernate_validators.impl;

import com.app.web_app.model.manager_game.dto.PlayerDto;
import com.app.web_app.validators.hibernate_validators.annotations.UniquePlayers;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;
import java.util.Map;

public class UniquePlayersValidatorImpl implements ConstraintValidator<UniquePlayers, Map<String, PlayerDto>> {

    @Override
    public boolean isValid(Map<String, PlayerDto> map, ConstraintValidatorContext constraintValidatorContext) {

        if (map != null) {
            Collection<PlayerDto> players = map.values();

            return players.size() == players.stream().distinct().count();
        }

        return true;
    }
}

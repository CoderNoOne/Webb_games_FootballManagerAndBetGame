package org.example.validator.spring_validators;

import org.example.model.fm.FormationDto;
import org.example.model.fm.PlayerDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormationDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return FormationDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        var formation = (FormationDto) o;

        var substitutions = formation.getSubstitutions();
        var players = formation.getPlayers();

        var nonNullSubs = substitutions != null;
        var nonNullPlayers = players != null;

        if (nonNullPlayers && nonNullSubs) {
            List<PlayerDto> allPlayers = List.of(substitutions.values(), players.values()).stream()
                    .flatMap(Collection::stream).collect(Collectors.toList());

            var differentPlayersNumber = allPlayers.stream()
                    .distinct()
                    .count();

            if (differentPlayersNumber != allPlayers.size()) {
                errors.rejectValue("players", "Players must be unique");
            }
        }

        if (formation.getName().trim().equals("")) {
            errors.rejectValue("name", "Formation name must not be blank");
        }
    }
}


package org.example.validator.spring_validators;

import org.example.model.fm.PlayerDto;
import org.example.model.fm.SquadDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;


@Component
public class SquadDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return SquadDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        SquadDto squadDto = (SquadDto) o;

        Set<PlayerDto> teamPlayers = squadDto.getTeamDto().getPlayers();

        if (teamPlayers.containsAll(squadDto.getPlayers().values())) {
            errors.rejectValue("players", "Players from squad must belong to the team");
        }
    }
}

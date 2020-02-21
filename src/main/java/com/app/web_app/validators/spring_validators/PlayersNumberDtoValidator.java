package com.app.web_app.validators.spring_validators;

import com.app.web_app.model.manager_game.dto.PlayersNumberDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Map;

@Component
public class PlayersNumberDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return PlayersNumberDtoValidator.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {

        PlayersNumberDto playersNumberDto = (PlayersNumberDto) obj;

        Map<Integer, Integer> playersNumber = playersNumberDto.getPlayersNumber();

        if (playersNumber == null) {
            errors.rejectValue("playersNumber", "No numbers has been selected");
        } else {

            boolean isUnique = playersNumber
                    .values()
                    .stream()
                    .distinct()
                    .count() == playersNumber.size();

            if (!isUnique) {
                errors.rejectValue("playersNumber", "Numbers have to be unique");
            }

            boolean isProperRange = playersNumber
                    .values()
                    .stream()
                    .allMatch(number -> number >= 1 && number <= 99);

            if (!isProperRange) {
                errors.rejectValue("playersNumber", "Numbers have to be in the range of <1,99>");
            }
        }
    }
}

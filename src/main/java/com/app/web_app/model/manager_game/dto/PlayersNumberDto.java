package com.app.web_app.model.manager_game.dto;

import com.app.web_app.validators.hibernate_validators.annotations.ValidNumbers;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidNumbers
public class PlayersNumberDto {

    private Map<Integer, Integer> playersNumber;
}

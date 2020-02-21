package com.app.web_app.model.manager_game.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SquadDto {

    private Integer id;

    private String name;

    private TeamDto teamDto;

    private Integer formationType;

    private Map<String, PlayerDto> players;

    private Map<String, PlayerDto> substitutions;
}

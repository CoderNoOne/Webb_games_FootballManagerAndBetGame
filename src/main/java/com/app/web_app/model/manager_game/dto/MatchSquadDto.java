package com.app.web_app.model.manager_game.dto;

import com.app.web_app.model.manager_game.AggressionLevel;
import com.app.web_app.model.manager_game.Attitude;
import com.app.web_app.model.manager_game.enums.Formation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchSquadDto {

    private Integer id;

    private Integer matchId;
    private Integer teamId;

    private Formation formation;
    private Integer substitutionsNumberAvailable;

    private Map<String, PlayerDto> players;

    private Attitude attitude;

    private AggressionLevel aggressionLevel;

    public Map<String, PlayerDto> substitutions;


}

package com.app.web_app.model.manager_game.dto;

import com.app.web_app.model.manager_game.FmMatchStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MatchDto {

    private Integer id;
    private TeamDto homeTeam;
    private TeamDto awayTeam;
    private LocalDateTime dateTime;
    private String score;
    private Integer matchDay;
    private Integer leagueId;
    private FmMatchStatus status;

    private List<TeamSquadDto> teamSquads;

}

package org.example.model.fm;

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
    private String status;

    private List<TeamSquadDto> teamSquads;

}

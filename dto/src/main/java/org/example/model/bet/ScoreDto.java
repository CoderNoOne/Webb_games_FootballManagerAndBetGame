package org.example.model.bet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.bet.enums.BetLeagueDto;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ScoreDto {

    private Integer id;
    private BetLeagueDto league;
    private String username;


    private List<BetScoreDto> betScores;
}

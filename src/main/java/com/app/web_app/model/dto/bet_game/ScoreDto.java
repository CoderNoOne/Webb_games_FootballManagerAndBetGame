package com.app.web_app.model.dto.bet_game;

import com.app.web_app.model.BetLeague;
import com.app.web_app.model.bet_game.BetScore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ScoreDto {

    private Integer id;
    private BetLeague league;
    private String username;


    private List<BetScore> betScores;
}

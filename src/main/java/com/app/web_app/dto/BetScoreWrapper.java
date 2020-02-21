package com.app.web_app.dto;

import com.app.web_app.model.bet_game.BetScore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BetScoreWrapper {

    private Map<Integer, BetScore> betScores;
}

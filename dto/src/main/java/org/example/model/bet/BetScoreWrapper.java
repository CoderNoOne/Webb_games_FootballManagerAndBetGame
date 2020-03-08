package org.example.model.bet;

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

    private Map<Integer, BetScoreDto> betScores;
}

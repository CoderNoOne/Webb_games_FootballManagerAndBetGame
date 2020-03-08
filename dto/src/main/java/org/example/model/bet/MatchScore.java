package org.example.model.bet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.bet.enums.BetDuration;
import org.example.model.bet.enums.BetWinner;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MatchScore {

    private BetWinner winner;

    private BetDuration duration;

    private SubScore fullTime;

    private SubScore extraTime;

    private SubScore penalties;
}

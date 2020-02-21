package com.app.web_app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

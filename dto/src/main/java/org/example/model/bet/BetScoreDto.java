package org.example.model.bet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BetScoreDto {

    private Integer homeScore;
    private Integer awayScore;
    private Integer matchId;
    private Integer matchDay;
    private String homeTeamName;
    private String awayTeamName;
    private ZonedDateTime matchStartingTime;
}

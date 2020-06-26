package org.example.entity.bet.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Embeddable;
import java.time.ZonedDateTime;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BetScore {

    private Integer homeScore;
    private Integer awayScore;

    private Integer matchId;
    private Integer matchDay;
    private String homeTeamName;
    private String awayTeamName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime matchStartingTime;
}

package org.example.model.bet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.bet.enums.BetLeagueDto;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BetPointsDto {

    private String username;
    private BetLeagueDto league;

    private Integer pointsNumber;
    private LocalDateTime lastUpdateTime;
}

package com.app.web_app.model.dto;

import com.app.web_app.model.BetLeague;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BetPointsDto {

    private String username;
    private BetLeague league;

    private Integer pointsNumber;
    private LocalDateTime lastUpdateTime;
}

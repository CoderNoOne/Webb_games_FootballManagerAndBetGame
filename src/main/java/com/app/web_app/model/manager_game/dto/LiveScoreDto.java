package com.app.web_app.model.manager_game.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LiveScoreDto {

    private GoalDetailDto homeTeam;
    private GoalDetailDto awayTeam;
}

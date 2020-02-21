package com.app.web_app.model.manager_game.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerAttributesDto {

    private Integer id;
    private Integer playerId;

    private Integer attacking;
    private Integer defending;
    private Integer heading;

    private Integer dribbling;
    private Integer passing;
    private Integer technique;
    private Integer teamWork;

    private Integer speed;
    private Integer aggression;

    private Integer reflexes;
    private Integer oneOnOnes;
    private Integer penaltySaving;
    private Integer penaltyScoring;
}

package org.example.model.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PlayerAttributesBaseDto {

    private Integer attacking;
    private Integer defending;
    private Integer heading;
    private Integer dribbling;
    private Integer passing;
    private Integer technique;
    private Integer teamWork;
    private Integer playerId;
    private Integer speed;
    private Integer aggression;
    private Integer oneOnOnes;
    private Integer penaltySaving;
    private Integer penaltyScoring;
    private Integer reflexes;

}

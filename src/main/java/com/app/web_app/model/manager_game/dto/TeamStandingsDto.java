package com.app.web_app.model.manager_game.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamStandingsDto {

   private TeamDto team;
   private Integer points;
   private Integer wins;
   private Integer draws;
   private Integer loses;
   private Integer goalDifference;
   private Integer matchesNumber;

}

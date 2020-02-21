package com.app.web_app.model.manager_game.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GoalDetailDto {

    private Integer id;
    private Integer minute;
    private PlayerDto scorer;
    private PlayerDto assistant;
    private String team;
}

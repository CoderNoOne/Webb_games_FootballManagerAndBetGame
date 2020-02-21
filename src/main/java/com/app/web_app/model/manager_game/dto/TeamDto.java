package com.app.web_app.model.manager_game.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TeamDto {

    private Integer id;
    private String name;
    private String backgroundUrl;
    private Integer gameId;
    private String logoUrl;
    private String shirtColors;

    private String username;

    private Set<PlayerDto> players;
}

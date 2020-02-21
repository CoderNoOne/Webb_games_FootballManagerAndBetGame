package com.app.web_app.model.manager_game.dto;

import com.app.web_app.model.manager_game.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlayerDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private Integer number;
    private String imageUrl;
    private Set<Position> positions;
    private PlayerAttributesDto playerAttributes;
    private Integer teamId;
}

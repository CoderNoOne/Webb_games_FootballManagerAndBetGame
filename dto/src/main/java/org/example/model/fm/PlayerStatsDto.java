package org.example.model.fm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerStatsDto {

    private PlayerDto playerDto;
    private Integer assists;
    private Integer goals;

}

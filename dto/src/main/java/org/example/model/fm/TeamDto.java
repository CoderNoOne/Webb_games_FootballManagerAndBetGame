package org.example.model.fm;

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
    private LeagueDto leagueDto;

    private String username;
    private CountryDto countryDto;

    private Set<PlayerDto> players;
}

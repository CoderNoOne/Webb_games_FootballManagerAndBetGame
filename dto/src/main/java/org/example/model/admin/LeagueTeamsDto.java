package org.example.model.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.fm.TeamDto;

import java.util.List;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeagueTeamsDto {

    private Map<String, List<TeamDto>> leagueTeams;
}

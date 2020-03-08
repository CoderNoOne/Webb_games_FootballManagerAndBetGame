package org.example.admin;

import org.example.model.admin.TeamBaseDto;
import org.example.model.fm.TeamDto;

public interface AdminMapper {

    static TeamDto mapTeamBaseToDto(TeamBaseDto teamBaseDto) {
        return teamBaseDto != null ?
                TeamDto
                        .builder()
                        .name(teamBaseDto.getName())
                        .backgroundUrl(teamBaseDto.getBackgroundUrl())
                        .logoUrl(teamBaseDto.getLogoUrl())
                        .shirtColors(teamBaseDto.getShirtColors())
                        .build()
                :
                null;
    }
}

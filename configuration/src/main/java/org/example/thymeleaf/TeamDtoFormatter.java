package org.example.thymeleaf;

import lombok.RequiredArgsConstructor;
import org.example.admin.AdminMapper;
import org.example.model.fm.TeamDto;
import org.example.repository.admin.Impl.TeamBaseRepository;
import org.springframework.format.Formatter;

import java.util.Locale;

@RequiredArgsConstructor
public class TeamDtoFormatter implements Formatter<TeamDto> {

    private final TeamBaseRepository teamBaseRepository;

    @Override
    public TeamDto parse(String name, Locale locale) {

        return teamBaseRepository.findByName(name)
                .map(AdminMapper::mapTeamBaseToDto)
                .orElse(null);
    }

    @Override
    public String print(TeamDto teamDto, Locale locale) {
        return teamDto.toString();
    }
}

package com.app.web_app.config.thymeleaf;

import com.app.web_app.model.admin.repository.Impl.TeamBaseRepository;
import com.app.web_app.model.manager_game.dto.TeamDto;
import com.app.web_app.model.manager_game.mapper.ManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import java.util.Locale;

public class TeamDtoFormatter implements Formatter<TeamDto> {

    @Autowired
    private TeamBaseRepository teamBaseRepository;

    @Autowired
    private ManagerMapper managerMapper;

    @Override
    public TeamDto parse(String name, Locale locale) {

        return teamBaseRepository.findByName(name)
                .map(managerMapper::mapTeamBaseToDto)
                .orElse(null);
    }

    @Override
    public String print(TeamDto teamDto, Locale locale) {
        return teamDto.toString();
    }
}

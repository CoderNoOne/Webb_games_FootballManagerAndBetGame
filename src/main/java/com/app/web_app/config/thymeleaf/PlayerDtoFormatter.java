package com.app.web_app.config.thymeleaf;

import com.app.web_app.model.manager_game.dto.PlayerDto;
import com.app.web_app.model.manager_game.mapper.ManagerMapper;
import com.app.web_app.model.manager_game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class PlayerDtoFormatter implements Formatter<PlayerDto> {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ManagerMapper managerMapper;

    @Override
    public PlayerDto parse(String stringId, Locale locale) throws ParseException {

        Integer id = Integer.parseInt(stringId);

        return playerRepository.findById(id).map(managerMapper::mapPlayerToDto).orElse(null);
    }

    @Override
    public String print(PlayerDto playerDto, Locale locale) {
        return playerDto.toString();
    }

}

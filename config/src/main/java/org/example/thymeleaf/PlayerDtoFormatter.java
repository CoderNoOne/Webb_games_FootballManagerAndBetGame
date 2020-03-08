package org.example.thymeleaf;

import lombok.RequiredArgsConstructor;
import org.example.fm.ManagerMapper;
import org.example.fm.PlayerRepository;
import org.example.model.fm.PlayerDto;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class PlayerDtoFormatter implements Formatter<PlayerDto> {

    private final PlayerRepository playerRepository;

    @Override
    public PlayerDto parse(String stringId, Locale locale) throws ParseException {

        Integer id = Integer.parseInt(stringId);

        return playerRepository.findById(id).map(ManagerMapper::mapPlayerToDto).orElse(null);
    }

    @Override
    public String print(PlayerDto playerDto, Locale locale) {
        return playerDto.toString();
    }

}

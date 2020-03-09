package org.example.model.fm.enums;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.core.exceptions.AppException;

import java.text.MessageFormat;
import java.util.Arrays;

@AllArgsConstructor
@Slf4j
public enum LeagueTypeDto {
    CUSTOM("Custom"),
    SERIE_A("Serie A"),
    BUNDESLIGA("Bundesliga"),
    PREMIER_LEAGUE("Premier League");

    private String description;

    public static LeagueTypeDto fromString(String leagueType) {
        try {
            return LeagueTypeDto.valueOf(leagueType);
        } catch (Exception e) {
            log.info(e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
            throw new AppException(MessageFormat.format("No enum constant: {}", leagueType));
        }
    }
}

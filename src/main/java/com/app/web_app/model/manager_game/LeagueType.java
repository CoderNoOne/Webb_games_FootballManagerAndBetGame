package com.app.web_app.model.manager_game;

import lombok.Getter;

@Getter
public enum LeagueType {
    CUSTOM("Custom"),
    SERIE_A("Serie A"),
    BUNDESLIGA("Bundesliga"),
    PREMIER_LEAGUE("Premier League");

    private String description;

    LeagueType(String description) {
        this.description = description;
    }

    public static LeagueType fromString(String leagueType) {
        try {
            return LeagueType.valueOf(leagueType);
        } catch (Exception e) {
            //
        }

        return null;
    }

    public String getDescription() {
        return description;
    }
}

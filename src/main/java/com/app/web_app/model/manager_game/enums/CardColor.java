package com.app.web_app.model.manager_game.enums;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public enum CardColor {
    RED,
    YELLOW;

    public CardColor fromString(String value) {
        try {
            return CardColor.valueOf(value);
        } catch (Exception e) {
            log.info(e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
        }
        return null;
    }
}

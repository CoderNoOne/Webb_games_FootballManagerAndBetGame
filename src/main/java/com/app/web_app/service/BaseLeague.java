package com.app.web_app.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BaseLeague {

    SERIE_A("SERIE A"),
    PREMIERA_DIVISION("PREMIERA DIVISION"),
    PREMIER_LEAGUE("PREMIER LEAGUE"),
    BUNDESLIGA("BUNDESLIGA");

    private String desc;
}

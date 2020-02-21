package com.app.web_app.model.manager_game.enums;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public enum InjuryType {

    KNEE_INJURY,
    OVERUSE_INJURY,
    ANKLE_SPRAIN,
    HIP_POINTER,
    CONCUSSIONS,
    BURNER,
    SPINE_INJURY,
    ACHILLES_INJURY;

    public InjuryType fromString(String value) {

        try {
            return InjuryType.valueOf(value);
        } catch (Exception e) {
            log.info(value + " is not an injury type!");
            log.error(Arrays.toString(e.getStackTrace()));
        }
        return null;
    }
}

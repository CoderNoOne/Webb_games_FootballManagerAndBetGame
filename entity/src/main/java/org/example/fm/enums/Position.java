package org.example.fm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@AllArgsConstructor
@Getter
@Slf4j
public enum Position {

    GK("Goal keeper"),
    LCB("Left central back"),
    RCB("Right central  back"),
    CB("Central back"),
    RB("Right back"),
    LB("Left back"),
    CM("Central midfielder"),
    LCM("Left central midfielder"),
    RCM("Right central midfielder"),
    RM("Right midfielder"),
    LM("Left midfielder"),
    DM("Defence midfielder"),
    LF("Left forward"),
    CF("Central forward"),
    LCF("Left central forward"),
    RCF("Right central forward"),
    RF("Right forward"),

    FIRST("sub one"),
    SECOND("sub two"),
    THIRD("sub three"),
    FOURTH("sub four"),
    FIFTH("sub five"),
    SIXTH("sub six"),
    SEVENTH("sub seven");


    private String desc;

    public static Position fromString(String value) {

        try {
            return Position.valueOf(value.toUpperCase());
        } catch (Exception e) {
            log.info(String.format("%s is not a Position type!", value));
            log.error(Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

}

package org.example.fm.enums;

import java.awt.desktop.AppEvent;

public enum FmMatchStatus {
    SCHEDULED, LIVE, FINISHED, HALF_BREAK;

    public static FmMatchStatus fromString(String status) {

        return FmMatchStatus.valueOf(status.toUpperCase());
    }
}

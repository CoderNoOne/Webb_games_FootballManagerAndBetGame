package org.example.entity.fm.enums;

public enum FmMatchStatus {
    SCHEDULED, LIVE, FINISHED, HALF_BREAK;

    public static FmMatchStatus fromString(String status) {

        return FmMatchStatus.valueOf(status.toUpperCase());
    }
}

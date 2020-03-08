package org.example.model.fm.enums;

import lombok.Getter;

import java.util.List;

@Getter
public enum PositionSkillsImportance {

    GK(List.of("defending", "oneOnOnes", "penaltySaving", "reflexes")),
    LCB(List.of("defending", "heading", "speed")),
    CB(List.of("defending", "heading", "speed")),
    RCB(List.of("defending", "heading", "speed")),
    RB(List.of("defending", "speed", "aggression", "dribbling")),
    LB(List.of("defending", "speed", "aggression", "dribbling")),
    LM(List.of("defending", "attacking", "speed", "aggression", "dribbling")),
    RM(List.of("defending", "attacking", "speed", "aggression", "dribbling")),
    RCM(List.of("defending", "attacking", "passing", "speed", "technique", "teamWork")),
    CM(List.of("defending", "attacking", "passing", "speed", "technique", "teamWork")),
    LCM(List.of("defending", "attacking", "passing", "speed", "technique", "teamWork")),
    LCF(List.of("attacking", "speed", "dribbling", "heading")),
    RCF(List.of("attacking", "speed", "dribbling", "heading"));

    private List<String> mostImportantSkillsForPosition;

    PositionSkillsImportance(List<String> mostImportantSkillsForPosition) {
        this.mostImportantSkillsForPosition = mostImportantSkillsForPosition;
    }
}

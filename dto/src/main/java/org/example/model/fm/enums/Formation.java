package org.example.model.fm.enums;


import org.example.exceptions.AppException;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.*;

public enum Formation {
    FOUR_FOUR_TWO(
            442,
            "ff2",
            Arrays.asList("GK", "LCB", "RCB", "LB", "RB", "RM", "LM", "LCM", "RCM", "LCF", "RCF"),
            ofEntries(entry("input_1", "LCF"),
                    entry("input_2", "RCF"),
                    entry("input_3", "LM"),
                    entry("input_4", "LCM"),
                    entry("input_5", "RCM"),
                    entry("input_6", "RM"),
                    entry("input_7", "RB"),
                    entry("input_8", "LB"),
                    entry("input_9", "LCB"),
                    entry("input_10", "RCB"),
                    entry("input_11", "GK"))
    ),

    THREE_FIVE_TWO(
            352,
            "_352",
            Arrays.asList("GK", "LCB", "CB", "RCB", "RM", "LM", "LCM", "RCM", "CM", "LCF", "RCF"),
            ofEntries(entry("input_1", "LCF"),
                    entry("input_2", "RCF"),
                    entry("input_3", "LM"),
                    entry("input_4", "LCM"),
                    entry("input_8", "CM"),
                    entry("input_5", "RCM"),
                    entry("input_6", "RM"),
                    entry("input_9", "LCB"),
                    entry("input_7", "CB"),
                    entry("input_10", "RCB"),
                    entry("input_11", "GK")
            )
    );

    private Integer number;
    private List<String> positions;
    private String cssClassName;
    private Map<String, String> positionForInputId;

    public static Optional<Formation> fromCssClassName(String cssClassName) {
        return Arrays.stream(Formation.values()).filter(enumVal -> enumVal.getCssClassName().equals(cssClassName)).findFirst();
    }


    public static Formation fromFormationNumber(Integer formationNumber) {

        if (formationNumber == null) {
            throw new AppException("Formation number is null");
        }
        return Arrays.stream(Formation.values()).filter(enumVal -> enumVal.getNumber().equals(formationNumber))
                .findFirst()
                .orElseThrow(() -> new AppException(MessageFormat.format("Formation identified with number: {0} doesn't exist", formationNumber)));
    }

    Formation(Integer number, String cssClassName, List<String> positions, Map<String, String> positionForInputId) {
        this.number = number;
        this.positions = positions;
        this.cssClassName = cssClassName;
        this.positionForInputId = positionForInputId;
    }

    public Integer getNumber() {
        return number;
    }

    public List<String> getPositions() {
        return positions;
    }

    public String getCssClassName() {
        return cssClassName;
    }

    public Map<String, String> getPositionForInputId() {
        return positionForInputId.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Entry::getKey,
                        Entry::getValue,
                        (oldVal, newVal) -> oldVal,
                        LinkedHashMap::new
                ));
    }
}


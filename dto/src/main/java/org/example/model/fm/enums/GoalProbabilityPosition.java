package org.example.model.fm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.core.exceptions.AppException;

import java.math.BigDecimal;
import java.util.Arrays;

@Getter
@AllArgsConstructor
@Slf4j
public enum GoalProbabilityPosition {
    CF(new BigDecimal("0.9")), LCF(new BigDecimal("0.9")), RCF(new BigDecimal("0.9")),
    LM(new BigDecimal("0.6")), RM(new BigDecimal("0.6")),
    LCM(new BigDecimal("0.5")), CM(new BigDecimal("0.5")), RCM(new BigDecimal("0.5")),
    LB(new BigDecimal("0.3")), RB(new BigDecimal("0.3")),
    LCB(new BigDecimal("0.2")), RCB(new BigDecimal("0.2")), CB(new BigDecimal("0.2")),
    GK(new BigDecimal("0.0"));

    private BigDecimal goalProbability;

    public static BigDecimal getGoalProbabilityForPosition(String position) {

        if (position == null) {
            throw new AppException("Position is null");
        }

        try {
            GoalProbabilityPosition goalProbabilityPosition = GoalProbabilityPosition.valueOf(position.toUpperCase());
            return goalProbabilityPosition.getGoalProbability();
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
            throw new AppException(e.getMessage());
        }

    }
}

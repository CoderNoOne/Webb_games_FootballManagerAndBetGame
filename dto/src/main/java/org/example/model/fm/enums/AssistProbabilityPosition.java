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
public enum AssistProbabilityPosition {
    CF(new BigDecimal(String.valueOf(0.9))), LCF(new BigDecimal(String.valueOf(0.9))), RCF(new BigDecimal(String.valueOf(0.9))),
    LCM(new BigDecimal(String.valueOf(0.8))), RCM(new BigDecimal(String.valueOf(0.8))), CM(new BigDecimal(String.valueOf(0.8))),
    LM(new BigDecimal(String.valueOf(0.5))), RM(new BigDecimal(String.valueOf(0.5))),
    LB(new BigDecimal(String.valueOf(0.3))), RB(new BigDecimal(String.valueOf(0.3))),
    LCB(new BigDecimal(String.valueOf(0.1))), RCB(new BigDecimal(String.valueOf(0.1))), CB(new BigDecimal(String.valueOf(0.1))),
    GK(new BigDecimal("0.0"));


    private BigDecimal assistProbability;

    public static BigDecimal getAssistProbabilityForPosition(String position) {

        if (position == null) {
            throw new AppException("Position is null");
        }

        try {
            AssistProbabilityPosition assistProbabilityPosition = AssistProbabilityPosition.valueOf(position.toUpperCase());
            return assistProbabilityPosition.getAssistProbability();
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
            throw new AppException(e.getMessage());
        }

    }

}

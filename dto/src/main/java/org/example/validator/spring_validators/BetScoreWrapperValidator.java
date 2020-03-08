package org.example.validator.spring_validators;

import org.example.model.bet.BetScoreWrapper;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.MessageFormat;
import java.util.function.Predicate;

@Component
public class BetScoreWrapperValidator implements Validator {

    private static final Integer MIN_SCORE = 0;
    private static final Integer MAX_SCORE = 20;

    private final Predicate<Integer> validateScore = score -> score != null &&
            score >= MIN_SCORE && score <= MAX_SCORE;

    @Override
    public boolean supports(Class<?> aClass) {
        return BetScoreWrapper.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        BetScoreWrapper betScoreWrapper = (BetScoreWrapper) o;

        boolean isValid = betScoreWrapper.getBetScores().values()
                .stream()
                .allMatch(betScore -> validateScore.test(betScore.getHomeScore())
                        && validateScore.test(betScore.getAwayScore()));

        if (!isValid) {
            errors.rejectValue(
                    "betScores",
                    MessageFormat.format("Score should be in the range {0}-{1}", MIN_SCORE, MAX_SCORE));
        }
    }
}


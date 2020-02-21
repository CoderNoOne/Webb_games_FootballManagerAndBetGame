//package com.app.demo.validators;
//
//import com.app.demo.model.dto.bet_game.ScoreDto;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//import org.springframework.validation.Validator;
//
//@Component
//public class ScoreEntityDtoValidator implements Validator {
//
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return aClass.equals(ScoreDto.class);
//    }
//
//    @Override
//    public void validate(Object obj, Errors errors) {
//
//        ScoreDto scoreDto = (ScoreDto) obj;
//
//        boolean isValid = scoreDto.getBetScores()
//                .values()
//                .stream()
//                .allMatch(subScore -> subScore.getAwayScore() != null && subScore.getHomeScore() != null);
//
//        if (!isValid) {
//            errors.rejectValue("betScores", "Scores can not be null");
//        }
//
//    }
//}

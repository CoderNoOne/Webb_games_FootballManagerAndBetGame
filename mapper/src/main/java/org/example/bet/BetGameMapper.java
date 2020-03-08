package org.example.bet;

import org.example.bet.entity.BetPoints;
import org.example.bet.entity.BetScore;
import org.example.bet.entity.ScoreEntity;
import org.example.bet.enums.BetLeague;
import org.example.core.entity.User;
import org.example.model.bet.BetPointsDto;
import org.example.model.bet.BetScoreDto;
import org.example.model.bet.ScoreDto;
import org.example.model.bet.enums.BetLeagueDto;

import java.util.ArrayList;
import java.util.stream.Collectors;

public interface BetGameMapper {


    static BetPointsDto mapBetPointsToDto(BetPoints betPoints) {

        return betPoints != null ?

                BetPointsDto.builder()
                        .lastUpdateTime(betPoints.getLastUpdateTime())
                        .league(BetGameMapper.mapBetLeagueToBetLeagueDto(betPoints.getLeague()))
                        .username(betPoints.getUser().getUsername())
                        .pointsNumber(betPoints.getPointsNumber())
                        .build()
                : null;

    }

    static BetLeagueDto mapBetLeagueToBetLeagueDto(BetLeague betLeague) {

        return betLeague != null ?
                BetLeagueDto.fromString(betLeague.name())
                : null;
    }

    static BetLeague mapBetLeagueDtoToBetLeague(BetLeagueDto betLeagueDto) {
        return betLeagueDto != null ?
                BetLeague.fromString(betLeagueDto.name())
                : null;
    }

    static BetScoreDto mapBetScoreToBetScoreDto(BetScore betScore) {

        return betScore != null ?
                BetScoreDto.builder()
                        .awayScore(betScore.getAwayScore())
                        .homeScore(betScore.getHomeScore())
                        .matchDay(betScore.getMatchDay())
                        .matchId(betScore.getMatchId())
                        .matchStartingTime(betScore.getMatchStartingTime())
                        .awayTeamName(betScore.getAwayTeamName())
                        .homeTeamName(betScore.getHomeTeamName())
                        .build()
                : null;
    }

    static BetScore mapBetScoreDtoToBetScore(BetScoreDto betScoreDto) {

        return betScoreDto != null ?

                BetScore.builder()
                        .awayScore(betScoreDto.getAwayScore())
                        .homeScore(betScoreDto.getHomeScore())
                        .awayTeamName(betScoreDto.getAwayTeamName())
                        .homeTeamName(betScoreDto.getHomeTeamName())
                        .matchDay(betScoreDto.getMatchDay())
                        .matchId(betScoreDto.getMatchId())
                        .matchStartingTime(betScoreDto.getMatchStartingTime())
                        .build()
                : null;
    }

    static ScoreDto mapScoreEntityToDto(ScoreEntity scoreEntity) {

        return scoreEntity != null ?
                ScoreDto.builder()
                        .id(scoreEntity.getId())
                        .league(BetGameMapper.mapBetLeagueToBetLeagueDto(scoreEntity.getLeague()))
                        .username(scoreEntity.getUser().getUsername())
                        .betScores(scoreEntity.getBetScores() != null ?
                                scoreEntity.getBetScores().stream().map(BetGameMapper::mapBetScoreToBetScoreDto).collect(Collectors.toList()) :
                                new ArrayList<>())
                        .build()

                : null;
    }


    static ScoreEntity mapScoreDtoToScoreEntity(ScoreDto scoreDto) {

        return scoreDto != null ?

                ScoreEntity.builder()
                        .id(scoreDto.getId())
                        .user(User.builder()
                                .username(scoreDto.getUsername())
                                .build())
                        .league(mapBetLeagueDtoToBetLeague(scoreDto.getLeague()))
                        .betScores(scoreDto.getBetScores() != null ?
                                scoreDto.getBetScores().stream().map(BetGameMapper::mapBetScoreDtoToBetScore).collect(Collectors.toList()) : new ArrayList<>())
                        .build()
                : null;
    }

//    public ScoreEntity mapScoreDtoToEntity(ScoreDto scoreDto) {
//
//        return scoreDto != null ?
//
//                scoreDto.getId() != null ?
//                        scoreEntityRepository.findById((scoreDto.getId())).orElse(
//
//                                ScoreEntity.builder()
//                                        .id(scoreDto.getId())
//                                        .league(scoreDto.getLeague())
//                                        .user(userRepository.findByUsername(scoreDto.getUsername()).orElse(null))
//                                        .betScores(scoreDto.getBetScores())
//                                        .build())
//                        :
//
//                        ScoreEntity.builder()
//                                .league(scoreDto.getLeague())
//                                .user(userRepository.findByUsername(scoreDto.getUsername()).orElse(null))
//                                .betScores(scoreDto.getBetScores())
//                                .build()
//
//                : null;
//    }
}

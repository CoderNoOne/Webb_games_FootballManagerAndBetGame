package org.example.fm;

import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.example.core.exceptions.AppException;
import org.example.fm.entity.*;
import org.example.model.fm.enums.Formation;
import org.example.model.fm.enums.PositionSkillsImportance;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SquadStrengthCalculatorService {

    private final MatchSquadRepository matchSquadRepository;
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    private static List<String> mostImportantPositionsFor335Formation = List.of("LM", "RM");
    private static List<String> mostImportantPositionsFor442Formation = List.of("LCM", "RCM");
    private static BigDecimal importanceFactor = new BigDecimal("2.5");


    private BigDecimal mostImportantPositionStrength(Formation formation, Map<String, Player> playersForPosition) {

        return switch (formation) {
            case THREE_FIVE_TWO -> calculatePositionStrengthForMostImportantPositions(mostImportantPositionsFor335Formation, playersForPosition);
            case FOUR_FOUR_TWO -> calculatePositionStrengthForMostImportantPositions(mostImportantPositionsFor442Formation, playersForPosition);
        };

    }


    private BigDecimal calculatePositionsStrengthForOtherPositions(List<String> otherPositions, Map<String, Player> playersForPositions) {

        return otherPositions
                .stream()
                .map(pos -> {
                    Player player = playersForPositions.get(pos);
                    PlayerAttributes playerAttributes = player.getPlayerAttributes();
                    List<String> mostImportantSkillsForPosition = PositionSkillsImportance.valueOf(pos).getMostImportantSkillsForPosition();
                    return mostImportantSkillsForPosition
                            .stream()
                            .map(skill -> getPropertyValue(playerAttributes, skill))
                            .mapToInt(i -> i)
                            .sum();
                })
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    private BigDecimal calculatePositionStrengthForMostImportantPositions(List<String> mostImportantPositionsForFormation, Map<String, Player> playersForPositions) {

        BigDecimal baseValue = mostImportantPositionsForFormation
                .stream()
                .map(position -> {
                    List<String> mostImportantSkillsForPosition = PositionSkillsImportance.valueOf(position).getMostImportantSkillsForPosition();
                    PlayerAttributes playerAttributes = playersForPositions.get(position).getPlayerAttributes();

                    return mostImportantSkillsForPosition.stream()
                            .collect(Collectors.toMap(
                                    skill -> skill,
                                    skill -> getPropertyValue(playerAttributes, skill)
                            ));

                })
                .findFirst().get().values().stream().map(BigDecimal::valueOf).reduce(BigDecimal.ZERO, BigDecimal::add);


        return baseValue.multiply(importanceFactor);

    }

    public BigDecimal calculateSquadStrength(MatchSquad matchSquad) {

        Formation formation = Formation.fromFormationNumber(matchSquad.getFormationType());

        Map<String, Player> playersForPosition = matchSquad.getPlayers();

        return switch (formation) {
            case THREE_FIVE_TWO -> {

                BigDecimal mostImportantPositionStrength = mostImportantPositionStrength(Formation.THREE_FIVE_TWO, playersForPosition);

                List<String> otherPositions = Formation.THREE_FIVE_TWO.getPositions().stream().filter(pos -> !mostImportantPositionsFor335Formation.contains(pos))
                        .collect(Collectors.toList());

                BigDecimal lessImportantPositionsStrength = calculatePositionsStrengthForOtherPositions(otherPositions, playersForPosition);

                yield mostImportantPositionStrength.add(lessImportantPositionsStrength);
            }


            case FOUR_FOUR_TWO -> {

                BigDecimal mostImportantPositionStrength = mostImportantPositionStrength(Formation.FOUR_FOUR_TWO, playersForPosition);

                List<String> otherPositions = Formation.FOUR_FOUR_TWO.getPositions().stream().filter(pos -> !mostImportantPositionsFor442Formation.contains(pos))
                        .collect(Collectors.toList());

                BigDecimal lessImportantPositionsStrength = calculatePositionsStrengthForOtherPositions(otherPositions, playersForPosition);

                yield mostImportantPositionStrength.add(lessImportantPositionsStrength);

            }
        };

    }


    public Map<Team, BigDecimal> calculateProbabilityOfScoringGoal(Match match) {

        List<MatchSquad> matchSquads = matchSquadRepository.findByMatchId(match.getId());

        Map<Integer, BigDecimal> squadStrengthPerTeam = matchSquads.stream()
                .collect(Collectors.toMap(
                        matchSquad -> matchSquad.getTeam().getId(),
                        this::calculateSquadStrength
                ));


        return squadStrengthPerTeam
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> teamRepository.findById(e.getKey()).get(),
                        e -> e.getValue().multiply(BigDecimal.valueOf(getRandomFactor()))
                ));


    }

    public Map<Team, BigDecimal> whoScoresWithWhatProbability(Match match) {

        Map<Team, BigDecimal> baseProbabilities = calculateProbabilityOfScoringGoal(match);

        return baseProbabilities.entrySet()
                .stream()
                .map(e -> Map.entry(e.getKey(), e.getValue().multiply(BigDecimal.valueOf(getRandomFactor()))))
                .sorted(Map.Entry.comparingByValue())
                .peek(e -> System.out.println(e.getKey().getName() + " - " + e.getValue()))
                .limit(2)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldVal, newVal) -> oldVal,
                        LinkedHashMap::new
                ));

    }

    public Map<Team, BigDecimal> goalProbability(Integer matchId) {

        Match match = matchRepository.findById(matchId).orElseThrow();

        Map<Team, BigDecimal> weightedProbability = whoScoresWithWhatProbability(match);

        BigDecimal minValue = weightedProbability.values()
                .stream()
                .min(BigDecimal::compareTo)
                .get();

        return weightedProbability.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue((e1, e2) -> -e1.compareTo(e2)))
                .limit(1)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> (e.getValue().subtract(minValue)).divide(e.getValue(), RoundingMode.HALF_UP)
                ));
    }

    public boolean isGoal(Map<Team, BigDecimal> weightedProbability) {

        BigDecimal bigDecimal = weightedProbability.values().stream().findFirst().get();
        double randomNumber = new Random().nextInt(101) / 100.0;
        BigDecimal roundedValue = bigDecimal.round(new MathContext(2));
        return new BigDecimal(randomNumber).compareTo(roundedValue) <= 0;
    }

    private Double getRandomFactor() {
        return (new Random().nextInt(201) + 900) / 1000.0;
    }

    private Integer getPropertyValue(PlayerAttributes playerAttributes, String propertyName) {
        try {
            return Integer.valueOf(BeanUtils.getProperty(playerAttributes, propertyName));
        } catch (Exception e) {
            throw new AppException(e.getMessage());
        }
    }

}

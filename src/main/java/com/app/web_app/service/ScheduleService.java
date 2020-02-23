package com.app.web_app.service;

import com.app.web_app.model.BetLeague;
import com.app.web_app.model.BetMatch;
import com.app.web_app.model.MatchScore;
import com.app.web_app.model.bet_game.BetPoints;
import com.app.web_app.model.bet_game.BetScore;
import com.app.web_app.model.manager_game.*;
import com.app.web_app.model.manager_game.dto.TeamSquadDto;
import com.app.web_app.model.manager_game.enums.Formation;
import com.app.web_app.model.manager_game.enums.Position;
import com.app.web_app.model.manager_game.repository.MatchSquadRepository;
import com.app.web_app.model.manager_game.repository.MatchStatisticRepository;
import com.app.web_app.model.manager_game.repository.TeamSquadRepository;
import com.app.web_app.model.manager_game.service.SquadStrengthCalculatorService;
import com.app.web_app.model.manager_game.service.TeamSquadService;
import com.app.web_app.repository.GoalDetailRepository;
import com.app.web_app.repository.MatchRepository;
import com.app.web_app.repository.bet_game.BetPointsRepository;
import com.app.web_app.repository.bet_game.ScoreEntityRepository;
import com.app.web_app.service.bet_game.BetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ScheduleService {

    private final TaskScheduler executor;
    private final MatchRepository matchRepository;
    private final MatchStatisticRepository matchStatisticRepository;
    private final SquadStrengthCalculatorService squadStrengthCalculatorService;
    private final MatchSquadRepository matchSquadRepository;
    private final BetService betService;
    private final TeamSquadService teamSquadService;
    private final ScoreEntityRepository scoreEntityRepository;
    private final BetPointsRepository betPointsRepository;
    private final TeamSquadRepository teamSquadRepository;
    private final GoalDetailRepository goalDetailRepository;

    private void getTriggersForLiveMatches() {

        getTriggersForLiveMatchesInItaly();
        getTriggersForLiveMatchesInEngland();
        getTriggersForLiveMatchesInSpain();
    }

    private void getTriggersForLiveMatchesInItaly() {
        List<CronTrigger> cronTriggers = getCronTriggersForLiveMatches(betService.getLiveMatchesForSerieA());

        cronTriggers.forEach(trigger -> executor.schedule(() -> {
            log.info("Update live matches for Serie A invoked");
            betService.updateLiveMatchesForSerieA();
        }, trigger));
    }

    private void getTriggersForLiveMatchesInSpain() {
        List<CronTrigger> cronTriggers = getCronTriggersForLiveMatches(betService.getLiveMatchesForSpain());

        cronTriggers.forEach(trigger -> executor.schedule(() -> {
            log.info("Update live matches for La Liga invoked");
            betService.updateLiveMatchesForSpain();
        }, trigger));
    }

    private void getTriggersForLiveMatchesInEngland() {
        List<CronTrigger> cronTriggers = getCronTriggersForLiveMatches(betService.getLiveMatchesForPremierLeague());

        cronTriggers.forEach(trigger -> executor.schedule(() -> {
            log.info("Update live matches for Premier League invoked");
            final List<BetMatch> liveMatches = betService.updateLiveMatchesForPremierLeague();
            System.out.println(liveMatches);
        }, trigger));
    }

    private void updateScheduledMatchesForCurrentMatchdayInSpain() {
        log.info("Update scheduled matches for current matchday in La liga invoked");
        final List<BetMatch> betMatches = betService.updateScheduledMatchesForSpain();

        List<CronTrigger> cronTriggers = getCronTriggersForScheduledMatches(betMatches);

        cronTriggers.forEach(trigger -> executor.schedule(() -> {
            log.info("Update live matches for La Liga invoked");
            betService.updateLiveMatchesForSpain();
        }, trigger));

        List<CronTrigger> cronTriggers1 = getCronTriggersForUpdateScheduledMatches(betMatches);

        cronTriggers1.forEach(trigger -> executor.schedule(() -> {
            log.info("Update scheduled matches for La Liga invoked");
            betService.updateScheduledMatchesForSpain();
        }, trigger));

    }

    private void updateScheduledMatchesForCurrentMatchdayInItaly() {
        log.info("Get scheduled matches for current matchday in Serie A invoked");

        final List<BetMatch> betMatches = betService.updateScheduledMatchesForItaly();
        System.out.println(betMatches);

        List<CronTrigger> cronTriggers = getCronTriggersForScheduledMatches(betMatches);

        cronTriggers.forEach(trigger -> executor.schedule(() -> {
            log.info("Update live matches for Serie A invoked");
            betService.updateLiveMatchesForSerieA();
        }, trigger));

        List<CronTrigger> cronTriggers1 = getCronTriggersForUpdateScheduledMatches(betMatches);

        cronTriggers1.forEach(trigger -> executor.schedule(() -> {
            log.info("Update scheduled matches for Serie A invoked");
            List<BetMatch> betMatches1 = betService.updateScheduledMatchesForItaly();
        }, trigger));
    }

    private void updateScheduledMatchesForCurrentMatchdayInEngland() {
        log.info("Get scheduled matches for current matchday in Premier League invoked");
        final List<BetMatch> betMatches = betService.updateScheduledMatchesForPremierLeague();

        List<CronTrigger> cronTriggers = getCronTriggersForScheduledMatches(betMatches);

        cronTriggers.forEach(trigger -> executor.schedule(() -> {
            log.info("Update live matches for Premier League invoked");
            betService.updateLiveMatchesForPremierLeague();
        }, trigger));

        List<CronTrigger> cronTriggers1 = getCronTriggersForUpdateScheduledMatches(betMatches);

        cronTriggers1.forEach(trigger -> executor.schedule(() -> {
            log.info("Update scheduled matches for Premier League invoked");
            List<BetMatch> betMatches1 = betService.updateScheduledMatchesForPremierLeague();
            System.out.println(betMatches1);
        }, trigger));
    }


    private void updateScheduledMatchesForCurrentMatchday() {

        executor.schedule(this::updateScheduledMatchesForCurrentMatchdayInSpain, new CronTrigger("0 0 0 ? * WED,SUN"));

        executor.schedule(this::updateScheduledMatchesForCurrentMatchdayInItaly, new CronTrigger("0 0 1 ? * WED,SUN"));

        executor.schedule(this::updateScheduledMatchesForCurrentMatchdayInEngland, new CronTrigger("0 0 2 ? * WED,SUN"));
    }


    private Map<Integer, BetMatch> groupMatchesById(List<BetMatch> matches) {

        return matches.stream()
                .collect(Collectors.toMap(
                        BetMatch::getId,
                        Function.identity()
                ));
    }

    private void updateUserResultsForSpecifiedLeague(BetLeague league) {

        List<BetMatch> matches = switch (league) {
            case ITALY -> betService.getLiveMatchesForSerieA();
            case SPAIN -> betService.getFinishedMatchesForLaLiga();
            case PREMIER_LEAGUE -> betService.getLiveMatchesForPremierLeague();
        };

        Map<String, Map<Integer, Map<String, Integer>>> betScoresByUserForItaly = scoreEntityRepository
                .findAllByLeague(league)
                .stream()
                .collect(Collectors.groupingBy(
                        scoreEntity -> scoreEntity.getUser().getUsername(),
                        Collectors.flatMapping(scoreEntity -> scoreEntity
                                        .getBetScores()
                                        .stream(),
                                Collectors.toMap(
                                        BetScore::getMatchId,
                                        betScore -> Map.of(
                                                "homeScore", betScore.getHomeScore(),
                                                "awayScore", betScore.getAwayScore()
                                        )
                                )
                        ))
                );

        Map<String, Integer> pointsPerUserForSerieA = betScoresByUserForItaly.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> countPoints(e.getValue(), groupMatchesById(matches))
                ));


        List<BetPoints> allByItaly = betPointsRepository.findAllByLeague(league);

        Map<String, BetPoints> betPointByKey = allByItaly.stream()
                .collect(
                        Collectors.groupingBy(
                                betPoints -> betPoints.getUser().getUsername(),
                                Collectors.reducing(null, (bet1, bet2) -> bet1)
                        )
                );

        LocalDateTime now = LocalDateTime.now();

        pointsPerUserForSerieA
                .forEach((username, points) -> {
                    BetPoints betPoints = betPointByKey.get(username);

                    betPoints.setPointsNumber(pointsPerUserForSerieA.get(username));
                    betPoints.setLastUpdateTime(now);

                });

        betPointsRepository.saveAll(betPointByKey.values());

    }

    private void updateAllUserResults() {

        executor.schedule(() -> {
            log.info("Update user results invoked");

            for (BetLeague league : BetLeague.values()) {
                updateUserResultsForSpecifiedLeague(league);
            }

        }, new CronTrigger("0 0 0 * * ?"));
    }

    private Integer countPoints(Map<Integer, Map<String, Integer>> userBets, Map<Integer, BetMatch> allFinishedMatches) {


        AtomicInteger counter = new AtomicInteger(0);
        userBets.forEach((matchId, mapScores) -> {
            BetMatch betMatch = allFinishedMatches.get(matchId);
            counter.addAndGet(countPointsForMatch(betMatch.getScore(), mapScores));
        });

        return counter.get();
    }

    private int countPointsForMatch(MatchScore score, Map<String, Integer> mapScores) {

        Integer homeTeamGoals = score.getFullTime().getHomeTeam();
        Integer awayTeamGoals = score.getFullTime().getAwayTeam();

        Integer betHomeGoals = mapScores.get("homeScore");
        Integer betAwayGoals = mapScores.get("awayScore");

        int points;
        if (homeTeamGoals.equals(betHomeGoals) && awayTeamGoals.equals(betAwayGoals)) {
            points = 5;
        } else if (isSameResult(homeTeamGoals, awayTeamGoals, betHomeGoals, betAwayGoals)) {
            points = 3;
        } else {
            points = -2;
        }

        return points;

    }

    private boolean isSameResult(Integer homeTeamGoals, Integer awayTeamGoals, Integer betHomeGoals, Integer betAwayGoals) {

        return Math.signum(homeTeamGoals - awayTeamGoals) == Math.signum(betHomeGoals - betAwayGoals);

    }

    //generate 5 minutes before matches triggers for simulating matches


    // TODO: 22.01.2020 triggery do symylacji live matchow jesli padnie serwer w trackie meczow


    public void simulateFmMatches(List<Integer> matchIds) {

        List<Match> matches = matchRepository.findAllById(matchIds);

        Map<Match, CronTrigger> cronTriggersToCheckIfTeamsHaveTheirStartingSquadsSet = getCronTriggersToCheckIfTeamsHaveTheirStartingSquadsSet(matches);

        cronTriggersToCheckIfTeamsHaveTheirStartingSquadsSet.forEach((match, trigger) -> executor.schedule(() ->
                {
                    boolean isReadyToSimulate = checkIfTeamsHaveSquadSet(match);
                    if (isReadyToSimulate) {

                        saveMatchStatisticBeforeMatch(match);

                        List<CronTrigger> cronTriggersForMatchUpdate = generateFootballManagerMatchesTriggerForScoreUpdate(match);

                        cronTriggersForMatchUpdate.forEach(trigg -> executor.schedule(() -> simulateSingleMatch(match), trigg));
                    }

                }, trigger)
        );

    }

    private boolean checkIfTeamsHaveSquadSet(Match match) {

        Integer homeTeamId = match.getHomeTeam().getId();
        Integer awayTeamId = match.getAwayTeam().getId();

        Optional<TeamStartingSquad> homeTeamSquadOptional = teamSquadRepository.findByTeamIdAndMatchId(homeTeamId, match.getId());
        Optional<TeamStartingSquad> awayTeamSquadOptional = teamSquadRepository.findByTeamIdAndMatchId(awayTeamId, match.getId());

        boolean isReadyToSimulate = homeTeamSquadOptional.isPresent() && awayTeamSquadOptional.isPresent();

        if (!isReadyToSimulate) {
            setScoreForWalkOverMatches(match);
        } else {
            MatchSquad matchSquadForHomeTeam = createMatchSquadForTeam(homeTeamSquadOptional.get());
            MatchSquad matchSquadForAwayTeam = createMatchSquadForTeam(awayTeamSquadOptional.get());

            matchSquadRepository.saveAll(List.of(matchSquadForHomeTeam, matchSquadForAwayTeam));
        }

        return isReadyToSimulate;
    }

    private MatchSquad createMatchSquadForTeam(TeamStartingSquad teamStartingSquad) {

        return MatchSquad.builder()
                .teamId(teamStartingSquad.getTeam().getId())
                .matchId(teamStartingSquad.getMatch().getId())
                .substitutionsAvailable(3)
                .formation(Formation.fromFormationNumber(teamStartingSquad.getSquad().getFormationType()).orElse(null))
                .formationName(teamStartingSquad.getSquad().getName())
                .players(getFirstElevenPlayers(teamSquadRepository.findPlayersPositionsForMatchIdAndTeamId(teamStartingSquad.getMatch().getId(), teamStartingSquad.getTeam().getId()),
                        Objects.requireNonNull(Formation.fromFormationNumber(teamStartingSquad.getSquad().getFormationType()).orElse(null))))
                .build();

    }

    private Map<String, Player> getFirstElevenPlayers(List<PlayerSquadPosition> allPlayersPosition, Formation formation) {

        Map<Position, Player> allPlayersPos = allPlayersPosition.stream().collect(
                Collectors.toMap(
                        PlayerSquadPosition::getPosition,
                        PlayerSquadPosition::getPlayer
                )
        );

        return formation.getPositions().stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        allPlayersPos::get
                ));
    }

    private void saveMatchStatisticBeforeMatch(Match match) {

        MatchStatistic matchStatistic = MatchStatistic.builder()
                .match(match)
                .minute(0)
                .score("0:0")
                .matchStatus(FmMatchStatus.SCHEDULED)
                .goalsDetails(new ArrayList<>())
                .build();

        matchStatisticRepository.save(matchStatistic);

    }

    private void simulateSingleMatch(Match match) {

        log.info("SIMULATE MATCH: {} invoked", match.getId());

        Optional<MatchStatistic> matchStatisticOptional = matchStatisticRepository.findByMatchId(match.getId());

        MatchStatistic matchStatistic = matchStatisticOptional.get();

        Integer matchMinute = matchStatistic.getMinute();

        matchMinute = matchMinute + 1;

        if (matchMinute == 1) {
            matchStatistic.setMatchStatus(FmMatchStatus.LIVE);
        } else if (matchMinute == 90) {
            matchStatistic.setMatchStatus(FmMatchStatus.FINISHED);
        } else if (matchMinute == 45) {
            matchStatistic.setMatchStatus(FmMatchStatus.HALF_BREAK);
        } else if (matchMinute == 46) {
            matchStatistic.setMatchStatus(FmMatchStatus.LIVE);
        }

        matchStatistic.setMinute(matchMinute);

        Map<String, Player> playersForHomeTeam = matchSquadRepository.findPlayerGroupedByPosition(match.getHomeTeam().getId(), match.getId());
        Map<String, Player> playersForAwayTeam = matchSquadRepository.findPlayerGroupedByPosition(match.getAwayTeam().getId(), match.getId());


        //algorytm do ustawiania wyniku meczu na podstawie siły drużyny + jakiś czynnik losowy

        Map<Team, BigDecimal> whoScoresWithWhatProb = squadStrengthCalculatorService.goalProbability(match.getId());

        boolean isGoal = squadStrengthCalculatorService.isGoal(whoScoresWithWhatProb);

        /*lazy initialization exception*/
        List<GoalDetail> goalsDetails = new ArrayList<>(goalDetailRepository.findAllByMatchStatisticId(matchStatistic.getId()));

        if (isGoal) {
            if (whoScoresWithWhatProb.containsKey(match.getHomeTeam())) {
                matchStatistic.setScore(getUpdatedScoreAfterGoal(true, matchStatistic.getScore()));
                Player goalScorer = findGoalScorer(playersForHomeTeam);
                Player goalAssistant = findGoalAssistant(playersForHomeTeam, goalScorer);

                GoalDetail goalDetail = GoalDetail.builder()
                        .assistant(goalAssistant)
                        .scorer(goalScorer)
                        .minute(matchMinute)
                        .matchStatistic(matchStatistic)
                        .build();

                goalsDetails.add(goalDetail);
            } else {
                matchStatistic.setScore(getUpdatedScoreAfterGoal(false, matchStatistic.getScore()));
                Player goalScorer = findGoalScorer(playersForAwayTeam);
                Player goalAssistant = findGoalAssistant(playersForAwayTeam, goalScorer);

                GoalDetail goalDetail = GoalDetail.builder()
                        .assistant(goalAssistant)
                        .scorer(goalScorer)
                        .minute(matchMinute)
                        .matchStatistic(matchStatistic)
                        .build();

                goalsDetails.add(goalDetail);
            }
        }

        matchStatistic.setGoalsDetails(goalsDetails);
        goalDetailRepository.saveAll(goalsDetails);
        matchStatisticRepository.save(matchStatistic);

        if (matchStatistic.getMatchStatus().equals(FmMatchStatus.FINISHED)) {
            match.setScore(matchStatistic.getScore());
            match.setStatus(FmMatchStatus.FINISHED);
            matchRepository.save(match);
        }

    }

    private Player findGoalAssistant(Map<String, Player> players, Player goalScorer) {

        //noinspection OptionalGetWithoutIsPresent
        return players.entrySet()
                .stream()
                .filter(e -> !e.getValue().equals(goalScorer))
                .collect(Collectors.toMap(
                        Map.Entry::getValue,
                        e -> {
                            PlayerAttributes playerAttributes = e.getValue().getPlayerAttributes();

                            return AssistProbabilityPosition
                                    .getAssistProbabilityForPosition(e.getKey())
                                    .multiply(new BigDecimal(String.valueOf(playerAttributes.getPassing())))
                                    .multiply(new BigDecimal(String.valueOf(new Random().nextDouble() * 0.2 + 0.9)));
                        }
                )).entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();

    }

    private Player findGoalScorer(Map<String, Player> players) {

        Map<Player, BigDecimal> goalProbabilityBasedOnPosition = players.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getValue,
                        e -> {
                            PlayerAttributes playerAttributes = e.getValue().getPlayerAttributes();

                            return GoalProbabilityPosition
                                    .getGoalProbabilityForPosition(
                                            e.getKey()).multiply(new BigDecimal(String.valueOf(playerAttributes.getAttacking())))
                                    .multiply(new BigDecimal(String.valueOf(new Random().nextDouble() * 0.2 + 0.9)));
                        }
                ));

        //noinspection OptionalGetWithoutIsPresent

        // TODO: 15.02.2020  
        return goalProbabilityBasedOnPosition.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();
    }

    private String getUpdatedScoreAfterGoal(boolean isHome, String scoreBefore) {

        String[] scores = scoreBefore.split("[:]");

        int homeBefore = Integer.parseInt(scores[0]);
        int awayBefore = Integer.parseInt(scores[1]);

        String newScore;
        if (isHome) {
            int homeAfter = Integer.parseInt(scores[0]) + 1;

            newScore = String.format("%d:%d", homeAfter, awayBefore);

        } else {
            int awayAfter = Integer.parseInt(scores[1]) + 1;

            newScore = String.format("%d:%d", homeBefore, awayAfter);
        }

        return newScore;
    }

    private void setScoreForWalkOverMatches(Match match) {

        // 1. Sprawdz czy obie druzyny mają ustawione składy startowe
        //2. Jeżeli któ©as z druzyn nie ma to walkover 2:0
        //3. Jeżeli składy są ustawione to przejdz do własćiwej symulacji meczu

        List<TeamSquadDto> startingSquadsForMatch = teamSquadService.getStartingSquadsForMatch(match.getId());
        match.setStatus(FmMatchStatus.FINISHED);

        match.setScore(switch (startingSquadsForMatch.size()) {
            case 0 -> "0:0";
            case 1 -> {
                @SuppressWarnings("OptionalGetWithoutIsPresent") Integer teamId = startingSquadsForMatch
                        .stream()
                        .map(teamSquadDto -> teamSquadDto.getTeamDto().getId())
                        .findFirst().get();
                yield match.getHomeTeam().getId().equals(teamId) ? "2:0" : "0:2";
            }
            default -> null;
        });

        matchRepository.save(match);

    }

    @PostConstruct
    public void scheduleTasks() {

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

//        getTriggersForLiveMatches();
//
//        updateScheduledMatchesForCurrentMatchdayInItaly();
//        updateScheduledMatchesForCurrentMatchdayInSpain();
//        updateScheduledMatchesForCurrentMatchdayInEngland();
//
//        updateScheduledMatchesForCurrentMatchday();

        updateAllUserResults();

        simulateFmMatches(
                matchRepository.findAllByStatusIn(List.of(FmMatchStatus.SCHEDULED))
                        .stream()
                        .map(Match::getId)
                        .collect(Collectors.toList()));

    }


    private Map<Match, CronTrigger> getCronTriggersToCheckIfTeamsHaveTheirStartingSquadsSet(List<Match> matches) {

        return matches
                .stream()
                .collect(Collectors.toMap(
                        match -> match,
                        match -> new CronTrigger(generateCronExpressionForCheckIfTeamsHaveTheirStartingSquadsSet(match.getDateTime().minusMinutes(5)))
                ));

    }

    private String generateCronExpressionForCheckIfTeamsHaveTheirStartingSquadsSet(LocalDateTime dateTime) {


        return String.format("%1$s %2$s %3$s %4$s %5$s %6$s", dateTime.getSecond(), dateTime.getMinute(), dateTime.getHour(), dateTime.getDayOfMonth(), dateTime.getMonth().getValue(), dateTime.getDayOfWeek().getValue());

    }

    private List<String> generateCronExpressionsForScheduledMatches(final Integer seconds, final Integer minutes, final Integer hours,
                                                                    final Integer dayOfMonth, final Integer month, final Integer dayOfWeek) {

        return minutes > 0 ?
                List.of(
                        String.format("%1$s %2$s %3$s %4$s %5$s %6$s", seconds, minutes + "/1", hours, dayOfMonth, month, dayOfWeek),
                        String.format("%1$s %2$s %3$s %4$s %5$s %6$s", seconds, "*/1", String.format("%d-%d", hours + 1, hours + 2), dayOfMonth, month, dayOfWeek),
                        String.format("%1$s %2$s %3$s %4$s %5$s %6$s", seconds, "0-" + minutes + "/1", hours + 2, dayOfMonth, month, dayOfWeek))
                :
                List.of(
                        String.format("%1$s %2$s %3$s %4$s %5$s %6$s", seconds, minutes + "*/1", String.format("%d-%d", hours, hours + 2), dayOfMonth, month, dayOfWeek));
    }

    private List<String> generateCronExpressionsForLiveMatches(final Integer seconds, final Integer minutes, final Integer hours,
                                                               final Integer dayOfMonth, final Integer month, final Integer dayOfWeek, final ZonedDateTime matchStartTime) {

        return switch (hours - matchStartTime.getHour()) {
            case 0 -> List.of(
                    String.format("%1$s %2$s %3$s %4$s %5$s %6$s", seconds, minutes + "/1", hours, dayOfMonth, month, dayOfWeek),
                    String.format("%1$s %2$s %3$s %4$s %5$s %6$s", seconds, "*/1", String.format("%d-%d", hours + 1, hours + 2), dayOfMonth, month, dayOfWeek),
                    String.format("%1$s %2$s %3$s %4$s %5$s %6$s", seconds, "0-" + matchStartTime.getMinute() + "/1", hours + 2, dayOfMonth, month, dayOfWeek)
            );
            case 1 -> List.of(
                    String.format("%1$s %2$s %3$s %4$s %5$s %6$s", seconds, minutes + "/1", hours, dayOfMonth, month, dayOfWeek),
                    String.format("%1$s %2$s %3$s %4$s %5$s %6$s", seconds, "0-" + matchStartTime.getMinute() + "/1", hours + 1, dayOfMonth, month, dayOfWeek)
            );

            case 2 -> List.of(
                    String.format("%1$s %2$s %3$s %4$s %5$s %6$s", seconds, minutes + "-" + matchStartTime.getMinute() + "/1", hours, dayOfMonth, month, dayOfWeek)
            );
            default -> Collections.emptyList();

        };

    }

    private List<CronTrigger> getCronTriggersForUpdateScheduledMatches(List<BetMatch> matches) {

        return matches.stream()
                .distinct()
                .map(BetMatch::getUtcDate)
                .map(date -> new CronTrigger(String.format("%1$s %2$s %3$s %4$s %5$s %6$s", date.getSecond(), date.getMinute() + 1, date.getHour(), date.getDayOfMonth(), date.getMonth().getValue(), date.getDayOfWeek().getValue())))
                .collect(Collectors.toList());

    }

    private List<CronTrigger> getCronTriggersForLiveMatches(List<BetMatch> matches) {
        final LocalDateTime recordingStartTime = LocalDateTime.now().plusSeconds(10);
        return matches.stream().map(BetMatch::getUtcDate)
                .distinct()
                .flatMap(date -> generateCronExpressionsForLiveMatches(recordingStartTime.getSecond(), recordingStartTime.getMinute(), recordingStartTime.getHour(), recordingStartTime.getDayOfMonth(), recordingStartTime.getMonth().getValue(), recordingStartTime.getDayOfWeek().getValue(), date)
                        .stream()
                        .map(CronTrigger::new)
                        .collect(Collectors.toList())
                        .stream())
                .collect(Collectors.toList());

    }

    private List<CronTrigger> getCronTriggersForScheduledMatches(List<BetMatch> matches) {
        return matches.stream().map(BetMatch::getUtcDate)
                .distinct()
                .flatMap(date -> generateCronExpressionsForScheduledMatches(date.getSecond(), date.getMinute(), date.getHour(), date.getDayOfMonth(), date.getMonth().getValue(), date.getDayOfWeek().getValue())
                        .stream()
                        .map(CronTrigger::new)
                        .collect(Collectors.toList())
                        .stream())
                .collect(Collectors.toList());
    }


    private List<String> generateCronExpressionsForFootballManagerMatches(Integer seconds, Integer minutes, Integer hours,
                                                                          Integer dayOfMonth, Integer month, Integer dayOfWeek) {

        LocalDate now = LocalDate.now();
        LocalDateTime dateTime = LocalDateTime.of(now.getYear(), month, dayOfMonth, hours, minutes);
        LocalDateTime dateTimePlus1Hour = dateTime.plusHours(1);
        LocalDateTime dateTimePlus2Hours = dateTime.plusHours(2);

        return minutes < 14 ?
                List.of(
                        String.format("%1$s %2$s %3$s %4$s %5$s %6$s", seconds, minutes + "-" + (46 + minutes) + "/1", hours, dayOfMonth, month, dayOfWeek),
                        String.format("%1$s %2$s %3$s %4$s %5$s %6$s", seconds, dateTimePlus1Hour.getMinute() + "-" + (46 + dateTimePlus1Hour.getMinute()) + "/1", dateTimePlus1Hour.getHour(), dateTimePlus1Hour.getDayOfMonth(), dateTimePlus1Hour.getMonth().getValue(), dateTime.getDayOfWeek().getValue()))
                :
                List.of(
                        String.format("%1$s %2$s %3$s %4$s %5$s %6$s", seconds, minutes + "/1", hours, dayOfMonth, month, dayOfWeek),
                        String.format("%1$s %2$s %3$s %4$s %5$s %6$s", seconds, "0-" + (dateTimePlus2Hours.getMinute() - 16) + "/1", dateTimePlus1Hour.getHour(), dateTimePlus1Hour.getDayOfMonth(), dateTimePlus1Hour.getMonth().getValue(), dateTimePlus1Hour.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US)),
                        String.format("%1$s %2$s %3$s %4$s %5$s %6$s", seconds, dateTimePlus1Hour.getMinute() + "/1", dateTimePlus1Hour.getHour(), dateTimePlus1Hour.getDayOfMonth(), dateTimePlus1Hour.getMonth().getValue(), dateTimePlus1Hour.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US)),
                        String.format("%1$s %2$s %3$s %4$s %5$s %6$s", seconds, "0-" + (dateTimePlus2Hours.getMinute() - 16) + "/1", dateTimePlus2Hours.getHour(), dateTimePlus2Hours.getDayOfMonth(), dateTimePlus2Hours.getMonth().getValue(), dateTimePlus2Hours.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US)));
    }


    private List<CronTrigger> generateFootballManagerMatchesTriggerForScoreUpdate(Match match) {
        LocalDateTime dateTime = match.getDateTime();

        return generateCronExpressionsForFootballManagerMatches(dateTime.getSecond(), dateTime.getMinute(), dateTime.getHour(),
                dateTime.getDayOfMonth(), dateTime.getMonth().getValue(), dateTime.getDayOfWeek().getValue())
                .stream()
                .map(CronTrigger::new)
                .collect(Collectors.toList());
    }

    //wysyłanie maila 1 h przed meczem


}


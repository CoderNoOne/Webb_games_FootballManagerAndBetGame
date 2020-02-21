package com.app.web_app.service.bet_game;

import com.app.web_app.exceptions.AppException;
import com.app.web_app.model.BetLeague;
import com.app.web_app.model.BetMatch;
import com.app.web_app.model.ResponseBet;
import com.app.web_app.model.bet_game.BetPoints;
import com.app.web_app.model.bet_game.BetScore;
import com.app.web_app.model.bet_game.ScoreEntity;
import com.app.web_app.model.dto.bet_game.ScoreDto;
import com.app.web_app.model.manager_game.mapper.ManagerMapper;
import com.app.web_app.repository.bet_game.BetPointsRepository;
import com.app.web_app.repository.bet_game.ScoreEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BetService {

    private static final String FINISHED_MATCHES_PER_COMPETITION_URL = "https://api.football-data.org/v2/competitions/{id}/matches?status=FINISHED";
    private static final String DATE_API_URL = "https://api.football-data.org/v2/competitions/2019/matches?dateFrom/{dateFrom}}/dateTo/{dateTo}";
    private static final String API_URL = "https://api.football-data.org/v2/competitions/{id}/matches?status={status}";
    private static final Map<String, List<String>> API_CALL_HEADERS = Map.of("X-Auth-Token", List.of("0f8a7383c5c5443ca65480bf6306eeca"));


    private final RestTemplate restTemplate;
    private final ScoreEntityRepository scoreEntityRepository;
    private final BetPointsRepository betPointsRepository;
    private final ManagerMapper managerMapper;

    @Cacheable("FinishedItalian")
    public List<BetMatch> getFinishedMatchesForSerieA() {
        return getFinishedMatchesForSpecifiedLeague(BetLeague.ITALY);
    }

    @CachePut("FinishedItalian")
    public List<BetMatch> updateFinishedMatchesForSerieA() {
        return getFinishedMatchesForSpecifiedLeague(BetLeague.ITALY);
    }

    @Cacheable("FinishedSpain")
    public List<BetMatch> getFinishedMatchesForLaLiga() {
        return getFinishedMatchesForSpecifiedLeague(BetLeague.SPAIN);
    }

    @CachePut("FinishedSpain")
    public List<BetMatch> updateFinishedMatchesForLaLiga() {
        return getFinishedMatchesForSpecifiedLeague(BetLeague.SPAIN);
    }

    @Cacheable("FinishedPremierLeague")
    public List<BetMatch> getFinishedMatchesForPremierLeague() {
        return getFinishedMatchesForSpecifiedLeague(BetLeague.PREMIER_LEAGUE);
    }

    @CachePut("FinishedPremierLeague")
    public List<BetMatch> updateFinishedMatchesForChampionsLeague() {
        return getFinishedMatchesForSpecifiedLeague(BetLeague.PREMIER_LEAGUE);
    }

    private List<BetMatch> getFinishedMatchesForSpecifiedLeague(BetLeague league) {
        HttpEntity<ResponseBet> matches = new HttpEntity<>(new LinkedMultiValueMap<>(API_CALL_HEADERS));
        return Objects.requireNonNull(restTemplate.exchange(FINISHED_MATCHES_PER_COMPETITION_URL, HttpMethod.GET, matches, ResponseBet.class,
                league.getId(), "FINISHED").getBody()).getMatches();
    }


    @Cacheable(value = "spainScheduled")
    public List<BetMatch> getScheduledMatchesForSpain() {
        return getScheduledMatchesForSpecifiedLeague(BetLeague.SPAIN)
                .stream()
                .filter(match -> match.getCurrentMatchday().equals(match.getMatchday()))
                .collect(Collectors.toList());
    }

    @CachePut(value = "spainScheduled")
    public List<BetMatch> updateScheduledMatchesForSpain() {
        return getScheduledMatchesForSpecifiedLeague(BetLeague.SPAIN)
                .stream()
                .filter(match -> match.getCurrentMatchday().equals(match.getMatchday()))
                .collect(Collectors.toList());
    }

    @CachePut(value = "italyScheduled")
    public List<BetMatch> updateScheduledMatchesForItaly() {
        return getScheduledMatchesForSpecifiedLeague(BetLeague.ITALY)
                .stream()
                .filter(match -> match.getCurrentMatchday().equals(match.getMatchday()))
                .collect(Collectors.toList());
    }

    @Cacheable(value = "italyScheduled")
    public List<BetMatch> getScheduledMatchesForItaly() {
        return getScheduledMatchesForSpecifiedLeague(BetLeague.ITALY)
                .stream()
                .filter(match -> match.getCurrentMatchday().equals(match.getMatchday()))
                .collect(Collectors.toList());
    }

    @Cacheable(value = "premierLeagueScheduled")
    public List<BetMatch> getScheduledMatchesForPremierLeague() {
        return getScheduledMatchesForSpecifiedLeague(BetLeague.PREMIER_LEAGUE)
                .stream()
                .filter(match -> match.getCurrentMatchday().equals(match.getMatchday()))
                .collect(Collectors.toList());
    }

    @CachePut(value = "premierLeagueScheduled")
    public List<BetMatch> updateScheduledMatchesForPremierLeague() {
        return getScheduledMatchesForSpecifiedLeague(BetLeague.PREMIER_LEAGUE)
                .stream()
                .filter(match -> match.getCurrentMatchday().equals(match.getMatchday()))
                .collect(Collectors.toList());
    }

    @Cacheable(value = "premierLeagueLiveMatches")
    public List<BetMatch> getLiveMatchesForPremierLeague() {
        return getLiveMatchesForSpecifiedLeague(BetLeague.PREMIER_LEAGUE);
    }

    @CachePut(value = "premierLeagueLiveMatches")
    public List<BetMatch> updateLiveMatchesForPremierLeague() {
        return getLiveMatchesForSpecifiedLeague(BetLeague.PREMIER_LEAGUE);
    }

    @Cacheable(value = "italyLiveMatches")
    public List<BetMatch> getLiveMatchesForSerieA() {
        return getLiveMatchesForSpecifiedLeague(BetLeague.ITALY);
    }

    @CachePut(value = "italyLiveMatches")
    public List<BetMatch> updateLiveMatchesForSerieA() {
        return getLiveMatchesForSpecifiedLeague(BetLeague.ITALY);
    }

    @Cacheable(value = "spainLiveMatches")
    public List<BetMatch> getLiveMatchesForSpain() {
        return getLiveMatchesForSpecifiedLeague(BetLeague.SPAIN);
    }

    @CachePut(value = "spainLiveMatches")
    public List<BetMatch> updateLiveMatchesForSpain() {
        return getLiveMatchesForSpecifiedLeague(BetLeague.SPAIN);
    }


    public void saveBet(ScoreDto scoreDto, BetLeague league, String username) {

        scoreEntityRepository.findByLeagueAndUserUsername(league, username)
                .ifPresentOrElse(scoreEntity -> scoreEntity.getBetScores().addAll(scoreDto.getBetScores()), () ->
                        scoreEntityRepository.save(managerMapper.mapScoreDtoToEntity(scoreDto)));
    }

    private List<BetMatch> getScheduledMatchesForSpecifiedLeague(BetLeague league) {
        HttpEntity<ResponseBet> matches = new HttpEntity<>(new LinkedMultiValueMap<>(API_CALL_HEADERS));
        return Objects.requireNonNull(restTemplate.exchange(API_URL, HttpMethod.GET, matches, ResponseBet.class,
                league.getId(), "SCHEDULED").getBody()).getMatches();
    }


    private List<BetMatch> getLiveMatchesForSpecifiedLeague(BetLeague league) {
        HttpEntity<ResponseBet> matches = new HttpEntity<>(new LinkedMultiValueMap<>(API_CALL_HEADERS));
        return Objects.requireNonNull(restTemplate.exchange(API_URL, HttpMethod.GET, matches, ResponseBet.class,
                league.getId(), "LIVE").getBody()).getMatches();
    }

    public List<ScoreEntity> findAllBetByUsername(String username) {
        return scoreEntityRepository.findAllByUserName(username);
    }

    public List<ScoreDto> findAllBetByUsernameAndLeague(String username, BetLeague league) {

        return scoreEntityRepository.findAllByUserNameAndLeague(username, league)
                .stream()
                .map(managerMapper::mapScoreEntityToDto)
                .collect(Collectors.toList());
    }

    public List<ScoreEntity> findAllBets() {
        return scoreEntityRepository.findAll();
    }

    public void savePoints(BetPoints betPoints) {

        betPointsRepository.saveAndFlush(betPoints);
    }

    public List<ScoreEntity> findAllByUsernameAndLeague(String username, BetLeague league) {
        return scoreEntityRepository.findAllByUserNameAndLeague(username, league);
    }

    public Map<Integer, List<BetScore>> findAllBetScoreByUsernameAndLeagueGroupedByMatchday(String username, BetLeague league) {

        if (username == null || league == null) {
            throw new AppException("Null method arguments: username=" + username + "| league: " + league);
        }

        return scoreEntityRepository.findAllByUserNameAndLeague(username, league)
                .stream()
                .flatMap(scoreEntity -> scoreEntity.getBetScores().stream())
                .sorted(Comparator.comparing(BetScore::getMatchDay).reversed())
                .collect(Collectors.groupingBy(
                        BetScore::getMatchDay));
    }

}


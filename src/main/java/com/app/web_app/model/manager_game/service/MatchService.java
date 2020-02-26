package com.app.web_app.model.manager_game.service;

import com.app.web_app.exceptions.AppException;
import com.app.web_app.model.manager_game.Match;
import com.app.web_app.model.manager_game.dto.MatchDto;
import com.app.web_app.model.manager_game.mapper.ManagerMapper;
import com.app.web_app.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final ManagerMapper managerMapper;

    public List<Match> findAllPlayedMatchesForLeagueById(Integer leagueId) {
        return matchRepository.findAllByLeagueId(leagueId);
    }

    public List<MatchDto> getFinishedMatchesForLeague(Integer leagueId) {

        return matchRepository.findAllByScoreNotNullAndLeagueId(leagueId)
                .stream()
                .map(managerMapper::mapMatchToDto)
                .collect(Collectors.toList());
    }

    public List<MatchDto> getScheduledMatches(Integer leagueId) {
        return matchRepository.findAllByScoreNullAndLeagueId(leagueId)
                .stream()
                .map(managerMapper::mapMatchToDto)
                .collect(Collectors.toList());
    }

    public List<MatchDto> getAllScheduledMatchesByTeamId(Integer id, Pageable pageable) {

        List<MatchDto> collect = matchRepository.findAllByAwayTeamIdOrHomeTeamId(id, pageable)
                .stream()
                .filter(match -> match.getScore() == null)
                .map(managerMapper::mapMatchToDto)
                .collect(Collectors.toList());

        System.out.println(collect);

        return collect;
    }

    public Optional<MatchDto> getById(Integer matchId) {

        return matchRepository.findById(matchId)
                .map(managerMapper::mapMatchToDto);
    }

    public Optional<MatchDto> getMatchById(Integer matchId) {
        return matchRepository.findById(matchId)
                .map(managerMapper::mapMatchToDto);
    }


    public Map<Integer, List<MatchDto>> getMatchesGroupedByMatchDay(List<MatchDto> matches) {

        if (matches == null) {
            throw new AppException("Matches list is null");
        }

        return matches.stream()
                .collect(Collectors.collectingAndThen(Collectors.groupingBy(MatchDto::getMatchDay),
                        map -> map.entrySet()
                                .stream()
                                .sorted(Map.Entry.comparingByKey())))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldVal, newVal) -> oldVal,
                        LinkedHashMap::new
                ));

    }

    public LocalDateTime getStartingTimeForMatchById(Integer matchId){

        if(matchId == null){
            throw new AppException("Match id is null");
        }

        return matchRepository.findById(matchId)
                .orElseThrow(()-> new AppException("Match with id {0} doesn't exist"))
                .getDateTime();

    }

}

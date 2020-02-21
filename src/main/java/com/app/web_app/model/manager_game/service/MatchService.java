package com.app.web_app.model.manager_game.service;

import com.app.web_app.model.manager_game.Match;
import com.app.web_app.model.manager_game.dto.MatchDto;
import com.app.web_app.model.manager_game.mapper.ManagerMapper;
import com.app.web_app.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private ManagerMapper managerMapper;

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

//    public SquadDto setStartingSquadForTeamByMatch(Integer matchId, Integer teamId, SquadDto startingSquad) {
//
//        matchRepository.findByMatchIdAndTeamId(matchId, teamId)
//                .ifPresentOrElse(
//                        (value) -> value.getStartingSquads().add()
//                );
//    }
}

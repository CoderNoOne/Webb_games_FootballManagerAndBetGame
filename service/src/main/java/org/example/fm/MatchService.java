package org.example.fm;

import lombok.RequiredArgsConstructor;
import org.example.exceptions.AppException;
import org.example.fm.entity.Match;
import org.example.model.fm.MatchDto;
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

    public List<Match> findAllPlayedMatchesForLeagueById(Integer leagueId) {
        return matchRepository.findAllByLeagueId(leagueId);
    }

    public List<MatchDto> getFinishedMatchesForLeague(Integer leagueId) {

        return matchRepository.findAllByScoreNotNullAndLeagueId(leagueId)
                .stream()
                .map(ManagerMapper::mapMatchToDto)
                .collect(Collectors.toList());
    }

    public List<MatchDto> getScheduledMatches(Integer leagueId) {
        return matchRepository.findAllByScoreNullAndLeagueId(leagueId)
                .stream()
                .map(ManagerMapper::mapMatchToDto)
                .collect(Collectors.toList());
    }

    public List<MatchDto> getAllScheduledMatchesByTeamId(Integer id, Pageable pageable) {

        return matchRepository.findAllByAwayTeamIdOrHomeTeamId(id, pageable)
                .stream()
                .filter(match -> match.getScore() == null)
                .map(ManagerMapper::mapMatchToDto)
                .collect(Collectors.toList());

    }

    public Optional<MatchDto> getById(Integer matchId) {

        if (matchId == null) {
            throw new AppException("Match id is null");

        }
        return matchRepository.findById(matchId)
                .map(ManagerMapper::mapMatchToDto);
    }

    public Optional<MatchDto> getMatchById(Integer matchId) {
        return matchRepository.findById(matchId)
                .map(ManagerMapper::mapMatchToDto);
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

    public LocalDateTime getStartingTimeForMatchById(Integer matchId) {

        if (matchId == null) {
            throw new AppException("Match id is null");
        }

        return matchRepository.findById(matchId)
                .orElseThrow(() -> new AppException("Match with id {0} doesn't exist"))
                .getDateTime();

    }

}

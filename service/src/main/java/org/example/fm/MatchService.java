package org.example.fm;

import lombok.RequiredArgsConstructor;
import org.example.core.exceptions.AppException;
import org.example.model.fm.MatchDto;
import org.example.repository.fm.MatchRepository;
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

    public List<MatchDto> getFinishedMatchesForLeague(Integer leagueId) {

        if (leagueId == null) {
            throw new AppException("League id is null");
        }

        return matchRepository.findAllByScoreNotNullAndLeagueId(leagueId)
                .stream()
                .map(ManagerMapper::mapMatchToDto)
                .collect(Collectors.toList());
    }

    public List<MatchDto> getScheduledMatches(Integer leagueId) {

        if (leagueId == null) {
            throw new AppException("League id is null");
        }

        return matchRepository.findAllByScoreNullAndLeagueId(leagueId)
                .stream()
                .map(ManagerMapper::mapMatchToDto)
                .collect(Collectors.toList());
    }

    public List<MatchDto> getAllScheduledMatchesByTeamId(Integer id, Pageable pageable) {

        if (id == null) {
            throw new AppException("Team id is null");
        }

        if (pageable == null) {
            throw new AppException("Pageable is null");
        }

        return matchRepository.findAllByAwayTeamIdOrHomeTeamId(id, pageable)
                .stream()
                .filter(match -> match.getScore() == null)
                .map(ManagerMapper::mapMatchToDto)
                .collect(Collectors.toList());

    }

    public Optional<MatchDto> getMatchById(Integer matchId) {

        if (matchId == null) {
            throw new AppException("Match id is null");
        }

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
                .orElseThrow(() -> new AppException(String.format("Match with id %d doesn't exist", matchId)))
                .getDateTime();
    }
}

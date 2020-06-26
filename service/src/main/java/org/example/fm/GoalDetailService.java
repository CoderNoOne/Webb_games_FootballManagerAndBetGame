package org.example.fm;

import lombok.RequiredArgsConstructor;
import org.example.core.exceptions.AppException;
import org.example.entity.fm.entity.Match;
import org.example.model.fm.GoalDetailDto;
import org.example.repository.fm.GoalDetailRepository;
import org.example.repository.fm.MatchRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GoalDetailService {

    private final GoalDetailRepository goalDetailRepository;
    private final MatchRepository matchRepository;

    public List<GoalDetailDto> getGoalDetailsForMatchId(Integer matchId) {

        if (Objects.isNull(matchId)) {
            throw new AppException("Match id is null");
        }

        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new AppException(String.format("Match with id %d doesn't exist", matchId)));

        Integer homeTeamId = match.getHomeTeam().getId();

        return goalDetailRepository.findAllByMatchId(matchId)
                .stream()
                .map(ManagerMapper::mapGoalDetailToDto)
                .peek(goalDetailDto -> goalDetailDto.setTeam(goalDetailDto.getScorer().getTeamId().equals(homeTeamId) ? "Home" : "Away"))
                .collect(Collectors.toList());
    }
}

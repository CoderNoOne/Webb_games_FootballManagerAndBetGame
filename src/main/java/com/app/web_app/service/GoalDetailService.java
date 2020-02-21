package com.app.web_app.service;

import com.app.web_app.exceptions.AppException;
import com.app.web_app.model.manager_game.GoalDetail;
import com.app.web_app.model.manager_game.Match;
import com.app.web_app.model.manager_game.Team;
import com.app.web_app.model.manager_game.dto.GoalDetailDto;
import com.app.web_app.model.manager_game.mapper.ManagerMapper;
import com.app.web_app.repository.GoalDetailRepository;
import com.app.web_app.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GoalDetailService {

    private final GoalDetailRepository goalDetailRepository;
    private final MatchRepository matchRepository;
    private final ManagerMapper managerMapper;

    public List<GoalDetailDto> getGoalDetailsForMatchStatisticId(Integer matchStatisticId) {

        if (matchStatisticId == null) {
            throw new AppException("Match statistic id is null");
        }

        return goalDetailRepository.findAllByMatchStatisticId(matchStatisticId)
                .stream()
                .map(managerMapper::mapGoalDetailToDto)
                .collect(Collectors.toList());
    }

    public Map<String, List<GoalDetailDto>> getGoalDetailsForMatchIdGroupedByTeams(Integer matchId) {

        if (matchId == null) {
            throw new AppException("Match id is null");
        }

        AtomicReference<Map<String, List<GoalDetailDto>>> result = new AtomicReference<>();

        matchRepository.findById(matchId)
                .ifPresentOrElse(match -> {
                            List<GoalDetail> allGoalDetails = goalDetailRepository.findAllByMatchId(matchId);

                            result.set(Map.of(
                                    "Home", getGoalsDetailForTeam(allGoalDetails, match.getHomeTeam()),
                                    "Away", getGoalsDetailForTeam(allGoalDetails, match.getAwayTeam())
                            ));
                        }, () -> {
                            throw new AppException(MessageFormat.format("Match with id: {0} doesn't exist", matchId));
                        }
                );

        return result.get();

    }


    private List<GoalDetailDto> getGoalsDetailForTeam(List<GoalDetail> allGoalDetails, Team team) {

        return allGoalDetails
                .stream()
                .filter(goalDetail -> goalDetail.getScorer().getTeam().equals(team))
                .map(managerMapper::mapGoalDetailToDto)
                .collect(Collectors.toList());

    }

    public List<GoalDetailDto> getGoalDetailsForMatchId(Integer matchId) {

        if (matchId == null) {
            throw new AppException("Match id is null");
        }

        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new AppException(MessageFormat.format("Match with id {0} doesn't exist", matchId)));

        Integer homeTeamId = match.getHomeTeam().getId();

        return goalDetailRepository.findAllByMatchId(matchId)
                .stream()
                .map(managerMapper::mapGoalDetailToDto)
                .peek(goalDetailDto -> goalDetailDto.setTeam(goalDetailDto.getScorer().getTeamId().equals(homeTeamId) ? "Home" : "Away"))
                .collect(Collectors.toList());
    }


}

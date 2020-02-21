package com.app.web_app.model.manager_game.service;

import com.app.web_app.model.manager_game.dto.MatchStatisticDto;
import com.app.web_app.model.manager_game.mapper.ManagerMapper;
import com.app.web_app.model.manager_game.repository.MatchStatisticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchStatisticService {

    private final ManagerMapper managerMapper;
    private final MatchStatisticRepository matchStatisticRepository;

    public Optional<MatchStatisticDto> getMatchStatisticByMatchId(Integer matchId) {

        return matchStatisticRepository.findByMatchId(matchId)
                .map(managerMapper::mapMatchStatisticToDto);
    }

}

package com.app.web_app.service.bet_game;

import com.app.web_app.exceptions.AppException;
import com.app.web_app.mapper.BetGameMapper;
import com.app.web_app.model.BetLeague;
import com.app.web_app.model.dto.BetPointsDto;
import com.app.web_app.repository.bet_game.BetPointsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BetPointsService {

    private final BetPointsRepository betPointsRepository;
    private final BetGameMapper mapper;

    public Map<String, Integer> getAllByLeagueGroupedByUser(BetLeague league) {

        if (league == null) {
            throw new AppException("League is null");
        }

        return betPointsRepository.findAllByLeague(league.name())
                .stream()
                .map(mapper::mapBetPointsToDto)
                .sorted(Comparator.comparing(BetPointsDto::getPointsNumber).reversed())
                .collect(Collectors.toMap(
                        BetPointsDto::getUsername,
                        BetPointsDto::getPointsNumber,
                        (oldVal, newVal) -> oldVal,
                        LinkedHashMap::new
                ));
    }

    public Map<String, Integer> getAllGroupedByUser() {

        return betPointsRepository.findAll()
                .stream()
                .map(mapper::mapBetPointsToDto)
                .sorted(Comparator.comparing(BetPointsDto::getPointsNumber).reversed())
                .collect(Collectors.toMap(
                        BetPointsDto::getUsername,
                        BetPointsDto::getPointsNumber,
                        (oldVal, newVal) -> oldVal,
                        LinkedHashMap::new
                ));
    }

}

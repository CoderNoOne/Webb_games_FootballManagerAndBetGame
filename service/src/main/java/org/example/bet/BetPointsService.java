package org.example.bet;

import lombok.RequiredArgsConstructor;
import org.example.model.bet.BetPointsDto;
import org.example.model.bet.enums.BetLeague;
import org.example.exceptions.AppException;
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

    public Map<String, Integer> getAllByLeagueGroupedByUser(BetLeague league) {

        if (league == null) {
            throw new AppException("League is null");
        }

        return betPointsRepository.findAllByLeague(league)
                .stream()
                .map(BetGameMapper::mapBetPointsToDto)
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
                .map(BetGameMapper::mapBetPointsToDto)
                .sorted(Comparator.comparing(BetPointsDto::getPointsNumber).reversed())
                .collect(Collectors.toMap(
                        BetPointsDto::getUsername,
                        BetPointsDto::getPointsNumber,
                        (oldVal, newVal) -> oldVal,
                        LinkedHashMap::new
                ));
    }

}

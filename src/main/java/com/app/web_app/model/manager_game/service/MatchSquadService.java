package com.app.web_app.model.manager_game.service;

import com.app.web_app.model.manager_game.MatchSquad;
import com.app.web_app.model.manager_game.dto.MatchSquadDto;
import com.app.web_app.model.manager_game.mapper.ManagerMapper;
import com.app.web_app.model.manager_game.repository.MatchSquadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchSquadService {

    private final MatchSquadRepository matchSquadRepository;

    private final ManagerMapper managerMapper;

    public Optional<MatchSquadDto> save(MatchSquadDto matchSquadDto) {

        AtomicReference<Optional<MatchSquadDto>> result = new AtomicReference<>(Optional.empty());

        matchSquadRepository.findByTeamIdAndMatchId(matchSquadDto.getTeamId(), matchSquadDto.getMatchId())
                .ifPresentOrElse((value) -> {
                            value.setPlayers(matchSquadDto.getPlayers()
                                    .entrySet()
                                    .stream()
                                    .collect(Collectors.toMap(
                                            Map.Entry::getKey,
                                            e -> managerMapper.mapPlayerDtoToPlayerEntity(e.getValue())
                                    )));
                            value.setFormation(matchSquadDto.getFormation());
                            value.setAggressionLevel(matchSquadDto.getAggressionLevel());
                            value.setAttitude(matchSquadDto.getAttitude());
                            value.setSubstitutionsAvailable(matchSquadDto.getSubstitutionsNumberAvailable());
                            result.set(Optional.of(value).map(managerMapper::mapMatchSquadToDto));
                        },
                        () -> {
                            MatchSquad savedValue = matchSquadRepository.save(managerMapper.mapMatchSquadDtoToMatchSquad(matchSquadDto));
                            result.set(Optional.of(savedValue).map(managerMapper::mapMatchSquadToDto));
                        }
                );

        return result.get();

    }

    private Optional<MatchSquadDto> saveMatchSquad(MatchSquadDto matchSquadDto) {
        return Optional.of(matchSquadRepository.save(managerMapper.mapMatchSquadDtoToMatchSquad(matchSquadDto)))
                .map(managerMapper::mapMatchSquadToDto);
    }

    public Optional<MatchSquadDto> getByTeamIdAndMatchId(Integer teamId, Integer matchId) {

        return matchSquadRepository.findByTeamIdAndMatchId(teamId, matchId)
                .map(managerMapper::mapMatchSquadToDto);
    }

    public Optional<MatchSquadDto> load(Integer teamId, Integer matchId) {

        return matchSquadRepository.findByTeamIdAndMatchId(teamId, matchId)
                .map(managerMapper::mapMatchSquadToDto);


    }
}

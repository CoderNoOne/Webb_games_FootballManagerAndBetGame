package com.app.web_app.model.manager_game.service;

import com.app.web_app.exceptions.AppException;
import com.app.web_app.model.manager_game.MatchSquad;
import com.app.web_app.model.manager_game.Player;
import com.app.web_app.model.manager_game.dto.MatchSquadDto;
import com.app.web_app.model.manager_game.dto.PlayerDto;
import com.app.web_app.model.manager_game.mapper.ManagerMapper;
import com.app.web_app.model.manager_game.repository.MatchSquadRepository;
import com.app.web_app.model.manager_game.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchSquadService {

    private final MatchSquadRepository matchSquadRepository;
    private final ManagerMapper managerMapper;
    private final PlayerRepository playerRepository;

    public Optional<MatchSquadDto> save(MatchSquadDto matchSquadDto) {

        AtomicReference<Optional<MatchSquadDto>> result = new AtomicReference<>(Optional.empty());

        matchSquadRepository.findByTeamIdAndMatchId(matchSquadDto.getTeamDto().getId(), matchSquadDto.getMatchId())
                .ifPresentOrElse((value) -> {
                            value.setPlayers(Stream.of(matchSquadDto.getPlayers(), matchSquadDto.getSubstitutions())
                                    .flatMap(e -> e.entrySet().stream())
                                    .collect(Collectors.toMap(
                                            Map.Entry::getKey,
                                            e -> managerMapper.mapPlayerDtoToPlayerEntity(e.getValue())
                                    )));
                            value.setFormationType(matchSquadDto.getFormation().getNumber());
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

    public Optional<MatchSquadDto> loadByTeamIdAndMatchId(Integer teamId, Integer matchId) {

        if (teamId == null) {
            throw new AppException("Team id is null");
        }

        if (matchId == null) {
            throw new AppException("Match id is null");
        }

        return matchSquadRepository.findByTeamIdAndMatchId(teamId, matchId)
                .map(managerMapper::mapMatchSquadToDto);
    }


    public Integer makeSubstitutions(Integer matchId, Integer teamId, Map<String, PlayerDto> subs) {

        if (matchId == null) {
            throw new AppException("Match id is null");
        }

        if (teamId == null) {
            throw new AppException("Team id is null");
        }

        if (subs == null) {
            throw new AppException("Subs map is null");
        }

        MatchSquad matchSquad = matchSquadRepository.findByTeamIdAndMatchId(teamId, matchId)
                .orElseThrow(() -> new AppException(MessageFormat.format("No match squad for (matchId {0}, {1})", matchId, teamId)));

        List<Player> players = playerRepository.findPlayersByIdIn(subs.values().stream().filter(Objects::nonNull).map(PlayerDto::getId).collect(Collectors.toList()));
        Map<String, Player> currentPlayers = matchSquad.getPlayers();

        Map<String, Player> subPlayersWithPosition = subs.entrySet()
                .stream()
                .filter(e -> e.getValue() != null)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> players.stream().filter(player -> player.getId().equals(e.getValue().getId())).findFirst().orElseThrow(() -> new AppException(MessageFormat.format("Player with id: {0} doesn't exist", e.getValue().getId())))
                ));

        currentPlayers.putAll(subPlayersWithPosition);

        Map<String, Player> playersToRemove = currentPlayers.entrySet()
                .stream()
                .filter(e -> !subPlayersWithPosition.containsValue(e.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        playersToRemove.forEach(currentPlayers::remove);
        //concurrent modification exception

        matchSquad.setPlayers(currentPlayers);
        matchSquad.setSubstitutionsAvailable(matchSquad.getSubstitutionsAvailable() - 1);

        matchSquadRepository.save(matchSquad);

        return players.size();
    }

    public Optional<MatchSquadDto> getByTeamIdAndMatchId(Integer teamId, Integer matchId) {

        if (teamId == null) {
            throw new AppException("Team id is null");
        }

        if (matchId == null) {
            throw new AppException("Match id is null");
        }

        return matchSquadRepository.findByTeamIdAndMatchId(teamId, matchId)
                .map(managerMapper::mapMatchSquadToDto);
    }

    public Optional<MatchSquadDto> getOpponentSquadForMatch(Integer matchId, Integer teamId) {

        if (teamId == null) {
            throw new AppException("Team id is null");
        }

        if (matchId == null) {
            throw new AppException("Match id is null");
        }

        return matchSquadRepository.findByMatchIdAndTeamIdNot(matchId, teamId)
                .map(managerMapper::mapMatchSquadToDto);

    }
}

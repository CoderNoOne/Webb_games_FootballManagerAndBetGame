package com.app.web_app.model.manager_game.service;

import com.app.web_app.exceptions.AppException;
import com.app.web_app.model.manager_game.MatchSquad;
import com.app.web_app.model.manager_game.Player;
import com.app.web_app.model.manager_game.PlayerSquadPosition;
import com.app.web_app.model.manager_game.TeamStartingSquad;
import com.app.web_app.model.manager_game.dto.MatchSquadDto;
import com.app.web_app.model.manager_game.dto.PlayerDto;
import com.app.web_app.model.manager_game.enums.Position;
import com.app.web_app.model.manager_game.mapper.ManagerMapper;
import com.app.web_app.model.manager_game.repository.MatchSquadRepository;
import com.app.web_app.model.manager_game.repository.PlayerRepository;
import com.app.web_app.model.manager_game.repository.TeamSquadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchSquadService {

    private final MatchSquadRepository matchSquadRepository;
    private final ManagerMapper managerMapper;
    private final PlayerRepository playerRepository;
    private final TeamSquadRepository teamSquadRepository;

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


        List<Player> players = playerRepository.findPlayersByIdIn(subs.values().stream().filter(Objects::nonNull).map(PlayerDto::getId).collect(Collectors.toList()));

        Map<String, Player> subPlayersWithPosition = subs.entrySet()
                .stream()
                .filter(e -> e.getValue() != null)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> players.stream().filter(player -> player.getId().equals(e.getValue().getId())).findFirst().orElseThrow(() -> new AppException(MessageFormat.format("Player with id: {0} doesn't exist", e.getValue().getId())))
                ));

        MatchSquad matchSquad = matchSquadRepository.findByTeamIdAndMatchId(teamId, matchId)
                .orElseThrow(() -> new AppException(MessageFormat.format("No match squad for (matchId {0}, {1})", matchId, teamId)));

        Map<String, Player> currentPlayers = matchSquad.getPlayers();
        currentPlayers.putAll(subPlayersWithPosition);

        matchSquad.setPlayers(currentPlayers);
        matchSquad.setSubstitutionsAvailable(matchSquad.getSubstitutionsAvailable() - 1);

        TeamStartingSquad teamStartingSquad = teamSquadRepository.findByTeamIdAndMatchId(teamId, matchId)
                .orElseThrow(() -> new AppException(MessageFormat.format("Team Squad with matchId {0} and teamId {1} doesn't exist", teamId, matchId)));

        List<PlayerSquadPosition> playerSquadPositions = teamStartingSquad.getSquad().getPlayerSquadPositions();

        playerSquadPositions.removeIf(playerSquadPosition -> players.contains(playerSquadPosition.getPlayer()));

        teamStartingSquad.getSquad().setPlayerSquadPositions(playerSquadPositions);

        teamSquadRepository.save(teamStartingSquad);

        matchSquadRepository.save(matchSquad);

        return players.size();
    }
}

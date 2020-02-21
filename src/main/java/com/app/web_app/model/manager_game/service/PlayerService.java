package com.app.web_app.model.manager_game.service;

import com.app.web_app.exceptions.AppException;
import com.app.web_app.model.manager_game.GoalDetail;
import com.app.web_app.model.manager_game.Player;
import com.app.web_app.model.manager_game.dto.PlayerDto;
import com.app.web_app.model.manager_game.dto.PlayersNumberDto;
import com.app.web_app.model.manager_game.dto.TeamDto;
import com.app.web_app.model.manager_game.mapper.ManagerMapper;
import com.app.web_app.model.manager_game.repository.PlayerRepository;
import com.app.web_app.model.manager_game.repository.TeamRepository;
import com.app.web_app.repository.GoalDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final ManagerMapper managerMapper;
    private final GoalDetailRepository goalDetailRepository;
    private final TeamRepository teamRepository;
    private final Random random = new Random();

    public List<PlayerDto> getPlayersWithIds(Collection<Integer> ids) {
        return playerRepository.findPlayersByIdIn(ids).stream().map(managerMapper::mapPlayerToDto).collect(Collectors.toList());
    }


    public Map<Integer, TeamDto> getTeamsForPlayers(Collection<PlayerDto> players) {

        if (players == null) {
            throw new AppException("Players list is null");
        }

        return players.stream()
                .distinct()
                .collect(Collectors.toMap(
                        PlayerDto::getId,
                        player -> managerMapper
                                .mapTeamToTeamDto(teamRepository.findById(player.getTeamId())
                                        .orElseThrow(() -> new AppException("Team is null")))
                ));

    }


    public Map<PlayerDto, Integer> getAssistsForPlayersInLeagueById(Integer leagueId) {

        if (leagueId == null) {
            throw new AppException("League id is null");
        }

        return createMapAssistsPerUser(goalDetailRepository.findAllByMatchStatisticMatchLeagueId(leagueId));
    }


    public Map<PlayerDto, Integer> getGoalsForPlayersInLeagueById(Integer leagueId) {

        if (leagueId == null) {
            throw new AppException("League id is null");
        }

        return createMapGoalsPerUser(goalDetailRepository.findAllByMatchStatisticMatchLeagueId(leagueId));
    }


    public List<PlayerDto> getPlayersForByTeamId(Integer teamId) {

        return playerRepository.findPlayersByTeamId(teamId)
                .stream()
                .map(managerMapper::mapPlayerToDto)
                .collect(Collectors.toList());
    }

    public void savePlayerNumbers(PlayersNumberDto playersNumberDto) {

        playersNumberDto
                .getPlayersNumber()
                .forEach((playerId, number) -> {
                    playerRepository.findById(playerId)
                            .ifPresent((player) -> player.setNumber(number));
                });
    }

    public Long clearPlayerNumbers(Collection<Integer> playerIds) {

        if (playerIds == null) {
            throw new AppException("CurrentPlayerNumbers is null");
        }

        AtomicLong counter = new AtomicLong(0);

        List<Player> players = playerRepository.findPlayersByIdIn(playerIds)
                .stream()
                .peek(player -> {
                    if (player.getNumber() != null) {
                        counter.incrementAndGet();
                    }
                })
                .peek(player -> player.setNumber(null))
                .collect(Collectors.toList());

        return counter.get();

    }

    public Long randomizePlayerNumbers(List<Integer> playerIdsToAdd, List<Integer> availableNumbers) {

        if (playerIdsToAdd == null) {
            throw new AppException("PlayerIdsToAdd list is null");
        }

        if (availableNumbers == null) {
            throw new AppException("AvailableNumbers list is null");
        }

        List<Player> players = playerRepository.findPlayersByIdIn(playerIdsToAdd);

        players.forEach(player -> {
            Integer randomizedNumber = availableNumbers.get(random.nextInt(availableNumbers.size()));
            player.setNumber(randomizedNumber);
            availableNumbers.remove((Integer) randomizedNumber);

        });

        return (long) players.size();
    }

    public Optional<PlayerDto> getPlayerById(Integer id) {
        if (id == null) {
            throw new AppException("Player id is null");
        }

        return playerRepository.findById(id)
                .map(managerMapper::mapPlayerToDto);
    }

    public Map<PlayerDto, Integer> getGoalsForPlayerForTeamById(Integer teamId) {

        if (teamId == null) {
            throw new AppException("Team id is null");
        }

        return createMapGoalsPerUser(goalDetailRepository.findAllByTeamId(teamId));
    }

    public Map<PlayerDto, Integer> getAssistsForPlayerForTeamById(Integer teamId) {

        if (teamId == null) {
            throw new AppException("Team id is null");
        }

        return createMapAssistsPerUser(goalDetailRepository.findAllByTeamId(teamId));
    }


    private Map<PlayerDto, Integer> createMapGoalsPerUser(List<GoalDetail> goalDetails) {

        if (goalDetails == null) {
            throw new AppException("Goal Details is null");
        }

        return goalDetails.stream()
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.groupingBy(
                                        goalDetail -> managerMapper.mapPlayerToDto(goalDetail.getScorer()),
                                        Collectors.summingInt(e -> 1)),
                                map -> map
                                        .entrySet()
                                        .stream()
                                        .sorted(
                                                Collections.reverseOrder(Map.Entry.comparingByValue()))
                                        .collect(Collectors.toMap(
                                                Map.Entry::getKey,
                                                Map.Entry::getValue,
                                                (oldVal, newVal) -> oldVal,
                                                LinkedHashMap::new))
                        )
                );
    }

    private Map<PlayerDto, Integer> createMapAssistsPerUser(List<GoalDetail> goalDetails) {

        if (goalDetails == null) {
            throw new AppException("Goal details is null");
        }

        return goalDetails
                .stream()
                .map(GoalDetail::getAssistant)
                .collect(Collectors.collectingAndThen(Collectors.groupingBy(
                        managerMapper::mapPlayerToDto,
                        Collectors.summingInt(e -> 1)),
                        map -> map
                                .entrySet()
                                .stream()
                                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (oldVal, newVal) -> oldVal,
                                        LinkedHashMap::new
                                ))
                ));

    }
}

package com.app.web_app.model.manager_game.service;

import com.app.web_app.exceptions.AppException;
import com.app.web_app.model.manager_game.Player;
import com.app.web_app.model.manager_game.dto.PlayerStatsDto;
import com.app.web_app.model.manager_game.mapper.ManagerMapper;
import com.app.web_app.model.manager_game.repository.PlayerStatsRepository;
import com.app.web_app.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlayerStatsService {

    @Autowired
    private PlayerStatsRepository playerStatsRepository;

    @Autowired
    private LeagueRepository leagueRepository;

    @Autowired
    private ManagerMapper managerMapper;

    public List<PlayerStatsDto> getPlayerStatsForLeague(Integer leagueId) {

        return playerStatsRepository.findAllByIdIn(
                leagueRepository.findById(leagueId)
                        .map(league ->
                                league.getTeams()
                                        .stream()
                                        .flatMap(team -> team.getPlayers().stream())
                                        .map(Player::getId)
                                        .collect(Collectors.toList())
                        )
                        .orElseThrow(() -> new AppException("League with id: " + leagueId + " not found")))
                .stream()
                .map(managerMapper::mapPlayerStatsToDto)
                .collect(Collectors.toList());
    }
}

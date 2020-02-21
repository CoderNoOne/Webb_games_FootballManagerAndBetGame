package com.app.web_app.model.manager_game.service;

import com.app.web_app.model.manager_game.dto.LeagueDto;
import com.app.web_app.model.manager_game.mapper.ManagerMapper;
import com.app.web_app.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class LeagueService {

    @Autowired
    private ManagerMapper managerMapper;

    @Autowired
    private LeagueRepository leagueRepository;

    public Set<LeagueDto> getAllLeaguesByActiveGameWithFetchedTeams() {
        return leagueRepository.findAllFetchTeams(true)
                .stream()
                .map(managerMapper::mapLeagueToDto)
                .collect(Collectors.toSet());
    }

    public Optional<LeagueDto> getActiveLeagueByUsername(String username) {
        return leagueRepository
                .findByGameActiveAndGameUsersUsername(true, username)
                .map(managerMapper::mapLeagueToDto);
    }
}

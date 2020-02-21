package com.app.web_app.model.manager_game.service;

import com.app.web_app.exceptions.AppException;
import com.app.web_app.model.manager_game.Game;
import com.app.web_app.model.manager_game.Team;
import com.app.web_app.model.manager_game.dto.TeamDto;
import com.app.web_app.model.manager_game.mapper.ManagerMapper;
import com.app.web_app.model.manager_game.repository.TeamRepository;
import com.app.web_app.model.user.User;
import com.app.web_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private ManagerMapper managerMapper;

    @Autowired
    private UserRepository userRepository;

    public Integer saveTeam(TeamDto teamDto) {

        return teamRepository.save(managerMapper.mapTeamDtoToTeamEntity(teamDto)).getId();
    }

    public Optional<TeamDto> getTeamByUsername(String username) {
        return teamRepository.findByUsername(username).map(managerMapper::mapTeamToTeamDto);
    }

    public Optional<TeamDto> getTeamById(Integer id) {
        return teamRepository.findById(id).map(managerMapper::mapTeamToTeamDto);
    }

    public void setUserForTeamByTeamId(Integer id, String username) {

        Team team = teamRepository.findById(id).orElseThrow();
        User user = userRepository.findByUsername(username).orElseThrow();

        Game game = team.getLeague().getGame();
        game.getUsers().add(user);

        user.getGames().add(game);

        team.setUser(user);
    }

    public Set<TeamDto> getTeamsWithAssociatedUsers(String[] teamNames, Integer id) {

        if (teamNames == null) {
            throw new AppException("teamNames is null");
        }

        return teamRepository.findTeamsByNameInAndLeagueIdAndUserNotNull(teamNames, id)
                .stream()
                .map(managerMapper::mapTeamToTeamDto)
                .collect(Collectors.toSet());
    }

    public boolean doUserHaveActiveTeam(String username) {
        return teamRepository.existsByUserUsernameAndLeagueGameActive(username, true);
    }

    public Optional<TeamDto> getActiveTeamByUsername(String username) {
        return teamRepository.findByUserUsernameAndLeagueGameActive(username, true)
                .map(managerMapper::mapTeamToTeamDto);
    }

    public List<TeamDto> getTeamsByLeague(Integer leagueId) {
        return teamRepository.findAllByLeagueId(leagueId)
                .stream()
                .map(managerMapper::mapTeamToTeamDto)
                .collect(Collectors.toList());
    }

    public Map<String, String> getShirtColors(TeamDto team) {

        if (team == null || team.getShirtColors() == null) {
            throw new AppException("Team or team shirt colors is null");
        }

        var shirtColors = team.getShirtColors().split("[-]");

        return Map.of("first", shirtColors[0], "second", shirtColors[1]);
    }

    public Set<TeamDto> getTeamsWithNotAssociatedUsers(String[] teamNames, Integer id) {

        if (teamNames == null) {
            throw new AppException("teamNames is null");
        }

        return teamRepository.findTeamsByNameInAndLeagueIdAndUserNull(teamNames, id)
                .stream()
                .map(managerMapper::mapTeamToTeamDto)
                .collect(Collectors.toSet());
    }
}

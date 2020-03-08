package org.example.fm;

import lombok.RequiredArgsConstructor;
import org.example.core.UserRepository;
import org.example.core.entity.User;
import org.example.exceptions.AppException;
import org.example.fm.entity.Country;
import org.example.fm.entity.Game;
import org.example.fm.entity.League;
import org.example.fm.entity.Team;
import org.example.model.fm.TeamDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final LeagueRepository leagueRepository;

    public Integer saveTeam(TeamDto teamDto) {

        if (teamDto == null) {
            throw new AppException("TeamDto is null");
        }

        Team team = ManagerMapper.mapTeamDtoToTeam2(teamDto);
        setCountryForTeam(team);
        setUserForTeam(team);
        setLeagueForTeam(team);

        return teamRepository.save(team).getId();
    }

    private void setLeagueForTeam(Team team) {

        if (team == null) {
            throw new AppException("Team is null");
        }

        if (team.getLeague() == null || team.getLeague().getId() == null) {
            throw new AppException("League or league id null");
        }

        League leagueFromDB = leagueRepository.findById(team.getLeague().getId())
                .orElseThrow(() -> new AppException(MessageFormat.format("League with id: {0} doesn't exist", team.getLeague().getId())));

        team.setLeague(leagueFromDB);
    }

    private void setUserForTeam(Team team) {

        if (team == null) {
            throw new AppException("Team is null");
        }

        if (team.getUser() == null || team.getUser().getUsername() == null) {
            throw new AppException("User or username is null");
        }

        User user = userRepository.findByUsername(team.getUser().getUsername())
                .orElseThrow(() -> new AppException(MessageFormat.format("No user for username: {0}", team.getUser().getUsername())));

        team.setUser(user);
    }

    private void setCountryForTeam(Team team) {

        if (team == null) {
            throw new AppException("Team is null");
        }

        if (team.getCountry() == null || team.getCountry().getName() == null) {
            throw new AppException("Country or country name is null");
        }

        Optional<Country> countryOptional = countryRepository.findByName(team.getCountry().getName());

        countryOptional.ifPresentOrElse(
                team::setCountry,
                () -> {
                    Country savedCountry = countryRepository.save(Country.builder().name(team.getCountry().getName()).build());
                    team.setCountry(savedCountry);
                }
        );
    }

    public Optional<TeamDto> getTeamByUsername(String username) {
        return teamRepository.findByUsername(username).map(ManagerMapper::mapTeamToTeamDto);
    }

    public Optional<TeamDto> getTeamById(Integer id) {
        return teamRepository.findById(id).map(ManagerMapper::mapTeamToTeamDto);
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
                .map(ManagerMapper::mapTeamToTeamDto)
                .collect(Collectors.toSet());
    }

    public boolean doUserHaveActiveTeam(String username) {
        return teamRepository.existsByUserUsernameAndLeagueGameActive(username, true);
    }

    public Optional<TeamDto> getActiveTeamByUsername(String username) {
        return teamRepository.findByUserUsernameAndLeagueGameActive(username, true)
                .map(ManagerMapper::mapTeamToTeamDto);
    }

    public List<TeamDto> getTeamsByLeague(Integer leagueId) {
        return teamRepository.findAllByLeagueId(leagueId)
                .stream()
                .map(ManagerMapper::mapTeamToTeamDto)
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
                .map(ManagerMapper::mapTeamToTeamDto)
                .collect(Collectors.toSet());
    }
}

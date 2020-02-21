package com.app.web_app.model.manager_game.repository;

import com.app.web_app.model.manager_game.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public interface TeamRepository extends JpaRepository<Team, Integer> {

    @Query("select t from Team as t where t.user.username = :username")
    Optional<Team> findByUsername(@Param("username") String username);

    Optional<Team> findByUserUsernameAndLeagueGameActive(String username, boolean isActive);

    Optional<Team> findByName(String name);

    boolean existsByUserUsernameAndLeagueGameActive(String username, boolean isActive);


    default List<Team> findByGameId(Integer gameId) {
        return findAll()
                .stream()
                .filter(team -> team
                        .getLeague()
                        .getGame()
                        .getId().equals(gameId))
                .collect(Collectors.toList());
    }

    List<Team> findAllByLeagueId(Integer id);

    Set<Team> findTeamsByNameInAndLeagueIdAndUserNotNull(String[] teamNames, Integer id);

    Set<Team> findTeamsByNameInAndLeagueIdAndUserNull(String[] teamNames, Integer id);
}

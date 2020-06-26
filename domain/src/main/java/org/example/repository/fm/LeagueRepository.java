package org.example.repository.fm;

import org.example.entity.fm.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface LeagueRepository extends JpaRepository<League, Integer> {

    @Query(value = "select l from League as l join fetch l.teams where l.game.active = :activeGame")
    Set<League> findAllFetchTeams(@Param("activeGame") Boolean activeGame);

    Optional<League> findByGameActiveAndGameUsersUsername(boolean isActive, String username);
}

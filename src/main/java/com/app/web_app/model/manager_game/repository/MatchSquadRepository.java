package com.app.web_app.model.manager_game.repository;

import com.app.web_app.model.manager_game.MatchSquad;
import com.app.web_app.model.manager_game.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface MatchSquadRepository extends JpaRepository<MatchSquad, Integer> {


    @Query(value = "select m from MatchSquad m  join fetch m.players where m.teamId = :teamId and m.matchId = :matchId")
    Optional<MatchSquad> findByTeamIdAndMatchIdFetchedPlayers(Integer teamId, Integer matchId);

    default Map<String, Player> findPlayerGroupedByPosition(Integer teamId, Integer matchId) {

        return findByTeamIdAndMatchIdFetchedPlayers(teamId, matchId)
                .orElseThrow()
                .getPlayers();

    }

    Optional<MatchSquad> findByTeamIdAndMatchId(Integer teamId, Integer matchId);

    void deleteAllByMatchIdIn(List<Integer> matchIds);

    List<MatchSquad> findByMatchId(Integer id);
}

package com.app.web_app.model.manager_game.repository;

import com.app.web_app.model.manager_game.MatchSquad;
import com.app.web_app.model.manager_game.Player;
import com.app.web_app.model.manager_game.enums.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public interface MatchSquadRepository extends JpaRepository<MatchSquad, Integer> {


    @Query(value = "select m from MatchSquad m  join fetch m.players where m.team.id = :teamId and m.match.id = :matchId")
    Optional<MatchSquad> findByTeamIdAndMatchIdFetchedPlayers(Integer teamId, Integer matchId);

    default Map<String, Player> findPlayerGroupedByPosition(Integer teamId, Integer matchId) {

        return findByTeamIdAndMatchIdFetchedPlayers(teamId, matchId)
                .orElseThrow()
                .getPlayers();

    }

    default Map<String, Player> findFirstElevenPlayers(Integer teamId, Integer matchId) {

        MatchSquad matchSquad = findByTeamIdAndMatchIdFetchedPlayers(teamId, matchId)
                .orElseThrow();
        return matchSquad.getPlayers().entrySet()
                .stream()
                .filter(e -> Formation.fromFormationNumber(matchSquad.getFormationType()).getPositions().contains(e.getKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    Optional<MatchSquad> findByMatchIdAndTeamIdNot(Integer matchId, Integer teamId);

    void deleteAllByMatchIdIn(List<Integer> matchIds);

    List<MatchSquad> findByMatchId(Integer id);

    Optional<MatchSquad> findByTeamIdAndMatchId(Integer teamId, Integer matchId);
}

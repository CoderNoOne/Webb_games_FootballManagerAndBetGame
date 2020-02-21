package com.app.web_app.model.manager_game.repository;

import com.app.web_app.model.manager_game.PlayerPosition;
import com.app.web_app.model.manager_game.TeamSquad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamSquadRepository extends JpaRepository<TeamSquad, Integer> {


    Optional<TeamSquad> findByTeamIdAndSquadId(Integer teamId, Integer squadId);

    Optional<TeamSquad> findByTeamIdAndMatchId(Integer teamId, Integer matchId);

    @Query("select t from TeamSquad t where t.team.id <> :teamId and t.match.id = :matchId")
    Optional<TeamSquad> findOpponentSquadForMatch(Integer teamId, Integer matchId);

    Optional<TeamSquad> findByMatchId(Integer matchId);

    List<TeamSquad> findByMatchIdAndTeamIdIn(Integer matchId, List<Integer> teamIds);

    @Query("select t.squad.playerPositions from TeamSquad t where t.match.id = :matchId and t.team.id in :teamsIds")
    List<PlayerPosition> findPlayersPositionsForMatchIdAndTeamIdsIn(Integer matchId, List<Integer> teamsIds);

    @Query("select t.squad.playerPositions from TeamSquad  t where t.match.id = :matchId and t.team.id = :teamId")
    List<PlayerPosition> findPlayersPositionsForMatchIdAndTeamId(Integer matchId, Integer teamId);

    Optional<TeamSquad> findByTeamId(Integer id);
}

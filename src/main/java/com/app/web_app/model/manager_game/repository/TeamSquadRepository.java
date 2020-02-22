package com.app.web_app.model.manager_game.repository;

import com.app.web_app.model.manager_game.PlayerSquadPosition;
import com.app.web_app.model.manager_game.TeamStartingSquad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamSquadRepository extends JpaRepository<TeamStartingSquad, Integer> {


    Optional<TeamStartingSquad> findByTeamIdAndSquadId(Integer teamId, Integer squadId);

    Optional<TeamStartingSquad> findByTeamIdAndMatchId(Integer teamId, Integer matchId);

    @Query("select t from TeamStartingSquad t where t.team.id <> :teamId and t.match.id = :matchId")
    Optional<TeamStartingSquad> findOpponentSquadForMatch(Integer teamId, Integer matchId);

    Optional<TeamStartingSquad> findByMatchId(Integer matchId);

    List<TeamStartingSquad> findByMatchIdAndTeamIdIn(Integer matchId, List<Integer> teamIds);

    @Query("select t.squad.playerSquadPositions from TeamStartingSquad t where t.match.id = :matchId and t.team.id in :teamsIds")
    List<PlayerSquadPosition> findPlayersPositionsForMatchIdAndTeamIdsIn(Integer matchId, List<Integer> teamsIds);

    @Query("select t.squad.playerSquadPositions from TeamStartingSquad  t where t.match.id = :matchId and t.team.id = :teamId")
    List<PlayerSquadPosition> findPlayersPositionsForMatchIdAndTeamId(Integer matchId, Integer teamId);

    Optional<TeamStartingSquad> findByTeamId(Integer id);
}

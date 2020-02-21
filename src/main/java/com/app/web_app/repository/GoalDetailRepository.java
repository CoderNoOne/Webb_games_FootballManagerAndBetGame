package com.app.web_app.repository;

import com.app.web_app.model.manager_game.GoalDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalDetailRepository extends JpaRepository<GoalDetail, Integer> {

    List<GoalDetail> findAllByMatchStatisticId(Integer matchStatisticId);

    List<GoalDetail> findAllByMatchStatisticMatchLeagueId(Integer leagueId);

    @Query(value = "select g from GoalDetail g" +
            " where g.assistant.team.id = :teamId")
    List<GoalDetail> findAllByTeamId(Integer teamId);

    @Query(value = "select g from GoalDetail g where g.matchStatistic.match.id = :matchId")
    List<GoalDetail> findAllByMatchId(Integer matchId);
}

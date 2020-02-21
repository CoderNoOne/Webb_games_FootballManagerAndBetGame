package com.app.web_app.model.manager_game.repository;

import com.app.web_app.model.manager_game.PlayerStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerStatsRepository extends JpaRepository<PlayerStats, Integer> {

    List<PlayerStats> findAllByIdIn(List<Integer> playerIds);

}

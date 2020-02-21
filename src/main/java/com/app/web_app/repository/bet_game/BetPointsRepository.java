package com.app.web_app.repository.bet_game;

import com.app.web_app.model.bet_game.BetPoints;
import com.app.web_app.model.bet_game.BetPointsIdentityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BetPointsRepository extends JpaRepository <BetPoints, BetPointsIdentityKey> {

    @Query(value = "select * from points as p where p.league = ?1 ",nativeQuery = true)
    List<BetPoints> findAllByLeague(String league);
}

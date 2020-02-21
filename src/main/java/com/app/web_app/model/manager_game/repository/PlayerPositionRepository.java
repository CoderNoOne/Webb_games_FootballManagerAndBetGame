package com.app.web_app.model.manager_game.repository;

import com.app.web_app.model.manager_game.PlayerPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerPositionRepository extends JpaRepository<PlayerPosition, Integer> {

    @Query(value = "delete from PlayerPosition p where p.id in :playerPositionsIds")
    @Modifying
    void deleteAllCustom(List<Integer> playerPositionsIds);
}

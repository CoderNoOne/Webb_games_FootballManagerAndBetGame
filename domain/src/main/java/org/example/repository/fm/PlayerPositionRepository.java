package org.example.repository.fm;

import org.example.entity.fm.entity.PlayerSquadPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerPositionRepository extends JpaRepository<PlayerSquadPosition, Integer> {

    @Query(value = "delete from PlayerSquadPosition p where p.id in :playerPositionsIds")
    @Modifying
    void deleteAllCustom(List<Integer> playerPositionsIds);
}

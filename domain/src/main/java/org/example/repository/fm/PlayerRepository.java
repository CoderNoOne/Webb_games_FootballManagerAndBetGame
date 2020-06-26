package org.example.repository.fm;

import org.example.entity.fm.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    List<Player> findPlayersByIdIn(Collection<Integer> ids);

    List<Player> findPlayersByTeamId(Integer teamId);
}

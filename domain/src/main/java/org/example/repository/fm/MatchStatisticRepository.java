package org.example.repository.fm;

import org.example.entity.fm.entity.MatchStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchStatisticRepository extends JpaRepository<MatchStatistic, Integer> {

    @Query(value = "select m from MatchStatistic m where m.match.id = :matchId")
    Optional<MatchStatistic> findByMatchId(Integer matchId);
}

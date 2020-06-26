package org.example.repository.fm;


import org.example.entity.fm.entity.Match;
import org.example.entity.fm.enums.FmMatchStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {

    List<Match> findAllByLeagueId(Integer id);

    List<Match> findAllByScoreNotNullAndLeagueId(Integer leagueId);

    List<Match> findAllByScoreNullAndLeagueId(Integer leagueId);

    List<Match> findAllByScoreNull();

    @Query("select m from Match m where m.homeTeam.id = :teamId or m.awayTeam.id = :teamId order by m.dateTime asc")
    List<Match> findAllByAwayTeamIdOrHomeTeamId(Integer teamId, Pageable pageable);

    @Query("select m from Match m where m.homeTeam.id = :teamId or m.awayTeam.id = :teamId and m.id = :matchId")
    Optional<Match> findByMatchIdAndTeamId(@Param("matchId") Integer matchId, @Param("teamId") Integer teamId);

    @Query(nativeQuery = true, value = "select m.away_id from matches m where m.id = :matchId union select m.home_id from matches m where m.id = :matchId")
    List<Integer> findTeamIdsForMatch(Integer matchId);

    List<Match> findAllByStatusIn(List<FmMatchStatus> statuses);

}

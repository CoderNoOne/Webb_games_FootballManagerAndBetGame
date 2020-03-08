package org.example.fm;

import org.example.fm.entity.Squad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SquadRepository extends JpaRepository<Squad, Integer> {

    Optional<Squad> findByName(String name);

    Optional<Squad> findByNameAndTeamId(String name, Integer teamId);

    @Query("select s from Squad as s where s.team.id = :id")
    List<Squad> findAllByTeamId(@Param("id") Integer id);

    @Modifying
    @Query(value = "delete from Squad s where s.id = :id ")
    Integer deleteCustom(Integer id);
}

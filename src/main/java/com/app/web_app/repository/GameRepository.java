package com.app.web_app.repository;

import com.app.web_app.model.manager_game.Game;
import com.app.web_app.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    @Query(value = "select g.users from Game g where g.id = :id")
     Set<User> getUsersForGame(Integer id);

    List<Game> findAllByActive(Boolean isActive);

    @Query(value = "select g from Game as g left join fetch g.leagues")
    List<Game> findAllFetch();


}

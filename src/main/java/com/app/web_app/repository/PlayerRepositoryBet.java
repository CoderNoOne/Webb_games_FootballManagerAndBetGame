package com.app.web_app.repository;

import com.app.web_app.model.manager_game.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepositoryBet extends JpaRepository<Player, Integer> {

}

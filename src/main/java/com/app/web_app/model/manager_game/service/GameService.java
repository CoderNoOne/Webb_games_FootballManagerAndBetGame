package com.app.web_app.model.manager_game.service;

import com.app.web_app.model.manager_game.Game;
import com.app.web_app.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public List<Game> findAllActive() {
        return gameRepository.findAllByActive(true);
    }
}

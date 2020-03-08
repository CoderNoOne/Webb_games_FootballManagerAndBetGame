package org.example.fm;

import lombok.RequiredArgsConstructor;
import org.example.fm.entity.Game;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GameService {

    private final GameRepository gameRepository;

    public List<Game> findAllActive() {
        return gameRepository.findAllByActive(true);
    }
}

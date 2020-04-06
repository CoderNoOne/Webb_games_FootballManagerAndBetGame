package org.example.fm;

import lombok.RequiredArgsConstructor;
import org.example.fm.entity.Game;
import org.example.model.core.GameDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GameService {

    private final GameRepository gameRepository;

    public List<GameDto> findAllActive() {
        return gameRepository.findAllByActive(true)
                .stream()
                .map(ManagerMapper::mapGameToDto)
                .collect(Collectors.toList());
    }
}

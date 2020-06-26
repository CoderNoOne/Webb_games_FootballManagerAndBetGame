package org.example.fm;

import lombok.RequiredArgsConstructor;

import org.example.model.core.GameDto;
import org.example.repository.fm.GameRepository;
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

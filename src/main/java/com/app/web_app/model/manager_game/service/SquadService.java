package com.app.web_app.model.manager_game.service;

import com.app.web_app.model.manager_game.PlayerSquadPosition;
import com.app.web_app.model.manager_game.dto.SquadDto;
import com.app.web_app.model.manager_game.mapper.ManagerMapper;
import com.app.web_app.model.manager_game.repository.PlayerPositionRepository;
import com.app.web_app.model.manager_game.repository.SquadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SquadService {

    private final SquadRepository squadRepository;
    private final ManagerMapper managerMapper;
    private final PlayerPositionRepository playerPositionRepository;

    public Optional<SquadDto> findSquadByNameAndTeamId(String name, Integer teamId) {

        return squadRepository.findByNameAndTeamId(name, teamId)
                .map(managerMapper::mapSquadToSquadDto);

    }

    public boolean saveSquad(SquadDto squadDto) {

        AtomicBoolean isOverridden = new AtomicBoolean(false);
        squadRepository.findByNameAndTeamId(squadDto.getName(), squadDto.getTeamDto().getId())
                .ifPresent(
                        squad -> {
                            playerPositionRepository.deleteAllCustom(squad.getPlayerSquadPositions().stream().map(PlayerSquadPosition::getId).collect(Collectors.toList()));
                            squadRepository.deleteCustom(squad.getId());
                            isOverridden.set(true);
                        }
                );

        squadRepository.save(managerMapper.createNewSquad(squadDto));

        return isOverridden.get();

    }

    public List<SquadDto> getSquadsByTeamId(Integer id) {
        return squadRepository.findAllByTeamId(id).stream()
                .map(managerMapper::mapSquadToSquadDto)
                .collect(Collectors.toList());
    }
}

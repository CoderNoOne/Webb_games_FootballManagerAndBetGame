package org.example.fm;

import lombok.RequiredArgsConstructor;
import org.example.core.exceptions.AppException;
import org.example.fm.entity.PlayerSquadPosition;
import org.example.fm.entity.Squad;
import org.example.fm.entity.Team;
import org.example.model.fm.SquadDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SquadService {

    private final SquadRepository squadRepository;
    private final PlayerPositionRepository playerPositionRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public Optional<SquadDto> findSquadByNameAndTeamId(String name, Integer teamId) {

        return squadRepository.findByNameAndTeamId(name, teamId)
                .map(ManagerMapper::mapSquadToSquadDto);

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

        Squad squad = ManagerMapper.mapSquadDtoToSquad(squadDto);

        setTeamForSquad(squad);
        setPlayersForSquad(squad);

        squadRepository.save(squad);

        return isOverridden.get();

    }

    private void setPlayersForSquad(Squad squad) {
        if (squad == null) {
            throw new AppException("Squad is null");
        }

        if (squad.getPlayerSquadPositions() == null) {
            throw new AppException("PlayersPosition is null");
        }

        if (squad.getPlayerSquadPositions().stream().anyMatch(playerSquadPosition -> playerSquadPosition.getPlayer() == null)) {
            throw new AppException("At least one player is null");
        }

        List<PlayerSquadPosition> playerSquadPositionList = squad.getPlayerSquadPositions()
                .stream()
                .peek(playerSquadPosition -> {
                    playerSquadPosition.setPlayer(
                            playerRepository.findById(playerSquadPosition.getPlayer().getId())
                                    .orElseThrow(() -> new AppException(MessageFormat.format("Player with id {0} doesn't exist",
                                            playerSquadPosition.getPlayer().getId()))));
                })
                .collect(Collectors.toList());

        squad.setPlayerSquadPositions(playerSquadPositionList);
    }

    private void setTeamForSquad(Squad squad) {
        if (squad == null) {
            throw new AppException("Squad is null");
        }

        if (squad.getTeam() == null || squad.getTeam().getId() == null) {
            throw new AppException("Team or id is null");
        }

        Team team = teamRepository.findById(squad.getTeam().getId())
                .orElseThrow(
                        () ->
                                new AppException(
                                        MessageFormat.format("Team with id: {0} doesn't exist", squad.getTeam().getId())));


        squad.setTeam(team);
    }

    public List<SquadDto> getSquadsByTeamId(Integer id) {
        return squadRepository.findAllByTeamId(id).stream()
                .map(ManagerMapper::mapSquadToSquadDto)
                .collect(Collectors.toList());
    }
}

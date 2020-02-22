package com.app.web_app.model.manager_game.service;

import com.app.web_app.model.manager_game.TeamStartingSquad;
import com.app.web_app.model.manager_game.dto.TeamDto;
import com.app.web_app.model.manager_game.dto.TeamSquadDto;
import com.app.web_app.model.manager_game.mapper.ManagerMapper;
import com.app.web_app.model.manager_game.repository.TeamSquadRepository;
import com.app.web_app.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamSquadService {

    private final TeamSquadRepository teamSquadRepository;
    private final MatchRepository matchRepository;
    private final ManagerMapper managerMapper;

    public Optional<TeamSquadDto> getByMatchIdAndTeamId(Integer teamId, Integer matchId) {

        return teamSquadRepository.findByTeamIdAndMatchId(teamId, matchId)
                .map(managerMapper::mapTeamSquadToDto);
    }

    public TeamSquadDto save(TeamSquadDto teamSquadDto) {

        AtomicReference<TeamStartingSquad> teamSquadReference = new AtomicReference<>();

        teamSquadRepository
                .findByTeamId(teamSquadDto.getTeamDto().getId())
                .ifPresentOrElse(teamSquad -> {
                            teamSquad.setSquad(managerMapper.mapSquadDtoToSquad(teamSquadDto.getSquadDto()));
                            teamSquadReference.set(teamSquad);
                        },
                        () -> teamSquadReference.set((teamSquadRepository
                                .save(managerMapper.mapTeamSquadDtoToTeamSquad(teamSquadDto)))));


        return managerMapper.mapTeamSquadToDto(teamSquadReference.get());
    }

    public Optional<TeamSquadDto> getOpponentSquadForMatch(TeamDto teamDto, Integer matchId) {

        return teamSquadRepository.findOpponentSquadForMatch(teamDto.getId(), matchId)
                .map(managerMapper::mapTeamSquadToDto);

    }

    public List<TeamSquadDto> getStartingSquadsForMatch(Integer matchId) {

        List<Integer> teamIds = matchRepository.findTeamIdsForMatch(matchId);

        return
                teamSquadRepository
                        .findByMatchIdAndTeamIdIn(matchId, teamIds)
                        .stream()
                        .map(managerMapper::mapTeamSquadToDto)
                        .collect(Collectors.toList());
    }
}

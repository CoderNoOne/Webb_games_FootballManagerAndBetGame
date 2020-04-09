package org.example.fm;

import lombok.RequiredArgsConstructor;
import org.example.core.exceptions.AppException;
import org.example.fm.entity.Match;
import org.example.fm.entity.MatchSquad;
import org.example.fm.entity.Player;
import org.example.fm.entity.Team;
import org.example.model.fm.MatchSquadDto;
import org.example.model.fm.PlayerDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchSquadService {

    private final MatchSquadRepository matchSquadRepository;
    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    public Optional<MatchSquadDto> save(MatchSquadDto matchSquadDto) {

        if (Objects.isNull(matchSquadDto)) {
            throw new AppException("MatchSquadDto is null");
        }

        if (Objects.isNull(matchSquadDto.getTeamDto()) || Objects.isNull(matchSquadDto.getTeamDto().getId())) {
            throw new AppException("TeamDto or team id is null");
        }

        if (Objects.isNull(matchSquadDto.getMatchId())) {
            throw new AppException("Match id is null");
        }

        if (Objects.isNull(matchSquadDto.getFormation()) || Objects.isNull(matchSquadDto.getFormation().getNumber())) {
            throw new AppException("Formation or formation number is null");
        }

        if (Objects.isNull(matchSquadDto.getPlayers()) || mapContainsNullKeyOrValue(matchSquadDto.getPlayers())) {
            throw new AppException("Players map or entry key/value is null");
        }

        if (Objects.isNull(matchSquadDto.getSubstitutions()) || mapContainsNullKeyOrValue(matchSquadDto.getSubstitutions())) {
            throw new AppException("Substitutions map or entry key/value is null");
        }

        AtomicReference<Optional<MatchSquadDto>> result = new AtomicReference<>(Optional.empty());

        matchSquadRepository.findByTeamIdAndMatchId(matchSquadDto.getTeamDto().getId(), matchSquadDto.getMatchId())
                .ifPresentOrElse((value) -> {
                            value.setPlayers(Stream.of(matchSquadDto.getPlayers(), matchSquadDto.getSubstitutions())
                                    .flatMap(e -> e.entrySet().stream())
                                    .collect(Collectors.toMap(
                                            Map.Entry::getKey,
                                            e -> ManagerMapper.mapPlayerDtoToPlayer2(e.getValue())
                                    )));
                            value.setFormationType(matchSquadDto.getFormation().getNumber());
                            value.setSubstitutionsAvailable(matchSquadDto.getSubstitutionsNumberAvailable());
                            result.set(Optional.of(value).map(ManagerMapper::mapMatchSquadToDto));
                        },
                        () -> {
                            MatchSquad matchSquad = ManagerMapper.mapMatchSquadDtoToMatchSquad(matchSquadDto);
                            setMatch(matchSquad);
                            setTeam(matchSquad);
                            setPlayers(matchSquad);

                            MatchSquad savedValue = matchSquadRepository.save(matchSquad);
                            result.set(Optional.of(savedValue).map(ManagerMapper::mapMatchSquadToDto));
                        }
                );

        return result.get();

    }

    private boolean mapContainsNullKeyOrValue(Map<String, PlayerDto> playersMap) {
        if (Objects.isNull(playersMap)) {
            throw new AppException("PlayersMap is null");
        }

        return playersMap.entrySet()
                .stream()
                .anyMatch(e -> Objects.isNull(e.getValue()) || Objects.isNull(e.getKey()));

    }

    private void setPlayers(MatchSquad matchSquad) {
        if (matchSquad == null) {
            throw new AppException("MatchSquad is null");
        }

        if (matchSquad.getPlayers() == null) {
            throw new AppException("Players are null");
        }

        Map<String, Player> playersWithPos = matchSquad.getPlayers().entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> playerRepository.findById(e.getValue().getId())
                                .orElseThrow(
                                        () -> new AppException(MessageFormat.format("Player with id {0} doesn't exist", e.getValue().getId())))));

        matchSquad.setPlayers(playersWithPos);
    }

    private void setTeam(MatchSquad matchSquad) {

        if (matchSquad == null) {
            throw new AppException("MatchSquad is null");
        }

        if (matchSquad.getTeam() == null || matchSquad.getTeam().getId() == null) {
            throw new AppException("Team or teamId is null");
        }

        Team team = teamRepository.findById(matchSquad.getTeam().getId())
                .orElseThrow(
                        () -> new AppException(
                                MessageFormat.format("No team with id: {0}", matchSquad.getTeam().getId())));

        matchSquad.setTeam(team);
    }

    private void setMatch(MatchSquad matchSquad) {
        if (matchSquad == null) {
            throw new AppException("MatchSquad is null");
        }

        if (matchSquad.getMatch() == null || matchSquad.getMatch().getId() == null) {
            throw new AppException("Match or matchId is null");
        }

        Match match = matchRepository.findById(matchSquad.getMatch().getId())
                .orElseThrow(
                        () -> new AppException(
                                MessageFormat.format("No match with id: {0}", matchSquad.getMatch().getId())));

        matchSquad.setMatch(match);
    }

    public Optional<MatchSquadDto> loadByTeamIdAndMatchId(Integer teamId, Integer matchId) {

        if (Objects.isNull(teamId)) {
            throw new AppException("Team id is null");
        }

        if (Objects.isNull(matchId)) {
            throw new AppException("Match id is null");
        }

        return matchSquadRepository.findByTeamIdAndMatchId(teamId, matchId)
                .map(ManagerMapper::mapMatchSquadToDto);
    }


    public Integer makeSubstitutions(Integer matchId, Integer teamId, Map<String, PlayerDto> subs) {

        if (Objects.isNull(matchId)) {
            throw new AppException("Match id is null");
        }

        if (Objects.isNull(teamId)) {
            throw new AppException("Team id is null");
        }

        if (Objects.isNull(subs)) {
            throw new AppException("Subs map is null");
        }

        MatchSquad matchSquad = matchSquadRepository.findByTeamIdAndMatchId(teamId, matchId)
                .orElseThrow(
                        () -> new AppException(MessageFormat.format("No match squad for (matchId {0}, {1})", matchId, teamId)));

        List<Player> players = playerRepository.findPlayersByIdIn(subs.values().stream().filter(Objects::nonNull).map(PlayerDto::getId).collect(Collectors.toList()));
        Map<String, Player> currentPlayers = matchSquad.getPlayers();

        Map<String, Player> subPlayersWithPosition = subs.entrySet()
                .stream()
                .filter(e -> e.getValue() != null)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> players.stream().filter(player -> player.getId().equals(e.getValue().getId())).findFirst().orElseThrow(() -> new AppException(MessageFormat.format("Player with id: {0} doesn't exist", e.getValue().getId())))
                ));

        currentPlayers.putAll(subPlayersWithPosition);

        Map<String, Player> playersToRemove = currentPlayers.entrySet()
                .stream()
                .filter(e -> !subPlayersWithPosition.containsValue(e.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        playersToRemove.forEach(currentPlayers::remove);

        matchSquad.setPlayers(currentPlayers);
        matchSquad.setSubstitutionsAvailable(matchSquad.getSubstitutionsAvailable() - 1);

        matchSquadRepository.save(matchSquad);

        return players.size();
    }

    public Optional<MatchSquadDto> getByTeamIdAndMatchId(Integer teamId, Integer matchId) {

        if (teamId == null) {
            throw new AppException("Team id is null");
        }

        if (matchId == null) {
            throw new AppException("Match id is null");
        }

        return matchSquadRepository.findByTeamIdAndMatchId(teamId, matchId)
                .map(ManagerMapper::mapMatchSquadToDto);
    }

    public Optional<MatchSquadDto> getOpponentSquadForMatch(Integer matchId, Integer teamId) {

        if (teamId == null) {
            throw new AppException("Team id is null");
        }

        if (matchId == null) {
            throw new AppException("Match id is null");
        }

        return matchSquadRepository.findByMatchIdAndTeamIdNot(matchId, teamId)
                .map(ManagerMapper::mapMatchSquadToDto);
    }
}

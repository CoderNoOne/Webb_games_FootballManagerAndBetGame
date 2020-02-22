package com.app.web_app.model.manager_game.mapper;

import com.app.web_app.exceptions.AppException;
import com.app.web_app.model.admin.TeamBaseDto;
import com.app.web_app.model.bet_game.ScoreEntity;
import com.app.web_app.model.dto.bet_game.ScoreDto;
import com.app.web_app.model.manager_game.*;
import com.app.web_app.model.manager_game.dto.*;
import com.app.web_app.model.manager_game.enums.Formation;
import com.app.web_app.model.manager_game.enums.Position;
import com.app.web_app.model.manager_game.repository.CountryRepository;
import com.app.web_app.model.manager_game.repository.PlayerRepository;
import com.app.web_app.model.manager_game.repository.SquadRepository;
import com.app.web_app.model.manager_game.repository.TeamRepository;
import com.app.web_app.repository.LeagueRepository;
import com.app.web_app.repository.MatchRepository;
import com.app.web_app.repository.UserRepository;
import com.app.web_app.repository.bet_game.ScoreEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class ManagerMapper {

    private final CountryRepository countryRepository;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final SquadRepository squadRepository;
    private final ScoreEntityRepository scoreEntityRepository;
    private final UserRepository userRepository;
    private final LeagueRepository leagueRepository;
    private final MatchRepository matchRepository;

    public ScoreDto mapScoreEntityToDto(ScoreEntity scoreEntity) {
        return scoreEntity != null ?
                ScoreDto.builder()
                        .id(scoreEntity.getId())
                        .league(scoreEntity.getLeague())
                        .username(scoreEntity.getUser().getUsername())
                        .betScores(scoreEntity.getBetScores())
                        .build()

                : null;
    }

    public Country mapCountryDtoToCountryEntity(CountryDto countryDto) {
        return countryDto != null && countryDto.getCountryName() != null ?

                countryRepository.findByName(countryDto.getCountryName())
                        .orElse(null)
                : null;
    }

  /*  public Team mapTeamDtoToTeam(TeamDto teamDto) {
        return teamDto != null ?

                Team.builder()
                        .name(teamDto.getName())
                        .

                .build()
                :null;
    }*/

    public Team mapTeamDtoToTeamEntity(TeamDto teamDto) {

        return teamDto != null && teamDto.getId() != null ?

                teamRepository.findById(teamDto.getId())
                        .orElse(null)
                : null;
    }

    public Player mapPlayerDtoToPlayerEntity(PlayerDto playerDto) {
        return playerDto != null && playerDto.getId() != null ?
                playerRepository.findById(playerDto.getId()).orElse(null)
                : null;
    }

    public Squad mapSquadDtoToSquad(SquadDto squadDto) {

        return squadDto != null ?
                squadDto.getName() != null ?
                        squadRepository.findByNameAndTeamId(squadDto.getName(), squadDto.getTeamDto().getId()).orElse(createNewSquad(squadDto))
                        : createNewSquad(squadDto)
                : null;

    }

    public Squad createNewSquad(SquadDto squadDto) {

        AtomicReference<Squad> squad = new AtomicReference<>();
        if (squadDto != null) {
            Map<String, PlayerDto> players = squadDto.getPlayers();
            players.putAll(squadDto.getSubstitutions());
            @SuppressWarnings("OptionalGetWithoutIsPresent")
            List<PlayerSquadPosition> playerSquadPositions = players.entrySet()
                    .stream()
                    .map(entry -> PlayerSquadPosition.builder()
                            .position(Position.fromString(entry.getKey()))
                            .player(playerRepository.findById(entry.getValue().getId()).get())
                            .build())
                    .collect(Collectors.toList());

            squad.set(Squad.builder()
                    .formationType(squadDto.getFormationType())
                    .name(squadDto.getName())
                    .playerSquadPositions(playerSquadPositions)
                    .team(teamRepository.findById(squadDto.getTeamDto().getId()).orElseThrow(() -> new AppException("No team found with id: " + squadDto.getTeamDto().getId())))
                    .build());

            playerSquadPositions.forEach(playerPosition -> playerPosition.setSquad(squad.get()));
        }


        return squad.get();
    }

    public PlayerAttributesDto mapPlayerAttributesToDto(PlayerAttributes playerAttributes) {
        return playerAttributes != null ?

                PlayerAttributesDto.builder()
                        .id(playerAttributes.getId())
                        .playerId(playerAttributes.getPlayer().getId())
                        .attacking(playerAttributes.getAttacking())
                        .defending(playerAttributes.getDefending())
                        .heading(playerAttributes.getHeading())
                        .dribbling(playerAttributes.getDribbling())
                        .passing(playerAttributes.getPassing())
                        .technique(playerAttributes.getTechnique())
                        .teamWork(playerAttributes.getTeamWork())
                        .speed(playerAttributes.getSpeed())
                        .aggression(playerAttributes.getAggression())
                        .oneOnOnes(playerAttributes.getOneOnOnes())
                        .penaltySaving(playerAttributes.getPenaltySaving())
                        .penaltyScoring(playerAttributes.getPenaltyScoring())
                        .reflexes(playerAttributes.getReflexes())
                        .build()
                : null;
    }

    public PlayerDto mapPlayerToDto(Player player) {
        return player != null ?
                PlayerDto.builder()
                        .id(player.getId())
                        .firstName(player.getFirstName())
                        .lastName(player.getLastName())
                        .positions(player.getPositions())
                        .number(player.getNumber())
                        .playerAttributes(mapPlayerAttributesToDto(player.getPlayerAttributes()))
                        .imageUrl(player.getImageUrl())
                        .teamId(player.getTeam().getId())
                        .build() :
                null;
    }


    public TeamDto mapTeamToTeamDto(Team team) {

        return team != null ?
                TeamDto.builder()
                        .id(team.getId())
                        .name(team.getName())
                        .backgroundUrl(team.getBackgroundUrl())
                        .players(team.getPlayers() != null ?
                                team.getPlayers().stream().map(this::mapPlayerToDto).filter(Objects::nonNull).collect(Collectors.toSet()) : new HashSet<>())
                        .shirtColors(team.getShirtColors())
                        .logoUrl(team.getLogoUrl())
                        .username(team.getUser() != null ? team.getUser().getUsername() : null)
                        .build()

                : null;
    }

    public TeamDto mapTeamBaseToDto(TeamBaseDto teamBaseDto) {
        return teamBaseDto != null ?
                TeamDto
                        .builder()
                        .name(teamBaseDto.getName())
                        .backgroundUrl(teamBaseDto.getBackgroundUrl())
                        .logoUrl(teamBaseDto.getLogoUrl())
                        .shirtColors(teamBaseDto.getShirtColors())
                        .build()
                :
                null;
    }

    public SquadDto mapSquadToSquadDto(Squad squad) {
        return squad != null ?
                SquadDto.builder()
                        .id(squad.getId())
                        .formationType(squad.getFormationType())
                        .name(squad.getName())
                        .teamDto(mapTeamToTeamDto(squad.getTeam()))
                        .players(squad.getPlayerSquadPositions() != null ? squad.getPlayerSquadPositions().stream()
                                .filter(playerPosition -> Formation.fromFormationNumber(squad.getFormationType()).isPresent() && Formation.fromFormationNumber(squad.getFormationType()).get().getPositions().contains(playerPosition.getPosition().name()))
                                .collect(Collectors.toMap(playerSquadPosition -> playerSquadPosition.getPosition().name(), playerPosition -> mapPlayerToDto(playerPosition.getPlayer())))
                                : new HashMap<>())
                        .substitutions(squad.getPlayerSquadPositions() != null ?
                                squad.getPlayerSquadPositions()
                                        .stream()
                                        .filter(playerPosition -> !Formation.fromFormationNumber(squad.getFormationType()).get().getPositions().contains(playerPosition.getPosition().name()))
                                        .collect(Collectors.toMap(
                                                playerSquadPosition -> playerSquadPosition.getPosition().name(),
                                                playerPosition -> mapPlayerToDto(playerPosition.getPlayer())))
                                : new HashMap<>())
                        .build()
                : null;
    }

    public LeagueDto mapLeagueToDto(League league) {

        return league != null ?
                LeagueDto.builder()
                        .id(league.getId())
                        .name(league.getName())
                        .startDate(league.getStartDate())
                        .teams(league.getTeams().stream().map(Team::getName).toArray(String[]::new))
                        .matches(league.getMatches().stream().map(this::mapMatchToDto).collect(Collectors.toList()))
                        .gameId(league.getGame().getId())
                        .build()
                : null;

    }

    public MatchDto mapMatchToDto(Match match) {
        return match != null ?

                MatchDto.builder()
                        .id(match.getId())
                        .awayTeam(mapTeamToTeamDto(match.getAwayTeam()))
                        .homeTeam(mapTeamToTeamDto(match.getHomeTeam()))
                        .dateTime(match.getDateTime())
                        .leagueId(match.getLeague().getId())
                        .score(match.getScore())
                        .matchDay(match.getMatchDay())
                        .teamSquads(match.getStartingTeamStartingSquads() != null ? match.getStartingTeamStartingSquads().stream().map(this::mapTeamSquadToDto).collect(Collectors.toList()) : new ArrayList<>())
                        .build()
                : null;
    }

    public Match mapMatchDtoToMatch(MatchDto matchDto) {

        return matchDto != null ?

                Match.builder()
                        .id(matchDto.getId())
                        .awayTeam(mapTeamDtoToTeamEntity(matchDto.getAwayTeam()))
                        .homeTeam(mapTeamDtoToTeamEntity(matchDto.getHomeTeam()))
                        .dateTime(matchDto.getDateTime())
                        .score(matchDto.getScore())
                        .matchDay(matchDto.getMatchDay())
                        .startingTeamStartingSquads(matchDto.getTeamSquads() != null ? matchDto.getTeamSquads().stream().map(this::mapTeamSquadDtoToTeamSquad).collect(Collectors.toList()) : null)
                        .status(matchDto.getStatus())
                        .league(leagueRepository.findById(matchDto.getId()).orElse(null))
                        .build()

                : null;
    }

    public ScoreEntity mapScoreDtoToEntity(ScoreDto scoreDto) {

        return scoreDto != null ?

                scoreDto.getId() != null ?
                        scoreEntityRepository.findById((scoreDto.getId())).orElse(

                                ScoreEntity.builder()
                                        .id(scoreDto.getId())
                                        .league(scoreDto.getLeague())
                                        .user(userRepository.findByUsername(scoreDto.getUsername()).orElse(null))
                                        .betScores(scoreDto.getBetScores())
                                        .build())
                        :

                        ScoreEntity.builder()
                                .league(scoreDto.getLeague())
                                .user(userRepository.findByUsername(scoreDto.getUsername()).orElse(null))
                                .betScores(scoreDto.getBetScores())
                                .build()

                : null;
    }

    public MatchSquadDto mapMatchSquadToDto(MatchSquad matchSquad) {

        return matchSquad != null ?

                MatchSquadDto.builder()
                        .id(matchSquad.getId())
                        .matchId(matchSquad.getMatchId())
                        .teamId(matchSquad.getTeamId())
                        .substitutionsNumberAvailable(matchSquad.getSubstitutionsAvailable())
                        .formation(matchSquad.getFormation())
                        .players(matchSquad.getPlayers().entrySet()
                                .stream()
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        e -> mapPlayerToDto(e.getValue())
                                )))
                        .build()
                :
                null;

    }

    public MatchSquad mapMatchSquadDtoToMatchSquad(MatchSquadDto matchSquadDto) {

        return matchSquadDto != null ?

                MatchSquad.builder()
                        .id(matchSquadDto.getId())
                        .formation(matchSquadDto.getFormation())
                        .substitutionsAvailable(matchSquadDto.getSubstitutionsNumberAvailable())
                        .matchId(matchSquadDto.getMatchId())
                        .teamId(matchSquadDto.getTeamId())
                        .players(matchSquadDto.getPlayers()
                                .entrySet()
                                .stream()
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        e -> mapPlayerDtoToPlayerEntity(e.getValue())
                                )))
                        .build()

                : null;

    }

    public TeamStartingSquad mapTeamSquadDtoToTeamSquad(TeamSquadDto teamSquadDto) {

        return teamSquadDto != null ?

                TeamStartingSquad.builder()
                        .id(teamSquadDto.getId())
                        .match(matchRepository.findById(teamSquadDto.getMatchId()).orElse(null))
                        .team(mapTeamDtoToTeamEntity(teamSquadDto.getTeamDto()))
                        .squad(mapSquadDtoToSquad(teamSquadDto.getSquadDto()))
                        .build()

                : null;

    }

    public TeamSquadDto mapTeamSquadToDto(TeamStartingSquad teamStartingSquad) {

        return teamStartingSquad != null ?

                TeamSquadDto.builder()
                        .id(teamStartingSquad.getId())
                        .matchId(teamStartingSquad.getMatch().getId())
                        .teamDto(mapTeamToTeamDto(teamStartingSquad.getTeam()))
                        .squadDto(mapSquadToSquadDto(teamStartingSquad.getSquad()))
                        .build()

                : null;

    }


    public MatchStatisticDto mapMatchStatisticToDto(MatchStatistic matchStatistic) {

        return matchStatistic != null ?

                MatchStatisticDto.builder()
                        .id(matchStatistic.getId())
                        .matchDto(mapMatchToDto(matchStatistic.getMatch()))
                        .minute(matchStatistic.getMinute())
                        .score(matchStatistic.getScore())
                        .status(matchStatistic.getMatchStatus())
                        .build()

                : null;
    }

    public MatchStatistic mapMatchStatisticDtoToMatchStatistic(MatchStatisticDto matchStatisticDto) {

        return matchStatisticDto != null ?

                MatchStatistic.builder()
                        .score(matchStatisticDto.getScore())
                        .minute(matchStatisticDto.getMinute())
                        .match(mapMatchDtoToMatch(matchStatisticDto.getMatchDto()))
                        .id(matchStatisticDto.getId())
                        .matchStatus(matchStatisticDto.getStatus())
                        .build()
                : null;
    }

    public GoalDetailDto mapGoalDetailToDto(GoalDetail goalDetail) {

        return goalDetail != null ?

                GoalDetailDto.builder()
                        .id(goalDetail.getId())
                        .minute(goalDetail.getMinute())
                        .scorer(mapPlayerToDto(goalDetail.getScorer()))
                        .assistant(mapPlayerToDto(goalDetail.getAssistant()))
                        .build()

                : null;
    }

}

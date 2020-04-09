package org.example.fm;

import org.example.core.entity.User;
import org.example.core.exceptions.AppException;
import org.example.fm.entity.*;
import org.example.fm.enums.FmMatchStatus;
import org.example.fm.enums.Position;
import org.example.model.admin.TeamBaseDto;
import org.example.model.core.GameDto;
import org.example.model.fm.*;
import org.example.model.fm.enums.Formation;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface ManagerMapper {

    static Country mapCountryDtoToCountry2(CountryDto countryDto) {
        return countryDto != null ?

                Country.builder()
                        .name(countryDto.getName())
                        .build()

                : null;
    }

    static Team mapTeamDtoToTeam2(TeamDto teamDto) {

        return teamDto != null ?
                Team.builder()
                        .id(teamDto.getId())
                        .country(mapCountryDtoToCountry2(teamDto.getCountryDto()))
                        .name(teamDto.getName())
                        .players(teamDto.getPlayers() != null ?
                                teamDto.getPlayers().stream()
                                        .map(ManagerMapper::mapPlayerDtoToPlayer2)
                                        .collect(Collectors.toSet())
                                : null)
                        .league(mapLeagueDtoToLeague2(teamDto.getLeagueDto()))
                        .backgroundUrl(teamDto.getBackgroundUrl())
                        .logoUrl(teamDto.getLogoUrl())
                        .shirtColors(teamDto.getShirtColors())
                        .user(User.builder()
                                .username(teamDto.getUsername())
                                .build())
                        .build()
                : null;

    }

    static League mapLeagueDtoToLeague2(LeagueDto leagueDto) {

        return leagueDto != null ?

                League.builder()
                        .id(leagueDto.getId())
                        .name(leagueDto.getName())
                        .country(mapCountryDtoToCountry2(leagueDto.getCountryDto()))
                        .startDate(leagueDto.getStartDate())
                        .matches(leagueDto.getMatches() != null ? leagueDto.getMatches().stream().map(ManagerMapper::mapMatchDtoToMatch2).collect(Collectors.toList()) : null)
                        .game(Game.builder()
                                .id(leagueDto.getGameId())
                                .build())
                        .teams(leagueDto.getTeams() != null ? Arrays
                                .stream(leagueDto.getTeams())
                                .map(teamName -> Team.builder()
                                        .name(teamName)
                                        .build()).collect(Collectors.toList()) : null)

                        .build()
                : null;
    }


    static Match mapMatchDtoToMatch2(MatchDto matchDto) {
        return matchDto != null ?

                Match.builder()
                        .id(matchDto.getId())
                        .dateTime(matchDto.getDateTime())
                        .league(League.builder()
                                .id(matchDto.getLeagueId())
                                .build())
                        .awayTeam(mapTeamDtoToTeam2(matchDto.getAwayTeam()))
                        .homeTeam(mapTeamDtoToTeam2(matchDto.getHomeTeam()))
                        .matchDay(matchDto.getMatchDay())
                        .score(matchDto.getScore())
                        .status(FmMatchStatus.fromString(matchDto.getStatus()))
                        .build()
                : null;
    }

    static Player mapPlayerDtoToPlayer2(PlayerDto playerDto) {

        return playerDto != null ?
                Player.builder()
                        .id(playerDto.getId())
                        .firstName(playerDto.getFirstName())
                        .lastName(playerDto.getLastName())
                        .country(Country.builder()
                                .name(playerDto.getCountryDto() != null ?
                                        playerDto.getCountryDto().getName() : null)
                                .build())
                        .number(playerDto.getNumber())
                        .imageUrl(playerDto.getImageUrl())
                        .team(Team.builder()
                                .id(playerDto.getTeamId())
                                .build())
                        .positions(playerDto.getPositions() != null ? playerDto.getPositions().stream().map(Position::fromString).collect(Collectors.toSet()) : new HashSet<>())
                        .playerAttributes(mapPlayerAttributesDtoToPlayerAttributes2(playerDto.getPlayerAttributes()))
                        .build()
                : null;
    }

    static PlayerAttributes mapPlayerAttributesDtoToPlayerAttributes2(PlayerAttributesDto playerAttributesDto) {

        return playerAttributesDto != null ?

                PlayerAttributes.builder()
                        .id(playerAttributesDto.getId())
                        .aggression(playerAttributesDto.getAggression())
                        .attacking(playerAttributesDto.getAttacking())
                        .defending(playerAttributesDto.getDefending())
                        .dribbling(playerAttributesDto.getDribbling())
                        .heading(playerAttributesDto.getHeading())
                        .oneOnOnes(playerAttributesDto.getOneOnOnes())
                        .passing(playerAttributesDto.getPassing())
                        .penaltySaving(playerAttributesDto.getPenaltySaving())
                        .penaltyScoring(playerAttributesDto.getPenaltyScoring())
                        .reflexes(playerAttributesDto.getReflexes())
                        .speed(playerAttributesDto.getSpeed())
                        .teamWork(playerAttributesDto.getTeamWork())
                        .technique(playerAttributesDto.getTechnique())
                        .player(Player.builder()
                                .id(playerAttributesDto.getPlayerId())
                                .build())
                        .build()

                : null;
    }


    static Team mapTeamDtoToTeam(TeamDto teamDto) {
        return teamDto != null ?
                Team.builder()
                        .id(teamDto.getId())
                        .build()
                : null;
    }


    static PlayerAttributesDto mapPlayerAttributesToDto(PlayerAttributes playerAttributes) {
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

    static PlayerDto mapPlayerToDto(Player player) {
        return player != null ?
                PlayerDto.builder()
                        .id(player.getId())
                        .firstName(player.getFirstName())
                        .lastName(player.getLastName())
                        .positions(player.getPositions() != null ? player.getPositions().stream().map(Position::name).collect(Collectors.toSet()) : new HashSet<>())
                        .number(player.getNumber())
                        .playerAttributes(mapPlayerAttributesToDto(player.getPlayerAttributes()))
                        .imageUrl(player.getImageUrl())
                        .teamId(player.getTeam() != null ? player.getTeam().getId() : null)
                        .build() :
                null;
    }


    static TeamDto mapTeamToTeamDto(Team team) {

        return team != null ?
                TeamDto.builder()
                        .id(team.getId())
                        .name(team.getName())
                        .backgroundUrl(team.getBackgroundUrl())
                        .players(team.getPlayers() != null ?
                                team.getPlayers().stream().map(ManagerMapper::mapPlayerToDto).filter(Objects::nonNull).collect(Collectors.toSet()) : new HashSet<>())
                        .shirtColors(team.getShirtColors())
                        .logoUrl(team.getLogoUrl())
                        .username(team.getUser() != null ? team.getUser().getUsername() : null)
                        .build()

                : null;
    }

    static TeamDto mapTeamBaseToDto(TeamBaseDto teamBaseDto) {
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

    static SquadDto mapSquadToSquadDto(Squad squad) {
        return squad != null ?
                SquadDto.builder()
                        .id(squad.getId())
                        .formationType(squad.getFormationType())
                        .name(squad.getName())
                        .teamDto(mapTeamToTeamDto(squad.getTeam()))
                        .players(squad.getPlayerSquadPositions() != null ? squad.getPlayerSquadPositions().stream()
                                .filter(playerPosition -> Formation.fromFormationNumber(squad.getFormationType()) != null && Formation.fromFormationNumber(squad.getFormationType()).getPositions().contains(playerPosition.getPosition().name()))
                                .collect(Collectors.toMap(playerSquadPosition -> playerSquadPosition.getPosition().name(), playerPosition -> mapPlayerToDto(playerPosition.getPlayer())))
                                : new HashMap<>())
                        .substitutions(squad.getPlayerSquadPositions() != null ?
                                squad.getPlayerSquadPositions()
                                        .stream()
                                        .filter(playerPosition -> !Formation.fromFormationNumber(squad.getFormationType()).getPositions().contains(playerPosition.getPosition().name()))
                                        .collect(Collectors.toMap(
                                                playerSquadPosition -> playerSquadPosition.getPosition().name(),
                                                playerPosition -> mapPlayerToDto(playerPosition.getPlayer())))
                                : new HashMap<>())
                        .formationType(squad.getFormationType())
                        .build()
                : null;
    }

    static LeagueDto mapLeagueToDto(League league) {

        return league != null ?
                LeagueDto.builder()
                        .id(league.getId())
                        .name(league.getName())
                        .startDate(league.getStartDate())
                        .teams(league.getTeams() != null ?
                                league.getTeams().stream().map(Team::getName).toArray(String[]::new)
                                : new String[]{})
                        .matches(league.getMatches() != null ?
                                league.getMatches().stream().map(ManagerMapper::mapMatchToDto).collect(Collectors.toList()) :
                                Collections.emptyList())
                        .gameId(league.getGame() != null ?
                                league.getGame().getId() : null)
                        .build()
                : null;

    }

    static MatchDto mapMatchToDto(Match match) {
        return match != null ?

                MatchDto.builder()
                        .id(match.getId())
                        .awayTeam(mapTeamToTeamDto(match.getAwayTeam()))
                        .homeTeam(mapTeamToTeamDto(match.getHomeTeam()))
                        .dateTime(match.getDateTime())
                        .leagueId(match.getLeague() != null ? match.getLeague().getId() : null)
                        .score(match.getScore())
                        .matchDay(match.getMatchDay())
                        .build()
                : null;
    }


    static MatchSquadDto mapMatchSquadToDto(MatchSquad matchSquad) {

        if (matchSquad == null) {
            return null;
        }

        Map<String, PlayerDto> subs = new HashMap<>();

        Map<String, PlayerDto> firstElevenPlayers = matchSquad.getPlayers() != null ? matchSquad.getPlayers().entrySet()
                .stream()
                .peek(e -> {
                    if (!Formation.fromFormationNumber(matchSquad.getFormationType()).getPositions().contains(e.getKey())) {
                        subs.put(e.getKey(), mapPlayerToDto(e.getValue()));
                    }
                })
                .filter(e -> Formation.fromFormationNumber(matchSquad.getFormationType()).getPositions().contains(e.getKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> mapPlayerToDto(e.getValue())
                )) : null;


        return MatchSquadDto.builder()
                .id(matchSquad.getId())
                .matchId(matchSquad.getMatch() != null ? matchSquad.getId() : null)
                .teamDto(mapTeamToTeamDto(matchSquad.getTeam()))
                .substitutionsNumberAvailable(matchSquad.getSubstitutionsAvailable())
                .formation(matchSquad.getFormationType() != null ? Formation.fromFormationNumber(matchSquad.getFormationType()) : null)
                .players(firstElevenPlayers != null ? firstElevenPlayers : new HashMap<>())
                .substitutions(subs)
                .build();

    }


    static MatchStatisticDto mapMatchStatisticToDto(MatchStatistic matchStatistic) {

        return matchStatistic != null ?

                MatchStatisticDto.builder()
                        .id(matchStatistic.getId())
                        .matchDto(mapMatchToDto(matchStatistic.getMatch()))
                        .minute(matchStatistic.getMinute())
                        .score(matchStatistic.getScore())
                        .status(matchStatistic.getMatchStatus().name())
                        .build()

                : null;
    }

    static GoalDetailDto mapGoalDetailToDto(GoalDetail goalDetail) {

        return goalDetail != null ?

                GoalDetailDto.builder()
                        .id(goalDetail.getId())
                        .minute(goalDetail.getMinute())
                        .scorer(mapPlayerToDto(goalDetail.getScorer()))
                        .assistant(mapPlayerToDto(goalDetail.getAssistant()))
                        .build()

                : null;
    }

    static MatchSquad mapMatchSquadDtoToMatchSquad(MatchSquadDto matchSquadDto) {

        return matchSquadDto != null ?

                MatchSquad.builder()
                        .id(matchSquadDto.getId())
                        .match(Match.builder()
                                .id(matchSquadDto.getMatchId())
                                .build())
                        .formationType(matchSquadDto.getFormation() != null ?
                                matchSquadDto.getFormation().getNumber() : null)
                        .substitutionsAvailable(matchSquadDto.getSubstitutionsNumberAvailable())
                        .team(mapTeamDtoToTeam2(matchSquadDto.getTeamDto()))
                        .players(matchSquadDto.getPlayers() != null ?
                                matchSquadDto.getPlayers()
                                        .entrySet()
                                        .stream()
                                        .collect(Collectors.toMap(
                                                Map.Entry::getKey,
                                                e -> mapPlayerDtoToPlayer2(e.getValue())
                                        ))
                                : new HashMap<>())
                        .build()

                : null;
    }

    static Squad mapSquadDtoToSquad(SquadDto squadDto) {
        return
                squadDto != null ?

                        Squad.builder()
                                .id(squadDto.getId())
                                .name(squadDto.getName())
                                .team(mapTeamDtoToTeam2(squadDto.getTeamDto()))
                                .formationType(squadDto.getFormationType())
                                .playerSquadPositions(Stream.of(squadDto.getPlayers(), squadDto.getSubstitutions())
                                        .flatMap(map -> map.entrySet().stream())
                                        .map(entry -> mapMapEntryToPlayerSquadPosition(entry.getKey(), entry.getValue()))
                                        .collect(Collectors.toList()))
                                .build()

                        : null;
    }

    private static PlayerSquadPosition mapMapEntryToPlayerSquadPosition(String position, PlayerDto playerDto) {

        if (position == null) {
            throw new AppException("Position is null");
        }

        if (playerDto == null) {
            throw new AppException("PlayerDto is null");
        }

        return PlayerSquadPosition
                .builder()
                .position(Position.fromString(position))
                .player(Player.builder()
                        .id(playerDto.getId())
                        .build())
                .build();

    }


    static GameDto mapGameToDto(Game game) {

        return game == null ? null :
                GameDto.builder()
                        .id(game.getId())
                        .active(game.getActive())
                        .startDate(game.getStartDate())
                        .leagues(
                                game.getLeagues() != null ?
                                        game.getLeagues().stream().map(ManagerMapper::mapLeagueToDto).collect(Collectors.toSet())
                                        : Collections.emptySet())
                        .userUsernames(game.getUsers() != null ?
                                game.getUsers().stream().map(User::getUsername).collect(Collectors.toSet()) :
                                Collections.emptySet())
                        .build();
    }

    static Game mapGameDtoToGame(GameDto gameDto) {
        return gameDto != null ?
                Game.builder()
                        .id(gameDto.getId())
                        .active(gameDto.getActive())
                        .startDate(gameDto.getStartDate())
                        .leagues(gameDto.getLeagues() != null ? gameDto.getLeagues().stream().map(leagueDto -> League.builder().id(leagueDto.getId()).build())
                                .collect(Collectors.toSet()) :
                                Collections.emptySet())
                        .users(gameDto.getUserUsernames() != null ? gameDto.getUserUsernames().stream().map(username -> User.builder().username(username).build()).collect(Collectors.toSet()) :
                                Collections.emptySet())
                        .build()
                : null;
    }
}

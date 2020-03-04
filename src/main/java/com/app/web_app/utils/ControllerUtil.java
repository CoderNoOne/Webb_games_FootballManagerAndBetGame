package com.app.web_app.utils;

import com.app.web_app.exceptions.AppException;
import com.app.web_app.model.FMChooseTeam;
import com.app.web_app.model.admin.LeagueBaseDto;
import com.app.web_app.model.dto.UserDto;
import com.app.web_app.model.dto.VerificationTokenDto;
import com.app.web_app.model.manager_game.FormationDto;
import com.app.web_app.model.manager_game.MatchSquad;
import com.app.web_app.model.manager_game.dto.*;
import com.app.web_app.model.manager_game.enums.Formation;
import com.app.web_app.model.manager_game.enums.Position;
import com.app.web_app.model.manager_game.service.PlayerService;
import com.app.web_app.model.user.enums.Authority;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Slf4j
@RequiredArgsConstructor
public class ControllerUtil {

    private final PlayerService playerService;

    public Map<TeamDto, TeamStandingsDto> createTeamStandingsForTeams(List<TeamDto> teams) {

        if (teams == null) {
            throw new AppException("Teams is null");
        }

        return teams.stream()
                .collect(Collectors.toMap(
                        teamDto -> teamDto,
                        teamDto -> TeamStandingsDto.builder()
                                .draws(0)
                                .wins(0)
                                .goalDifference(0)
                                .matchesNumber(0)
                                .points(0)
                                .loses(0)
                                .team(teamDto)
                                .build()
                ));
    }

    public void countPoints(boolean isHome, String result, TeamStandingsDto teamStandingsDto) {

        if (result == null) {
            throw new AppException("Result is null");
        }

        if (teamStandingsDto == null) {
            throw new AppException("TeamStandinsDto is null");
        }

        if (!result.matches("\\d+:\\d+")) {
            throw new AppException("Result is not in valid format!");
        }

        String[] scores = result.split("[:]");

        int homeTeam = isHome ? 0 : 1;
        int awayTeam = isHome ? 1 : 0;

        int score = Integer.parseInt(scores[homeTeam]) - Integer.parseInt(scores[awayTeam]);
        countPointsForScore(score, teamStandingsDto);
    }

    private void countPointsForScore(int score, TeamStandingsDto teamStandingsDto) {
        if (score > 0) {
            teamStandingsDto.setWins(teamStandingsDto.getWins() + 1);
            teamStandingsDto.setPoints(teamStandingsDto.getPoints() + 3);

        } else if (score == 0) {
            teamStandingsDto.setDraws(teamStandingsDto.getDraws() + 1);
            teamStandingsDto.setPoints(teamStandingsDto.getPoints() + 1);
        } else {
            teamStandingsDto.setLoses(teamStandingsDto.getLoses() + 1);
        }

        teamStandingsDto.setMatchesNumber(teamStandingsDto.getMatchesNumber() + 1);
        teamStandingsDto.setGoalDifference(teamStandingsDto.getGoalDifference() + score);
    }

    public Map<String, String> bindErrorsHibernateFields(BindingResult bindingResult) {

        if (bindingResult == null) {
            throw new AppException("Binding result is null");
        }

        Map<String, String> errors = new HashMap<>();
        if (bindingResult.hasErrors()) {
            errors = bindingResult.getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            DefaultMessageSourceResolvable::getDefaultMessage,
                            (v1, v2) -> v1 + " and " + v2
                    ));

        }
        return errors;
    }

    public Map<String, String> bindErrorsHibernateType(BindingResult bindingResult) {

        if (bindingResult == null) {
            throw new AppException("Binding result is null");
        }

        Map<String, String> errors = new HashMap<>();
        if (bindingResult.hasErrors()) {


            errors = bindingResult.getAllErrors()
                    .stream()
                    .collect(Collectors.toMap(
                            ObjectError::getObjectName,
                            DefaultMessageSourceResolvable::getDefaultMessage,
                            (v1, v2) -> v1 + " and " + v2
                    ));

        }
        return errors;
    }

    public Map<String, String> bindErrorsSpring(BindingResult bindingResult) {

        if (bindingResult == null) {
            throw new AppException("BindingResult is null");
        }

        Map<String, String> errors = new HashMap<>();
        if (bindingResult.hasErrors()) {


            errors = bindingResult.getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(
                            ObjectError::getObjectName,
                            DefaultMessageSourceResolvable::getCode,
                            (v1, v2) -> v1 + " and " + v2
                    ));

        }
        return errors;
    }

    public void createModelAttributesForGoals(Model model, Map<PlayerDto, Integer> goalsForTeamPlayers) {

        if (goalsForTeamPlayers == null) {
            throw new AppException("GoalsForTeamPlayers map is null");
        }

        if (model == null) {
            throw new AppException("Model is null");
        }

        model.addAttribute("goalsForPlayers", goalsForTeamPlayers)
                .addAttribute("teamsForPlayers", playerService.getTeamsForPlayers(goalsForTeamPlayers.keySet()));
    }

    public void createModelAttributesForAssists(Model model, Map<PlayerDto, Integer> assistsForPlayers) {

        if (assistsForPlayers == null) {
            throw new AppException("Assists for players map is null");
        }

        if (model == null) {
            throw new AppException("Model is null");
        }

        model.addAttribute("assistsForPlayers", assistsForPlayers)
                .addAttribute("teamsForPlayers", playerService.getTeamsForPlayers(assistsForPlayers.keySet()));
    }

    public TeamDto[] getTeamsForNames(String... teamNames) {

        if (teamNames == null) {
            throw new AppException("Team names is null");
        }

        return Arrays.stream(teamNames)
                .map(name -> TeamDto.builder()
                        .name(name)
                        .build())
                .toArray(TeamDto[]::new);
    }

    public void postRegisterOperations(UserDto userDto) {
        if (userDto == null) {
            throw new AppException("UserDto is null");
        }
        userDto.setEnabled(false);
        userDto.setAuthorities(new HashSet<>(Set.of(Authority.ROLE_USER)));
    }

    public VerificationTokenDto createVerificationTokenForUserDto(UserDto userDto) {

        return VerificationTokenDto.builder()
                .token(UUID.randomUUID().toString())
                .userDto(userDto)
                .build();
    }

    public boolean isNumberNotSelectedYet(PlayersNumberDto playersNumberDto, Map<Integer, Integer> currentPlayerNumbers) {

        return currentPlayerNumbers.entrySet()
                .stream()
                .allMatch(e ->
                        playersNumberDto.getPlayersNumber().entrySet()
                                .stream()
                                .filter(ee -> !ee.getKey().equals(e.getKey()))
                                .noneMatch(ee -> ee.getValue().equals(e.getValue())));
    }

    public List<Integer> getAvailableNumbers(Map<Integer, Integer> currentPlayerNumbers, List<Integer> playerIdsToAdd) {

        Map<Integer, Integer> playersWithNumberSelected = currentPlayerNumbers.entrySet()
                .stream()
                .peek(e -> {
                    if (e.getValue() == null) {
                        playerIdsToAdd.add(e.getKey());
                    }
                })
                .filter(e -> e.getValue() != null)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue));

        List<Integer> alreadySelectedNumbers = new ArrayList<>(playersWithNumberSelected.values());

        return IntStream.rangeClosed(1, 99)
                .filter(number -> !alreadySelectedNumbers.contains(number))
                .boxed()
                .collect(Collectors.toList());
    }

    public void createModelForFormation(Model model, Formation formation, Set<PlayerDto> players, List<SquadDto> squadsByTeamId) {

        Map<String, PlayerDto> sortedPositions = getSortedPositionsByFormation(formation, players);

        model.addAllAttributes(sortedPositions)
                .addAttribute("elevenPotential", FormationDto.builder().players(sortedPositions).substitutions(getSubsModelAttribute()).build())
                .addAttribute("playersForPosition", getPlayersForPosition(formation, players))
                .addAttribute("players", players)
                .addAttribute("chosenFormation", formation)
                .addAttribute("numbers", getNumbersForPosition(formation))
                .addAttribute("positionForInputIdMap", formation.getPositionForInputId())
                .addAttribute("savedSquads", squadsByTeamId);
    }

    public void createModelAttributesForTeam(Model model, TeamDto teamDto) {
        if (teamDto == null) {
            throw new AppException("TeamDto is null");
        }

        if (model == null) {
            throw new AppException("Model is null");
        }

        Map<Integer, Integer> playerNumbers = teamDto.getPlayers()
                .stream()
                .collect(Collectors.toMap(
                        PlayerDto::getId,
                        playerDto -> -1)
                );

        PlayersNumberDto playersNumberDto = PlayersNumberDto.builder().playersNumber(playerNumbers).build();

        model.addAttribute("playersNumberDto", playersNumberDto)
                .addAttribute("players", teamDto.getPlayers())
                .addAttribute("teamDto", teamDto);
    }

    public MatchSquadDto createMatchSquadDto() {

        return null;
    }

    public void createModelAttributesForOppTeamMatchCentre(Model model, MatchSquadDto matchSquadDto, Map<String, String> shirtColors) {

        if (model == null) {
            throw new AppException("Model is null");
        }

        if (matchSquadDto == null) {
            throw new AppException("MatchSquadDto is null");
        }

        if (shirtColors == null) {
            throw new AppException("Shirt colors is null");
        }

        Formation formation = matchSquadDto.getFormation();

        model.addAttribute("eleven", FormationDto.builder().players(matchSquadDto.getPlayers())
                .substitutions(matchSquadDto.getSubstitutions()).build())
                .addAttribute("chosenFormation", formation)
                .addAttribute("positionForInputIdMap", formation.getPositionForInputId())
                .addAttribute("colors", shirtColors);

    }

    public void setMatchSquadDtoProperties(MatchSquadDto matchSquadDto, Formation formation, SquadDto squadDto) {

        if (matchSquadDto == null) {
            throw new AppException("MatchSquadDto is null");
        }

        if (formation == null) {
            throw new AppException("Formation is null");
        }

        if (squadDto == null) {
            throw new AppException("SquadDto is null");
        }

        matchSquadDto.setFormation(formation);
        matchSquadDto.setPlayers(squadDto.getPlayers());
        matchSquadDto.setSubstitutionsNumberAvailable(3);
        matchSquadDto.setSubstitutions(squadDto.getSubstitutions());
    }

    public void createModelAttributesForUserTeamMatchCentre(Model model, MatchSquadDto matchSquadDto, Map<String, String> shirtColors) {

        if (model == null) {
            throw new AppException("Model is null");
        }

        if (matchSquadDto == null) {
            throw new AppException("MatchSquadDto is null");
        }

        if (shirtColors == null) {
            throw new AppException("Shirt colors is null");
        }

        Formation formation = matchSquadDto.getFormation();

        Map<String, List<PlayerDto>> subsPerPosition = formation.getPositionForInputId()
                .values()
                .stream()
                .collect(Collectors.toMap(
                        position -> position,
                        position -> matchSquadDto.getSubstitutions().values().stream()
                                .filter(playerDto -> playerDto.getPositions().contains(Position.fromString(position)))
                                .collect(Collectors.toList())
                ));

        model.addAttribute("eleven", FormationDto.builder().players(matchSquadDto.getPlayers())
                .substitutions(matchSquadDto.getSubstitutions()).build())
                .addAttribute("chosenFormation", formation)
                .addAttribute("positionForInputIdMap", formation.getPositionForInputId())
                .addAttribute("colors", shirtColors)
                .addAttribute("subsPerPosition", subsPerPosition);

    }

    public SquadDto createSquadDto(FormationDto eleven, Formation chosenFormation, TeamDto teamDto) {

        if (eleven == null) {
            throw new AppException("FormationDto is null");
        }

        if (chosenFormation == null) {
            throw new AppException("Chosen Formation is null");
        }

        if (teamDto == null) {
            throw new AppException("TeamDto is null");
        }

        return SquadDto.builder()
                .name(eleven.getName())
                .formationType(chosenFormation.getNumber())
                .players(eleven.getPlayers())
                .substitutions(eleven.getSubstitutions())
                .teamDto(teamDto)
                .build();
    }

    private Map<String, Integer> getNumbersForPosition(Formation formation) {

        if (formation == null) {
            throw new AppException("Formation is null");
        }

        if (formation.getPositions() == null) {
            throw new AppException("Formation positions is null");
        }

        AtomicInteger counter = new AtomicInteger(1);
        return formation.getPositions()
                .stream()
                .collect(
                        Collectors.toMap(
                                position -> position,
                                position -> counter.getAndIncrement()
                        )
                );

    }

    private Map<String, PlayerDto> getSortedPositionsByFormation(Formation formation, Set<PlayerDto> players) {

        if (formation == null) {
            throw new AppException("Formation is null");
        }

        if (formation.getPositions() == null) {
            throw new AppException("Players is null");
        }


        return formation.getPositions().stream()
                .collect(Collectors.toMap(
                        position -> position,
                        position -> new PlayerDto(),
                        (oldV, newV) -> oldV,
                        LinkedHashMap::new
                ));
    }

    private Map<String, List<PlayerDto>> getPlayersForPosition(Formation formation, Set<PlayerDto> players) {

        if (formation == null) {
            throw new AppException("Formation is null");
        }

        if (players == null) {
            throw new AppException("Players is null");
        }

        if (formation.getPositions() == null) {
            throw new AppException("Players is null");
        }

        return formation.getPositions()
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        position -> players.stream().filter(player -> player.getPositions().contains(Position.fromString(position))).collect(Collectors.toList()))
                );

    }

    private Map<String, PlayerDto> getSubsModelAttribute() {

        return List.of(
                "First",
                "Second",
                "Third",
                "Fourth",
                "Fifth",
                "Sixth",
                "Seventh"
        )
                .stream()
                .collect(Collectors.toMap(
                        sub -> sub,
                        sub -> new PlayerDto(),
                        (oldV, newV) -> oldV,
                        LinkedHashMap::new
                ));
    }

}

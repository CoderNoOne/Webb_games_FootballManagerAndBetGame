package com.app.web_app.controller;

import com.app.web_app.exceptions.AppException;
import com.app.web_app.model.FMChooseTeam;
import com.app.web_app.model.manager_game.FormationDto;
import com.app.web_app.model.manager_game.dto.*;
import com.app.web_app.model.manager_game.enums.Formation;
import com.app.web_app.model.manager_game.enums.Position;
import com.app.web_app.model.manager_game.service.*;
import com.app.web_app.service.EmailService;
import com.app.web_app.service.EmailType;
import com.app.web_app.service.GoalDetailService;
import com.app.web_app.service.UserService;
import com.app.web_app.utils.ControllerUtil;
import com.app.web_app.validators.FormationDtoValidator;
import com.app.web_app.validators.spring_validators.PlayersNumberDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@SessionAttributes(names = {"backgroundUrl"})
@RequiredArgsConstructor
@RequestMapping("/fm")
public class FootballManagerController {

    private final SquadService squadService;
    private final TeamService teamService;
    private final PlayerService playerService;
    private final LeagueService leagueService;
    private final MatchService matchService;
    private final MatchSquadService matchSquadService;
    private final ControllerUtil controllerUtil;
    private final TeamSquadService teamSquadService;
    private final MatchStatisticService matchStatisticService;
    private final PlayersNumberDtoValidator playersNumberDtoValidator;
    private final EmailService emailService;
    private final UserService userService;
    private final FormationDtoValidator formationDtoValidator;
    private final GoalDetailService goalDetailService;


    @ModelAttribute(name = "username")
    public String getLoggedUsername(
            @AuthenticationPrincipal(expression = "username") String username) {
        return username;
    }


    @GetMapping("/leagueSchedule")
    public String getLeagueSchedule(
            @AuthenticationPrincipal(expression = "username") String username,
            @RequestParam(required = false) String status,
            Model model) {

        leagueService.getActiveLeagueByUsername(username).ifPresentOrElse(
                leagueDto -> {
                    Integer leagueId = leagueDto.getId();

                    if (status == null || "scheduled".equals(status)) {

                        Map<Integer, List<MatchDto>> matchesGroupedByMatchDay = matchService.getMatchesGroupedByMatchDay(matchService.getScheduledMatches(leagueId));

                        model.addAttribute(matchesGroupedByMatchDay.isEmpty() ? "notScheduledMatches" : "scheduledMatchesPerMatchDay",
                                matchesGroupedByMatchDay.isEmpty() ? "There is not scheduled matches yet" : matchesGroupedByMatchDay);

                    } else if (status.equals("finished")) {

                        Map<Integer, List<MatchDto>> matchesGroupedByMatchDay = matchService.getMatchesGroupedByMatchDay(matchService.getFinishedMatchesForLeague(leagueId));

                        model.addAttribute(matchesGroupedByMatchDay.isEmpty() ? "notFinishedMatches" : "finishedMatchesPerMatchDay",
                                matchesGroupedByMatchDay.isEmpty() ? "There is not finished matches yet" : matchesGroupedByMatchDay);

                    }
                }, () -> model.addAttribute("noActiveLeague", "noActiveLeague")
        );

        model.addAttribute("currentTime", LocalTime.now());

        return "fm/league_schedule";
    }

    @GetMapping("/leagueBoard")
    public String getLeagueBoard(
            @AuthenticationPrincipal(expression = "username") String username,
            Model model) {

        leagueService.getActiveLeagueByUsername(username).ifPresentOrElse(
                leagueDto -> {

                    List<MatchDto> matches = leagueDto.getMatches()
                            .stream()
                            .filter(match -> match.getScore() != null)
                            .collect(Collectors.toList());

                    Map<TeamDto, TeamStandingsDto> map = controllerUtil
                            .createTeamStandingsForTeams(teamService.getTeamsByLeague(leagueDto.getId()));

                    matches
                            .forEach(match -> {
                                controllerUtil.countPoints(true, match.getScore(), map.get(match.getHomeTeam()));
                                controllerUtil.countPoints(false, match.getScore(), map.get(match.getAwayTeam()));
                            });

                    List<TeamStandingsDto> teamStandings = map.values().stream().sorted(Comparator.comparing(TeamStandingsDto::getPoints).reversed()).collect(Collectors.toList());

                    model.addAttribute("teamStandings", teamStandings)
                            .addAttribute("leagueName", leagueDto.getName());

                }, () -> model.addAttribute(
                        "noActiveLeague",
                        MessageFormat.format("There is no active league you participate in, {0}", username))
        );

        model.addAttribute("currentTime", LocalTime.now());

        return "fm/league_board";
    }

    @GetMapping("/teamManagement")
    public String manageTeam(
            @AuthenticationPrincipal(expression = "username") String username,
            Model model) {

        Optional<TeamDto> activeTeamByUsername = teamService.getActiveTeamByUsername(username);

        model.addAttribute("backgroundUrl", "https://i.imgur.com/p9hiHBU.jpg");

        activeTeamByUsername
                .ifPresentOrElse(
                        teamDto -> {
                            List<PlayerDto> players = playerService.getPlayersForByTeamId(teamDto.getId());
                            model.addAttribute("players", players)
                                    .addAttribute("playersNumberDto", new PlayersNumberDto(players.stream().collect(Collectors.toMap(
                                            PlayerDto::getId,
                                            player -> 0
                                    ))))
                                    .addAttribute("playerPositions", players.stream().collect(Collectors.toMap(
                                            PlayerDto::getId,
                                            player -> player.getPositions().stream().map(Position::name).collect(Collectors.joining(", "))
                                    )));
                        }
                        , () -> model.addAttribute("teamNotExists", "You have to choose team")
                );

        return "fm/team_management";
    }

    @PostMapping("/chooseTeam")
    public String chooseTeam(
            @AuthenticationPrincipal(expression = "username") String username,
            RedirectAttributes redirectAttributes,
            FMChooseTeam chooseTeam) {

        if (chooseTeam.getLeagueIdTeamId() == null) {
            redirectAttributes.addFlashAttribute("teamNotSelected", "You must enter team name");
        } else {

            var teamId = chooseTeam.getLeagueIdTeamId().values().stream().findFirst().get();

            teamService.setUserForTeamByTeamId(teamId, username);
        }
        return "redirect:/fm/start";
    }

    @ModelAttribute(name = "backgroundUrl")
    public String getBackgroundUrl(
            @AuthenticationPrincipal(expression = "username") String username) {

        Optional<TeamDto> activeTeamByUsername = teamService.getActiveTeamByUsername(username);
        return activeTeamByUsername.map(TeamDto::getBackgroundUrl).orElse(null);

    }

    @GetMapping("/playerNumbers")
    public String getPlayerNumbers
            (@AuthenticationPrincipal(expression = "username") String username,
             Model model) {

        Optional<TeamDto> activeTeamByUsername = teamService.getActiveTeamByUsername(username);

        TeamDto teamDto = activeTeamByUsername.get();

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

        return "fm/players_number";
    }

    @GetMapping("/clearPlayerNumber/{id}")
    public String clearPlayerNumber(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes) {

        playerService.getPlayerById(id).ifPresentOrElse(playerDto -> {

            boolean isCleared = playerService.clearPlayerNumbers(Collections.singleton(id)) == 1;

            if (isCleared) {
                redirectAttributes.addFlashAttribute("numberCleared", MessageFormat.format("Number was successfully cleared for player {0} {1}", playerDto.getFirstName(), playerDto.getLastName()));
            } else {
                redirectAttributes.addFlashAttribute("numberNotCleared", MessageFormat.format("Player {0} {1} hasn''t any number set", playerDto.getFirstName(), playerDto.getLastName()));
            }

        }, () -> redirectAttributes.addFlashAttribute("playerNotExists", MessageFormat.format("Player with id {0}doesnt''t exist", id)));
        return "redirect:/fm/playerNumbers";
    }

    @PostMapping("/clearPlayerNumbers")
    public String clearPlayerNumbers(
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal(expression = "username") String username) {

        Optional<TeamDto> activeTeamByUsername = teamService.getActiveTeamByUsername(username);

        Map<Integer, Integer> currentPlayerNumbers = activeTeamByUsername.orElseThrow(AppException::new).getPlayers().stream()
                .filter(e -> e.getNumber() != null)
                .collect(HashMap::new, (map, player) -> map.put(player.getId(), player.getNumber()), HashMap::putAll);

        Long playersCounter = playerService.clearPlayerNumbers(currentPlayerNumbers.keySet());

        redirectAttributes.addFlashAttribute("numberCleared", MessageFormat.format("Players number has been cleared for {0} players", playersCounter));

        return "redirect:/fm/playerNumbers";
    }

    @PostMapping("/savePlayerNumbers")
    public String savePlayerNumbers(
            @RequestParam(required = false) String randomizedNumbers,
            @AuthenticationPrincipal(expression = "username") String username,
            PlayersNumberDto playersNumberDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        Optional<TeamDto> activeTeamByUsername = teamService.getActiveTeamByUsername(username);

        Map<Integer, Integer> currentPlayerNumbers = activeTeamByUsername.get().getPlayers().stream()
                .collect(HashMap::new, (map, player) -> map.put(player.getId(), player.getNumber()), HashMap::putAll);

        if (randomizedNumbers == null) {

            playersNumberDtoValidator.validate(playersNumberDto, bindingResult);

            var errors = controllerUtil.bindErrorsSpring(bindingResult);

            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("errors", errors);
                return "redirect:/fm/playerNumbers";
            }

            boolean isNumberNotSelectedYet = currentPlayerNumbers.entrySet()
                    .stream()
                    .allMatch(e ->
                            playersNumberDto.getPlayersNumber().entrySet()
                                    .stream()
                                    .filter(ee -> !ee.getKey().equals(e.getKey()))
                                    .noneMatch(ee -> ee.getValue().equals(e.getValue())));

            if (!isNumberNotSelectedYet) {
                redirectAttributes.addFlashAttribute("numbersAlreadySelected", "Some numbers are already selected");
                return "redirect:/fm/playerNumbers";
            }

            redirectAttributes.addFlashAttribute("numbersSet", "Numbers has been successfully set");
            playerService.savePlayerNumbers(playersNumberDto);

        } else {

            List<Integer> playerIdsToAdd = new ArrayList<>();

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

            List<Integer> availableNumbers = IntStream.rangeClosed(1, 99)
                    .filter(number -> !alreadySelectedNumbers.contains(number))
                    .boxed()
                    .collect(Collectors.toList());

            Long playersCounter = playerService.randomizePlayerNumbers(playerIdsToAdd, availableNumbers);

            redirectAttributes.addFlashAttribute("randomizedNumbers", MessageFormat.format("Random number has been selected for {0} players", playersCounter));

        }
        return "redirect:/fm/playerNumbers";
    }

    @GetMapping("/start")
    public String start(
            @AuthenticationPrincipal(expression = "username") String username,
            String requestSend,
            String requestToShort,
            Model model,
            String teamNotSelected) {


        if (teamService.doUserHaveActiveTeam(username)) {
            getBackgroundUrl(getLoggedUsername(username));
            return "redirect:/fm/formation";
        }

        if (requestToShort != null) {
            model.addAttribute("requestToShort", "Request too short. Your request have to be at least 20 characters long");
        }

        if (requestSend != null) {
            model.addAttribute(requestSend, "Your request has been sent");
        }

        if (teamNotSelected != null) {
            model.addAttribute("teamNotSelected", teamNotSelected);
        }


        Set<LeagueDto> allLeaguesByActiveGame = leagueService.getAllLeaguesByActiveGameWithFetchedTeams();

        if (allLeaguesByActiveGame.size() == 0) {
            model.addAttribute("noTeams", "There is not available any team. Send a request to admin");
        }

        var leaguesInfo = allLeaguesByActiveGame.stream()
                .collect(Collectors.toMap(
                        LeagueDto::getId,
                        leagueDto -> leagueDto
                ));


        var alreadySelectedTeams = allLeaguesByActiveGame.stream()
                .collect(Collectors.toMap(
                        LeagueDto::getId,
                        league -> teamService.getTeamsWithAssociatedUsers(league.getTeams(), league.getId())
                ));

        var teamsToSelect = allLeaguesByActiveGame.stream()
                .collect(Collectors.toMap(
                        LeagueDto::getId,
                        league -> teamService.getTeamsWithNotAssociatedUsers(league.getTeams(), league.getId())
                ));


        var chooseTeam = FMChooseTeam.builder()
                .leagueIdTeamId(allLeaguesByActiveGame.stream().collect(Collectors.toMap(LeagueDto::getId, league -> -1)))
                .build();

        model
                .addAttribute("chooseTeam", chooseTeam)
                .addAttribute("alreadySelectedTeams", alreadySelectedTeams)
                .addAttribute("teamsToSelect", teamsToSelect)
                .addAttribute("leaguesInfo", leaguesInfo);

        return "fm/chooseTeam";

    }

    @GetMapping("/formation")
    public String formation(
            @AuthenticationPrincipal(expression = "username") String username,
            Model model,
            Formation formation) {

        teamService.getTeamByUsername(username).ifPresentOrElse(
                team -> {

                    Set<PlayerDto> players = team.getPlayers();

                    Map<String, String> shirtColors = teamService.getShirtColors(team);

                    List<SquadDto> squadsByTeamId = squadService.getSquadsByTeamId(team.getId());

                    model.addAttribute("colors", shirtColors);

                    switch (formation != null ? formation : Formation.FOUR_FOUR_TWO) {
                        case FOUR_FOUR_TWO -> {

                            List<String> formationPositions = Formation.FOUR_FOUR_TWO.getPositions();

                            Map<String, List<PlayerDto>> playersForPosition = formationPositions
                                    .stream()
                                    .collect(Collectors.toMap(
                                            position -> position,
                                            position -> players.stream().filter(player -> player.getPositions().contains(Position.fromString(position))).collect(Collectors.toList()))
                                    );

                            Map<String, PlayerDto> sortedPositions = formationPositions.stream()
                                    .collect(Collectors.toMap(
                                            position -> position,
                                            position -> new PlayerDto(),
                                            (oldV, newV) -> oldV,
                                            LinkedHashMap::new
                                    ));


                            AtomicInteger counter = new AtomicInteger(1);
                            Map<String, Integer> numbers = formationPositions
                                    .stream()
                                    .collect(
                                            Collectors.toMap(
                                                    position -> position,
                                                    position -> counter.getAndIncrement()
                                            )
                                    );


                            Map<String, PlayerDto> subs = List.of(
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
                            model.addAllAttributes(sortedPositions)
                                    .addAttribute("elevenPotential", FormationDto.builder().players(sortedPositions).substitutions(subs).build())
                                    .addAttribute("playersForPosition", playersForPosition)
                                    .addAttribute("players", players)
                                    .addAttribute("chosenFormation", Formation.FOUR_FOUR_TWO)
                                    .addAttribute("numbers", numbers)
                                    .addAttribute("positionForInputIdMap", Formation.FOUR_FOUR_TWO.getPositionForInputId())
                                    .addAttribute("savedSquads", squadsByTeamId);
                        }
                        case THREE_FIVE_TWO -> {

                            List<String> formationPositions = Formation.THREE_FIVE_TWO.getPositions();

                            Map<String, List<PlayerDto>> playersForPosition = formationPositions
                                    .stream()
                                    .collect(Collectors.toMap(
                                            position -> position,
                                            position -> players.stream().filter(player -> player.getPositions().contains(Position.fromString(position))).collect(Collectors.toList()))
                                    );

                            AtomicInteger counter = new AtomicInteger(1);
                            Map<String, Integer> numbers = formationPositions
                                    .stream()
                                    .collect(
                                            Collectors.toMap(
                                                    position -> position,
                                                    position -> counter.getAndIncrement()
                                            )
                                    );

                            Map<String, PlayerDto> sortedPositions = formationPositions.stream()
                                    .collect(Collectors.toMap(
                                            position -> position,
                                            position -> new PlayerDto(),
                                            (oldV, newV) -> oldV,
                                            LinkedHashMap::new
                                    ));

                            Map<String, PlayerDto> subs = List.of(
                                    Position.FIRST.name(),
                                    Position.SECOND.name(),
                                    Position.THIRD.name(),
                                    Position.FOURTH.name(),
                                    Position.FIFTH.name(),
                                    Position.SIXTH.name(),
                                    Position.SEVENTH.name()
                            )
                                    .stream()
                                    .collect(Collectors.toMap(
                                            sub -> sub,
                                            sub -> new PlayerDto(),
                                            (oldV, newV) -> oldV,
                                            LinkedHashMap::new
                                    ));

                            model.addAllAttributes(sortedPositions)
                                    .addAttribute("elevenPotential", FormationDto.builder().players(sortedPositions).substitutions(subs).build())
                                    .addAttribute("playersForPosition", playersForPosition)
                                    .addAttribute("chosenFormation", Formation.THREE_FIVE_TWO)
                                    .addAttribute("numbers", numbers)
                                    .addAttribute("players", players)
                                    .addAttribute("positionForInputIdMap", Formation.THREE_FIVE_TWO.getPositionForInputId())
                                    .addAttribute("savedSquads", squadsByTeamId);

                        }
                    }
                }, () -> model.addAttribute("")
        );
        model.addAttribute("formations", Formation.values());

        return "fm/custom_formation";
    }

    @PostMapping("/changeFormation")
    public String changeFormation(
            @AuthenticationPrincipal(expression = "username") String username,
            RedirectAttributes redirectAttributes,
            Formation chosenFormation) {

        redirectAttributes.addAttribute("formation", chosenFormation);

        return "redirect:/fm/formation";
    }

    @PostMapping("/saveSquad")
    public String saveFormation(
            @AuthenticationPrincipal(expression = "username") String username,
            @Valid FormationDto eleven,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Formation chosenFormation) {

        formationDtoValidator.validate(eleven, result);

        if (result.hasErrors()) {
            Map<String, String> o = controllerUtil.bindErrorsSpring(result);
            redirectAttributes.addFlashAttribute("errors", o);
        } else {

            TeamDto teamDto = teamService.getTeamByUsername(username).orElse(null);

            boolean isOverridden = squadService.saveSquad(SquadDto.builder()
                    .name(eleven.getName())
                    .formationType(chosenFormation.getNumber())
                    .players(eleven.getPlayers())
                    .substitutions(eleven.getSubstitutions())
                    .teamDto(teamDto)
                    .build());

            redirectAttributes.addFlashAttribute("squadSaved", isOverridden ?
                    MessageFormat.format("Squad with name: {0} has been overridden", eleven.getName())
                    : MessageFormat.format("Squad with name: {0} has been saved", eleven.getName()));
        }

        redirectAttributes.addAttribute("formation", chosenFormation);
        return "redirect:/fm/formation";
    }

    @PostMapping(value = "/loadSquad")
    public String loadFormation(
            @AuthenticationPrincipal(expression = "username") String username,
            String loadedSquad,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (loadedSquad == null) {
            redirectAttributes.addFlashAttribute("notValidLoadedSquad", "You must load a valid, existing squad");
            return "redirect:/fm/formation";
        } else {

            final TeamDto teamDto = teamService.getActiveTeamByUsername(username).orElse(null);

            SquadDto squadByName = squadService.findSquadByNameAndTeamId(loadedSquad, teamDto.getId()).orElse(null);

            Integer formationTypeNumber = squadByName.getFormationType();

            Formation formation = Formation.fromFormationNumber(formationTypeNumber).get();

            model.addAttribute("eleven", FormationDto.builder().players(squadByName.getPlayers()).substitutions(squadByName.getSubstitutions()).build());


            Map<String, PlayerDto> sortedPositions = formation.getPositions().stream()
                    .collect(Collectors.toMap(
                            position -> position,
                            position -> new PlayerDto(),
                            (oldV, newV) -> oldV,
                            LinkedHashMap::new
                    ));

            Set<PlayerDto> players = teamDto.getPlayers();

            final List<SquadDto> savedSquads = squadService.getSquadsByTeamId(teamDto.getId());


            Map<String, PlayerDto> subs = List.of(
                    "FIRST",
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

            Map<String, List<PlayerDto>> playersForPosition = formation.getPositions()
                    .stream()
                    .collect(Collectors.toMap(
                            position -> position,
                            position -> players.stream().filter(player -> player.getPositions().contains(Position.fromString(position))).collect(Collectors.toList()))
                    );

            System.out.println("subs" + squadByName.getSubstitutions());

            model.addAllAttributes(sortedPositions)
                    .addAttribute("eleven", FormationDto.builder().players(squadByName.getPlayers()).substitutions(squadByName.getSubstitutions()).build())
                    .addAttribute("elevenPotential", FormationDto.builder().players(sortedPositions).substitutions(subs).build())
                    .addAttribute("playersForPosition", playersForPosition)
                    .addAttribute("players", players)
                    .addAttribute("chosenFormation", formation)
                    .addAttribute("positionForInputIdMap", formation.getPositionForInputId())
                    .addAttribute("savedSquads", savedSquads)
                    .addAttribute("formations", Formation.values())
                    .addAttribute("colors", teamService.getShirtColors(teamDto));
        }
        return "fm/custom_formation";
    }


    @GetMapping("/upcomingMatches")
    public String getUpcomingMatches(
            @AuthenticationPrincipal(expression = "username") String username,
            Model model) {

        Optional<TeamDto> activeTeamByUsername = teamService.getActiveTeamByUsername(username);

        activeTeamByUsername.ifPresentOrElse(
                teamDto -> {
                    List<MatchDto> allMatches = matchService.getAllScheduledMatchesByTeamId(teamDto.getId(), PageRequest.of(0, 10));

                    if (allMatches.isEmpty()) {
                        model.addAttribute("noScheduledMatches", "Currently there is no scheduled matches");
                    } else {
                        MatchDto match = allMatches.get(0);

                        model.addAttribute("nextMatch", match);

                        if (match.getDateTime().minus(5, ChronoUnit.MINUTES).compareTo(LocalDateTime.now()) < 0) {
                            model.addAttribute("deactivateLink", "You can set starting squads no later than 5 minutes before match starts");
                        }
                    }
                }
                , () -> {
                    model.addAttribute("noActiveTeam", "You 've no active team yet. Choose one");
                }
        );

        return "fm/upcoming_matches";
    }

    @GetMapping("/startingSquad/{matchId}")
    public String startingSquad(
            @PathVariable Integer matchId,
            @AuthenticationPrincipal(expression = "username") String username,
            Model model) {

        TeamDto teamDto = teamService.getActiveTeamByUsername(username).orElse(null);

        teamSquadService.getByMatchIdAndTeamId(teamDto.getId(), matchId).ifPresentOrElse(
                teamSquadDto -> {

                    Formation formation = Formation.fromFormationNumber(teamSquadDto.getSquadDto().getFormationType()).get();

                    model.addAttribute("eleven", FormationDto.builder().players(teamSquadDto.getSquadDto().getPlayers())
                            .substitutions(teamSquadDto.getSquadDto().getSubstitutions()).build())
                            .addAttribute("chosenFormation", formation)
                            .addAttribute("positionForInputIdMap", formation.getPositionForInputId());
                },

                () -> model
                        .addAttribute("noSquadSet", "The squad isn't set yet. Choose one"));

        List<SquadDto> savedSquads = squadService.getSquadsByTeamId(teamDto.getId());

        model.addAttribute("savedSquads", savedSquads)
                .addAttribute("matchId", matchId)
                .addAttribute("matchDate", matchService.getMatchById(matchId).get().getDateTime())
                .addAttribute("colors", teamService.getShirtColors(teamDto));

        return "fm/set_squad";
    }

    @PostMapping("/setStartingSquad/{matchId}")
    public String setSquad(
            @PathVariable Integer matchId,
            @AuthenticationPrincipal(expression = "username") String username,
            String loadedSquad,
            Model model) {

        teamService.getActiveTeamByUsername(username).ifPresentOrElse(teamDto -> {

                    squadService.findSquadByNameAndTeamId(loadedSquad, teamDto.getId())
                            .ifPresentOrElse(
                                    (squadDto -> {
                                        @SuppressWarnings("OptionalGetWithoutIsPresent")
                                        Formation formation = Formation.fromFormationNumber(squadDto.getFormationType()).get();

                                        teamSquadService.save(TeamSquadDto.builder()
                                                .squadDto(squadDto)
                                                .teamDto(teamDto)
                                                .matchId(matchId)
                                                .build());

                                        model.addAttribute("chosenFormation", formation)
                                                .addAttribute("eleven", FormationDto.builder().players(squadDto.getPlayers()).substitutions(squadDto.getSubstitutions()).build())
                                                .addAttribute("positionForInputIdMap", formation.getPositionForInputId())
                                                .addAttribute("colors", teamService.getShirtColors(teamDto));
                                    })
                                    , () -> model.addAttribute("notValidSquad", "you must choose valid squad")
                            );
                    //noinspection OptionalGetWithoutIsPresent
                    model.addAttribute("matchDate", matchService.getMatchById(matchId).get().getDateTime())
                            .addAttribute("savedSquads", squadService.getSquadsByTeamId(teamDto.getId()));
                }
                , () -> model.addAttribute("noTeam", "You have to have active team"));

        return "fm/set_squad";
    }

    @GetMapping("/playerStats")
    public String getPlayerStats(
            @RequestParam(required = false) String option,
            @RequestParam(required = false) String attributes,
            @AuthenticationPrincipal(expression = "username") String username,
            Model model) {

        if (option == null || option.equals("general")) {

            Integer leagueId = leagueService.getActiveLeagueByUsername(username).get().getId();
            model.addAttribute("selected", "general");

            if (attributes == null || attributes.equals("goals")) {
                controllerUtil.createModelAttributesForGoals(model, playerService.getGoalsForPlayersInLeagueById(leagueId));
            } else {
                controllerUtil.createModelAttributesForAssists(model, playerService.getAssistsForPlayersInLeagueById(leagueId));
            }

        } else {
            model.addAttribute("selected", "yourTeam");
            teamService.getActiveTeamByUsername(username)
                    .ifPresentOrElse(teamDto -> {
                                if (attributes == null || attributes.equals("goals")) {
                                    controllerUtil.createModelAttributesForGoals(model, playerService.getGoalsForPlayerForTeamById(teamDto.getId()));
                                } else {
                                    controllerUtil.createModelAttributesForAssists(model, playerService.getAssistsForPlayerForTeamById(teamDto.getId()));
                                }
                            },
                            () -> model.addAttribute("noActiveTeam", "You have no active team yet!"));
        }

        return "fm/players_stats";
    }

    @GetMapping("/matchCentre/{matchId}")
    public String showMatchCentre(
            @RequestParam(required = false) String team,
            @RequestParam(name = "statistics", required = false) String statistics,
            @PathVariable Integer matchId,
            @AuthenticationPrincipal(expression = "username") String username,
            Model model) {

        TeamDto teamDto = teamService.getActiveTeamByUsername(username).orElse(null);
        if (statistics != null) {

            matchStatisticService.getMatchStatisticByMatchId(matchId).
                    ifPresentOrElse(matchStatisticDto -> {

                        List<GoalDetailDto> goalsDetails = goalDetailService.getGoalDetailsForMatchId(matchId);

                        System.out.println(goalsDetails);

                        model.addAttribute("matchStatistic", matchStatisticDto)
                                .addAttribute("goalsDetails", goalsDetails);

                    }, () -> {
                        model.addAttribute("notStarted", "Match has not been started yet");
                    });

        } else if (team.equals("your")) {

            teamSquadService.getByMatchIdAndTeamId(teamDto.getId(), matchId).ifPresentOrElse(
                    teamSquadDto -> {

                        Formation formation = Formation.fromFormationNumber(teamSquadDto.getSquadDto().getFormationType()).get();

                        model.addAttribute("eleven", FormationDto.builder().players(teamSquadDto.getSquadDto().getPlayers())
                                .substitutions(teamSquadDto.getSquadDto().getSubstitutions()).build())
                                .addAttribute("chosenFormation", formation)
                                .addAttribute("positionForInputIdMap", formation.getPositionForInputId())
                                .addAttribute("colors", teamService.getShirtColors(teamDto));

                    },

                    () -> model
                            .addAttribute("noSquadSet", "The squad isn't set yet. Choose one"));
        } else if (team.equals("opp")) {
            teamSquadService.getOpponentSquadForMatch(teamDto, matchId)
                    .ifPresentOrElse((teamSquadDto -> {
                        Formation formation = Formation.fromFormationNumber(teamSquadDto.getSquadDto().getFormationType()).get();

                        model.addAttribute("eleven", FormationDto.builder().players(teamSquadDto.getSquadDto().getPlayers())
                                .substitutions(teamSquadDto.getSquadDto().getSubstitutions()).build())
                                .addAttribute("chosenFormation", formation)
                                .addAttribute("positionForInputIdMap", formation.getPositionForInputId())
                                .addAttribute("colors", teamService.getShirtColors(teamSquadDto.getTeamDto()));

                    }), () -> model.addAttribute("noSquadSet", "Opponent did not have set squad yet"));

        }

        model.addAttribute("matchSquadDto", MatchSquadDto.builder().matchId(matchId).teamId(teamDto.getId()).build())
                .addAttribute("matchDate", matchService.getMatchById(matchId).orElseThrow().getDateTime());

        return "fm/match_centre";
    }

    @PostMapping("/loadSquadMatchCentre")
    public String loadSquadInMatchCentre(
            MatchSquadDto matchSquadDto,
            @AuthenticationPrincipal(expression = "username") String username,
            String loadedSquad,
            Model model) {

        final TeamDto teamDto = teamService.getActiveTeamByUsername(username).orElse(null);

        if (loadedSquad == null) {
            model.addAttribute("noSquadSelected", "You need to select one of the saved formations");
        } else {

            Optional<MatchSquadDto> byTeamIdAndMatchId = matchSquadService.getByTeamIdAndMatchId(teamDto.getId(), matchSquadDto.getMatchId());

            if (byTeamIdAndMatchId.isEmpty()) {

                SquadDto squadByName = squadService.findSquadByNameAndTeamId(loadedSquad, teamDto.getId()).orElse(null);
                final Integer formationTypeNumber = squadByName.getFormationType();
                final Formation formation = Formation.fromFormationNumber(formationTypeNumber).get();

                matchSquadDto.setFormation(formation);
                matchSquadDto.setPlayers(squadByName.getPlayers());
                matchSquadDto.setSubstitutionsNumberAvailable(3);
                matchSquadDto.setSubstitutions(squadByName.getSubstitutions());
                matchSquadService.save(matchSquadDto);

                model.addAttribute("chosenFormation", formation)
                        .addAttribute("eleven", FormationDto.builder().players(squadByName.getPlayers()).substitutions(squadByName.getSubstitutions()).build())
                        .addAttribute("positionForInputIdMap", formation.getPositionForInputId());
            } else {

                model.addAttribute("chosenFormation", byTeamIdAndMatchId.get().getFormation())
                        .addAttribute("eleven", FormationDto.builder().players(byTeamIdAndMatchId.get().getPlayers()).substitutions(byTeamIdAndMatchId.get().getSubstitutions()).build())
                        .addAttribute("positionForInputIdMap", byTeamIdAndMatchId.get().getFormation().getPositionForInputId());
            }
        }

        final List<SquadDto> savedSquads = squadService.getSquadsByTeamId(teamDto.getId());

        model.addAttribute("savedSquads", savedSquads);

        return "fm/match_centre";
    }

    @PostMapping("/sendRequestToAdmin")
    public String sendRequestToAdmin(
            @AuthenticationPrincipal(expression = "username") String username,
            String request,
            RedirectAttributes redirectAttributes) {

        String email = userService.getEmailForUsername(username);

        if (request.strip().length() < 20) {
            redirectAttributes.addFlashAttribute("requestToShort", "Request too short. Your request have to be at least 20 characters long");
        } else {

            emailService.sendEmail(EmailType.REQUEST_ADMIN, email, request, email);
            redirectAttributes.addFlashAttribute("requestSend", "Your request has been sent");
        }

        return "redirect:/fm/start";
    }
}

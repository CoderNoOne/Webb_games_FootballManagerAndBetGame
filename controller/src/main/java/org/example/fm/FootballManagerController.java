package org.example.fm;

import lombok.RequiredArgsConstructor;
import org.example.core.EmailService;
import org.example.core.UserService;
import org.example.model.core.enums.EmailType;
import org.example.model.fm.*;
import org.example.model.fm.enums.Formation;
import org.example.util.ControllerUtil;
import org.example.validator.spring_validators.FormationDtoValidator;
import org.example.validator.spring_validators.PlayersNumberDtoValidator;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@SessionAttributes(names = {"backgroundUrl", "username"})
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
                                matchesGroupedByMatchDay.isEmpty() ? "There is not scheduled matches yet" : matchesGroupedByMatchDay)
                                .addAttribute("status", "scheduled");

                    } else if (status.equals("finished")) {

                        Map<Integer, List<MatchDto>> matchesGroupedByMatchDay = matchService.getMatchesGroupedByMatchDay(matchService.getFinishedMatchesForLeague(leagueId));

                        model.addAttribute(matchesGroupedByMatchDay.isEmpty() ? "notFinishedMatches" : "finishedMatchesPerMatchDay",
                                matchesGroupedByMatchDay.isEmpty() ? "There is not finished matches yet" : matchesGroupedByMatchDay)
                                .addAttribute("status", "finished");

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

        teamService.getActiveTeamByUsername(username)
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
                                            player -> String.join(", ", player.getPositions())
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
            FMChooseTeamDto chooseTeam) {

        if (chooseTeam.getLeagueIdTeamId() == null) {
            redirectAttributes.addFlashAttribute("teamNotSelected", "You must enter team name");
        } else {

            //noinspection OptionalGetWithoutIsPresent
            var teamId = chooseTeam.getLeagueIdTeamId().values().stream().findFirst().get();

            teamService.setUserForTeamByTeamId(teamId, username);
        }
        return "redirect:/fm/start";
    }

    @ModelAttribute(name = "backgroundUrl")
    public String getBackgroundUrl(
            @AuthenticationPrincipal(expression = "username") String username) {

        return teamService
                .getActiveTeamByUsername(username)
                .map(TeamDto::getBackgroundUrl)
                .orElse("");

    }

    @GetMapping("/playerNumbers")
    public String getPlayerNumbers(
            @AuthenticationPrincipal(expression = "username") String username,
            Model model) {

        teamService.getActiveTeamByUsername(username).ifPresentOrElse(
                teamDto ->
                        controllerUtil.createModelAttributesForTeam(model, teamDto),
                () -> model
                        .addAttribute("noActiveTeam", MessageFormat.format("There is no active team for username {}", username))
        );

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

        teamService.getActiveTeamByUsername(username).ifPresentOrElse(
                activeTeamByUsername -> {
                    Map<Integer, Integer> currentPlayerNumbers = activeTeamByUsername.getPlayers().stream()
                            .filter(e -> e.getNumber() != null)
                            .collect(HashMap::new, (map, player) -> map.put(player.getId(), player.getNumber()), HashMap::putAll);
                    Long playersCounter = playerService.clearPlayerNumbers(currentPlayerNumbers.keySet());

                    redirectAttributes
                            .addFlashAttribute(
                                    "numberCleared",
                                    MessageFormat.format("Players number has been cleared for {0} players", playersCounter));
                },
                () -> redirectAttributes
                        .addAttribute("noActiveTeam", MessageFormat.format("There is no active team for username {}", username))
        );

        return "redirect:/fm/playerNumbers";
    }

    @PostMapping("/savePlayerNumbers")
    public String savePlayerNumbers(
            @RequestParam(required = false) String randomizedNumbers,
            @AuthenticationPrincipal(expression = "username") String username,
            PlayersNumberDto playersNumberDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        teamService.getActiveTeamByUsername(username).ifPresentOrElse(
                activeTeamByUsername -> {
                    Map<Integer, Integer> currentPlayerNumbers = activeTeamByUsername.getPlayers().stream()
                            .collect(HashMap::new, (map, player) -> map.put(player.getId(), player.getNumber()), HashMap::putAll);

                    if (randomizedNumbers == null) {

                        playersNumberDtoValidator.validate(playersNumberDto, bindingResult);
                        var errors = controllerUtil.bindErrorsSpring(bindingResult);

                        if (bindingResult.hasErrors()) {
                            redirectAttributes.addFlashAttribute("errors", errors);
                            return;
                        }

                        if (!controllerUtil.isNumberNotSelectedYet(playersNumberDto, currentPlayerNumbers)) {
                            redirectAttributes.addFlashAttribute("numbersAlreadySelected", "Some numbers are already selected");
                            return;
                        }
                        redirectAttributes.addFlashAttribute("numbersSet", "Numbers has been successfully set");
                        playerService.savePlayerNumbers(playersNumberDto);

                    } else {

                        List<Integer> playerIdsToAdd = new ArrayList<>();
                        Long playersCounter = playerService.randomizePlayerNumbers(playerIdsToAdd, controllerUtil.getAvailableNumbers(currentPlayerNumbers, playerIdsToAdd));
                        redirectAttributes.addFlashAttribute("randomizedNumbers", MessageFormat.format("Random number has been selected for {0} players", playersCounter));
                    }
                },
                () -> redirectAttributes
                        .addFlashAttribute("noActiveTeam", MessageFormat.format("There is no active team for username {}", username))
        );
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

        controllerUtil.createModelAttributesIfTeamsAreAvailable(model, leagueService.getAllLeaguesByActiveGameWithFetchedTeams());

        return "fm/chooseTeam";
    }

    @GetMapping("/formation")
    public String formation(
            @SessionAttribute(name = "username") String username,
            Model model,
            Formation formation) {

        teamService.getTeamByUsername(username).ifPresentOrElse(
                team -> {
                    model.addAttribute("colors", teamService.getShirtColors(team));

                    controllerUtil.createModelForFormation(model, formation != null ? formation : Formation.FOUR_FOUR_TWO,
                            team.getPlayers(), squadService.getSquadsByTeamId(team.getId()));

                }, () -> model.addAttribute("noActiveTeam", MessageFormat.format("There is no active team for username: {0}", username))
        );
        model.addAttribute("formations", Formation.values());

        return "fm/custom_formation";
    }

    @PostMapping("/changeFormation")
    public String changeFormation(
            RedirectAttributes redirectAttributes,
            Formation chosenFormation) {

        redirectAttributes.addAttribute("formation", chosenFormation);
        return "redirect:/fm/formation";
    }

    @PostMapping("/saveSquad")
    public String saveFormation(
            @SessionAttribute(name = "username") String username,
            @Valid FormationDto eleven,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Formation chosenFormation) {

        formationDtoValidator.validate(eleven, result);

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", controllerUtil.bindErrorsSpring(result));
        } else {

            teamService.getTeamByUsername(username).ifPresentOrElse(teamDto ->
                            redirectAttributes.addFlashAttribute("squadSaved", squadService.saveSquad(controllerUtil.createSquadDto(eleven, chosenFormation, teamDto)) ?
                                    MessageFormat.format("Squad with name: {0} has been overridden", eleven.getName())
                                    : MessageFormat.format("Squad with name: {0} has been saved", eleven.getName()))

                    , () -> redirectAttributes.addFlashAttribute("noActiveTeam", MessageFormat.format("There is no active team for usename: {0}", username)));
        }

        redirectAttributes.addAttribute("formation", chosenFormation);
        return "redirect:/fm/formation";
    }

    @PostMapping(value = "/loadSquad")
    public String loadFormation(
            @SessionAttribute(name = "username") String username,
            String loadedSquad,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {

        if (loadedSquad == null) {
            redirectAttributes.addFlashAttribute("notValidLoadedSquad", "You must load a valid, existing squad");
            return "redirect:/fm/formation";
        } else {

            teamService.getActiveTeamByUsername(username).ifPresentOrElse(teamDto ->
                            squadService.findSquadByNameAndTeamId(loadedSquad, teamDto.getId()).ifPresentOrElse(squadByName ->
                                            redirectAttributes
                                                    .addFlashAttribute("eleven", FormationDto.builder().players(squadByName.getPlayers()).substitutions(squadByName.getSubstitutions()).build())

                                    , () -> redirectAttributes.addFlashAttribute("noSquadFound", MessageFormat.format("There is no active team for usename: {0}", username))),
                    () -> redirectAttributes.addFlashAttribute("noActiveTeam", MessageFormat.format("There is no active team for usename: {0}", username)));

        }

        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("/upcomingMatches")
    public String getUpcomingMatches(
            @SessionAttribute(name = "username") String username,
            Model model) {

        teamService.getActiveTeamByUsername(username).ifPresentOrElse(
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
                , () -> model.addAttribute("noActiveTeam", "You 've no active team yet. Choose one"));

        return "fm/upcoming_matches";
    }

    @GetMapping("/startingSquad/{matchId}")
    public String startingSquad(
            @PathVariable Integer matchId,
            @SessionAttribute(name = "username") String username,
            Model model) {

        teamService.getActiveTeamByUsername(username).ifPresentOrElse(
                teamDto -> {
                    matchSquadService.getByTeamIdAndMatchId(teamDto.getId(), matchId).ifPresentOrElse(
                            matchSquadDto -> {

                                Formation formation = matchSquadDto.getFormation();

                                model.addAttribute("eleven", FormationDto.builder().players(matchSquadDto.getPlayers())
                                        .substitutions(matchSquadDto.getSubstitutions()).build())
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
                },
                () -> model.addAttribute("noActiveTeam", MessageFormat.format("There is no active team for username: {0}", username))
        );
        return "fm/set_squad";
    }

    @PostMapping("/setStartingSquad/{matchId}")
    public String setSquad(
            @PathVariable Integer matchId,
            @SessionAttribute(name = "username") String username,
            String loadedSquad,
            Model model) {

        teamService.getActiveTeamByUsername(username).ifPresentOrElse(teamDto -> {

                    squadService.findSquadByNameAndTeamId(loadedSquad, teamDto.getId())
                            .ifPresentOrElse(
                                    (squadDto -> {
                                        Formation formation = Formation.fromFormationNumber(squadDto.getFormationType());

                                        matchSquadService.save(MatchSquadDto.builder()
                                                .substitutionsNumberAvailable(3)
                                                .matchId(matchId)
                                                .teamDto(teamDto)
                                                .formation(formation)
                                                .players(squadDto.getPlayers())
                                                .substitutions(squadDto.getSubstitutions())
                                                .build());

                                        model.addAttribute("chosenFormation", formation)
                                                .addAttribute("eleven", FormationDto.builder().players(squadDto.getPlayers()).substitutions(squadDto.getSubstitutions()).build())
                                                .addAttribute("positionForInputIdMap", formation.getPositionForInputId())
                                                .addAttribute("colors", teamService.getShirtColors(teamDto));
                                    })
                                    , () -> model.addAttribute("notValidSquad", "you must choose valid squad")
                            );

                    MatchDto matchDto = matchService.getMatchById(matchId).orElse(null);

                    model.addAttribute("matchDate", matchDto != null ? matchDto.getDateTime() : "")
                            .addAttribute("savedSquads", squadService.getSquadsByTeamId(teamDto.getId()));
                }
                , () -> model.addAttribute("noTeam", "You have to have active team"));

        return "fm/set_squad";
    }

    @GetMapping("/playerStats")
    public String getPlayerStats(
            @RequestParam(required = false) String option,
            @RequestParam(required = false) String attributes,
            @SessionAttribute(name = "username") String username,
            Model model) {

        if (option == null || option.equals("general")) {

            model.addAttribute("selected", "general");

            leagueService.getActiveLeagueByUsername(username).ifPresentOrElse(
                    leagueDto -> {

                        if (attributes == null || attributes.equals("goals")) {
                            controllerUtil.createModelAttributesForGoals(model, playerService.getGoalsForPlayersInLeagueById(leagueDto.getId()));
                        } else {
                            controllerUtil.createModelAttributesForAssists(model, playerService.getAssistsForPlayersInLeagueById(leagueDto.getId()));
                        }

                        model.addAttribute("leagueName", leagueDto.getName());
                    },
                    () -> model.addAttribute("noActiveLeague", MessageFormat.format("There is no active league for username {0}", username))
            );

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
            @SessionAttribute(name = "username") String username,
            Model model) {

        teamService.getActiveTeamByUsername(username).ifPresentOrElse(teamDto -> {

                    if (statistics != null) {

                        matchStatisticService.getMatchStatisticByMatchId(matchId).
                                ifPresentOrElse(matchStatisticDto -> {

                                            model.addAttribute("matchStatistic", matchStatisticDto)
                                                    .addAttribute("goalsDetails", goalDetailService.getGoalDetailsForMatchId(matchId));

                                        }, () ->
                                                model.addAttribute("notStarted", "Match has not been started yet")
                                );

                    } else if (team.equals("your")) {

                        matchSquadService.getByTeamIdAndMatchId(teamDto.getId(), matchId).ifPresentOrElse(
                                matchSquadDto ->
                                        controllerUtil.createModelAttributesForUserTeamMatchCentre(model, matchSquadDto, teamService.getShirtColors(teamDto)),
                                () -> model
                                        .addAttribute("noSquadSet", "The squad isn't set yet. Choose one"));
                    } else if (team.equals("opp")) {

                        matchSquadService.getOpponentSquadForMatch(matchId, teamDto.getId())
                                .ifPresentOrElse(matchSquadDto ->
                                                controllerUtil.createModelAttributesForOppTeamMatchCentre(model, matchSquadDto, teamService.getShirtColors(teamDto))
                                        , () -> model.addAttribute("noSquadSet", "Opponent did not have set squad yet"));

                    }
                    model.addAttribute("matchSquadDto", MatchSquadDto.builder().matchId(matchId).teamDto(teamDto).build())
                            .addAttribute("matchDate", matchService.getMatchById(matchId).orElseThrow().getDateTime());

                },
                () -> model
                        .addAttribute("noActiveTeam", MessageFormat.format("No active team for username: {0}", username)));

        return "fm/match_centre";
    }


    @PostMapping("/makeSubstitutions/{matchId}")
    public String makeSubstitutions(
            @SessionAttribute(name = "username") String username,
            @ModelAttribute FormationDto eleven,
            @PathVariable Integer matchId,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request
    ) {

        teamService.getActiveTeamByUsername(username).ifPresentOrElse(
                teamDto -> {

                    Optional<MatchSquadDto> matchSquadDto = matchSquadService.loadByTeamIdAndMatchId(teamDto.getId(), matchId);

                    if (matchSquadDto.isEmpty() || matchService.getStartingTimeForMatchById(matchId).compareTo(LocalDateTime.now()) > 0) {
                        redirectAttributes.addFlashAttribute("matchNotStarted", "Match has not been started yet!");
                        return;
                    }

                    if (matchSquadDto.get().getSubstitutionsNumberAvailable() == 0) {
                        redirectAttributes.addFlashAttribute("noSubsAvailable", "You can't make more than 3 substitutions!");
                        return;
                    }

                    Integer subNumber = matchSquadService.makeSubstitutions(matchId, teamDto.getId(), eleven.getPlayers());

                    if (subNumber == 0) {
                        redirectAttributes.addFlashAttribute("noSubs", "No substitutions has been made");
                        return;
                    }

                    redirectAttributes.addFlashAttribute("subs", subNumber == 1 ? "1 Substitution has been made" : MessageFormat.format("{0} substitutions has been made", subNumber));

                },
                () -> redirectAttributes.addFlashAttribute("noActiveTeam", MessageFormat.format("There is no active team for username: {0}", username))
        );


        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping("/loadSquadMatchCentre")
    public String loadSquadInMatchCentre(
            MatchSquadDto matchSquadDto,
            @SessionAttribute(name = "username") String username,
            String loadedSquad,
            Model model) {

        teamService.getActiveTeamByUsername(username).ifPresentOrElse(teamDto -> {

            if (loadedSquad == null) {
                model.addAttribute("noSquadSelected", "You need to select one of the saved formations");
            } else {

                Optional<MatchSquadDto> byTeamIdAndMatchId = matchSquadService.loadByTeamIdAndMatchId(teamDto.getId(), matchSquadDto.getMatchId());

                if (byTeamIdAndMatchId.isEmpty()) {

                    squadService.findSquadByNameAndTeamId(loadedSquad, teamDto.getId()).ifPresentOrElse(squadDto -> {
                        Formation formation = Formation.fromFormationNumber(squadDto.getFormationType());

                        controllerUtil.setMatchSquadDtoProperties(matchSquadDto, formation, squadDto);
                        matchSquadService.save(matchSquadDto);

                        model.addAttribute("chosenFormation", formation)
                                .addAttribute("eleven", FormationDto.builder()
                                        .players(squadDto.getPlayers())
                                        .substitutions(squadDto.getSubstitutions())
                                        .build())
                                .addAttribute("positionForInputIdMap", formation.getPositionForInputId());

                    }, () -> model.addAttribute("noSquad"));

                } else {

                    model.addAttribute("chosenFormation", byTeamIdAndMatchId.get().getFormation())
                            .addAttribute("eleven", FormationDto.builder().players(byTeamIdAndMatchId.get().getPlayers()).substitutions(byTeamIdAndMatchId.get().getSubstitutions()).build())
                            .addAttribute("positionForInputIdMap", byTeamIdAndMatchId.get().getFormation().getPositionForInputId());
                }
            }
            model.addAttribute("savedSquads", squadService.getSquadsByTeamId(teamDto.getId()));

        }, () -> model.addAttribute(MessageFormat.format("no active Team for username {0}", username)));

        return "fm/match_centre";
    }

    @PostMapping("/sendRequestToAdmin")
    public String sendRequestToAdmin(
            @SessionAttribute(name = "username") String username,
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

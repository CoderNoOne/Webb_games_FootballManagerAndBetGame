package com.app.web_app.controller;

import com.app.web_app.model.BetLeague;
import com.app.web_app.model.BetMatch;
import com.app.web_app.model.bet_game.BetScore;
import com.app.web_app.model.bet_game.ScoreEntity;
import com.app.web_app.dto.BetScoreWrapper;
import com.app.web_app.model.dto.bet_game.ScoreDto;
import com.app.web_app.service.UserService;
import com.app.web_app.service.bet_game.BetPointsService;
import com.app.web_app.service.bet_game.BetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/bet")
@RequiredArgsConstructor
public class BetController {

    private final BetService betService;
    private final BetPointsService betPointsService;
    private final UserService userService;

    @GetMapping({"/home", ""})
    public String betGameHome() {
        return "bet_game/bet_home";
    }

    @GetMapping("/italy")
    public String italy(@AuthenticationPrincipal(expression = "username") String username, Model model) {


        List<Integer> allBetByUsername = betService.findAllBetByUsernameAndLeague(username, BetLeague.ITALY)
                .stream()
                .flatMap(scoreEntity -> scoreEntity.getBetScores()
                        .stream().map(BetScore::getMatchId))
                .collect(Collectors.toList());

        List<BetMatch> scheduledMatches = betService.getScheduledMatchesForItaly().
                stream()
                .filter(match -> !allBetByUsername.contains(match.getId()))
                .collect(Collectors.toList());

        if (scheduledMatches.isEmpty()) {
            model.addAttribute("noMatchesToBet", "There are currently no matches to bet");
        } else {
            model.addAttribute("matchday", scheduledMatches.get(0).getMatchday());
        }

        Map<Integer, BetScore> betScores = new HashMap<>(scheduledMatches.stream().collect(Collectors.toMap(
                BetMatch::getId,
                e -> BetScore.builder()
                        .matchStartingTime(e.getUtcDate())
                        .awayTeamName(e.getAwayTeam().getName())
                        .homeTeamName(e.getHomeTeam().getName())
                        .matchId(e.getId())
                        .matchDay(e.getMatchday())
                        .build()
        )));


        BetScoreWrapper betScoreWrapper = BetScoreWrapper.builder().betScores(betScores).build();

        model
                .addAttribute("scheduledMatches", scheduledMatches)
                .addAttribute("betScoreWrapper", betScoreWrapper);

        return "bet_game/italy/italy";
    }

    @PostMapping("/italy")
    public String postItaly(BetScoreWrapper betScoreWrapper, BindingResult bindingResult, @AuthenticationPrincipal(expression = "username") String username, RedirectAttributes redirectAttributes) {

        ScoreDto scoreDto = ScoreDto.builder().betScores(new ArrayList<>(betScoreWrapper.getBetScores().values())).league(BetLeague.ITALY).build();

        scoreDto.setUsername(username);

        betService.saveBet(scoreDto, BetLeague.ITALY, username);

        redirectAttributes.addFlashAttribute("success", "Match has been successfully bet");

        return "redirect:/bet/italy";
    }

    @GetMapping("italy/stats")
    public String italyStats(@AuthenticationPrincipal(expression = "username") String username, Model model) {

        List<ScoreEntity> allBetByUsername = betService.findAllByUsernameAndLeague(username, BetLeague.ITALY);

        if (allBetByUsername.isEmpty()) {
            model.addAttribute("noBetsYet", "You did not have bet any Serie A matches yet");
        } else {

            List<BetScore> bets = allBetByUsername
                    .stream()
                    .flatMap(scoreEntity -> scoreEntity.getBetScores().stream())
                    .collect(Collectors.toList());
            model
                    .addAttribute("allBets", bets)
                    .addAttribute("matchday", bets.get(0).getMatchDay());
        }


        return "bet_game/italy/italy_bets";
    }

    @GetMapping("/england/stats")
    public String englandStats(@AuthenticationPrincipal(expression = "username") String username, Model model) {


        List<ScoreEntity> allBetByUsername = betService.findAllByUsernameAndLeague(username, BetLeague.PREMIER_LEAGUE);

        if (allBetByUsername.isEmpty()) {
            model.addAttribute("noBetsYet", "You did not have bet any Premier League matches yet");
        } else {

            List<BetScore> bets = allBetByUsername
                    .stream()
                    .flatMap(scoreEntity -> scoreEntity.getBetScores().stream())
                    .collect(Collectors.toList());
            model
                    .addAttribute("allBets", bets)
                    .addAttribute("matchday", bets.get(0).getMatchDay());
        }

        return "bet_game/england/premier_league_bets";
    }

    @GetMapping("/spain")
    public String spain(@AuthenticationPrincipal(expression = "username") String username, Model model) {

        List<Integer> allBetByUsername = betService.findAllBetByUsernameAndLeague(username, BetLeague.SPAIN)
                .stream()
                .flatMap(scoreEntity -> scoreEntity.getBetScores()
                        .stream().map(BetScore::getMatchId))
                .collect(Collectors.toList());

        List<BetMatch> scheduledMatches = betService.getScheduledMatchesForSpain().
                stream()
                .filter(match -> !allBetByUsername.contains(match.getId()))
                .collect(Collectors.toList());

        if (scheduledMatches.isEmpty()) {
            model.addAttribute("noMatchesToBet", "There are currently no matches to bet");
        } else {
            model.addAttribute("matchday", scheduledMatches.get(0).getMatchday());
        }


        Map<Integer, BetScore> betScores = new HashMap<>(scheduledMatches.stream().collect(Collectors.toMap(
                BetMatch::getId,
                e -> BetScore.builder()
                        .matchStartingTime(e.getUtcDate())
                        .awayTeamName(e.getAwayTeam().getName())
                        .homeTeamName(e.getHomeTeam().getName())
                        .matchId(e.getId())
                        .matchDay(e.getMatchday())
                        .build()
        )));


        BetScoreWrapper betScoreWrapper = BetScoreWrapper.builder().betScores(betScores).build();

        model
                .addAttribute("scheduledMatches", scheduledMatches)
                .addAttribute("betScoreWrapper", betScoreWrapper);

        return "bet_game/spain/spain";
    }

    @PostMapping("/spain")
    public String postSpain(BetScoreWrapper betScoreWrapper, BindingResult bindingResult, @AuthenticationPrincipal(expression = "username") String username, RedirectAttributes redirectAttributes) {


        ScoreDto scoreDto = ScoreDto.builder().betScores(new ArrayList<>(betScoreWrapper.getBetScores().values())).league(BetLeague.SPAIN).build();

        scoreDto.setUsername(username);

        betService.saveBet(scoreDto, BetLeague.SPAIN, username);

        redirectAttributes.addFlashAttribute("success", "Match has been successfully bet");

        return "redirect:/bet/spain";
    }


    @GetMapping("spain/stats")
    public String spainStats(@AuthenticationPrincipal(expression = "username") String username, Model model) {

        Map<Integer, List<BetScore>> allBetByUsername = betService.findAllBetScoreByUsernameAndLeagueGroupedByMatchday(username, BetLeague.SPAIN);

        if (allBetByUsername.isEmpty()) {
            model.addAttribute("noBetsYet", "You did not have bet any La Liga matches yet");
        } else {
            model.addAttribute("allBets", allBetByUsername);

        }

        return "bet_game/spain/spain_bets";
    }

    @GetMapping("/premierLeague")
    public String championsLeague(@AuthenticationPrincipal(expression = "username") String username, Model model) {

        List<Integer> allBetByUsername = betService.findAllBetByUsernameAndLeague(username, BetLeague.PREMIER_LEAGUE)
                .stream()
                .flatMap(scoreEntity -> scoreEntity.getBetScores()
                        .stream().map(BetScore::getMatchId))
                .collect(Collectors.toList());

        List<BetMatch> scheduledMatches = betService.getScheduledMatchesForPremierLeague().
                stream()
                .filter(match -> !allBetByUsername.contains(match.getId()))
                .collect(Collectors.toList());

        if (scheduledMatches.isEmpty()) {
            model.addAttribute("noMatchesToBet", "There are currently no matches to bet");
        } else {
            model.addAttribute("matchday", scheduledMatches.get(0).getMatchday());
        }


        Map<Integer, BetScore> betScores = new HashMap<>(scheduledMatches.stream().collect(Collectors.toMap(
                BetMatch::getId,
                e -> BetScore.builder()
                        .matchStartingTime(e.getUtcDate())
                        .awayTeamName(e.getAwayTeam().getName())
                        .homeTeamName(e.getHomeTeam().getName())
                        .matchId(e.getId())
                        .matchDay(e.getMatchday())
                        .build()
        )));


        BetScoreWrapper betScoreWrapper = BetScoreWrapper.builder().betScores(betScores).build();

        model
                .addAttribute("scheduledMatches", scheduledMatches)
                .addAttribute("betScoreWrapper", betScoreWrapper);


        return "bet_game/england/premier_league";
    }

    @PostMapping("/premierLeague")
    public String postChampionsLeague(BetScoreWrapper betScoreWrapper, BindingResult bindingResult, @AuthenticationPrincipal(expression = "username") String username, RedirectAttributes redirectAttributes) {


        ScoreDto scoreDto = ScoreDto.builder().betScores(new ArrayList<>(betScoreWrapper.getBetScores().values())).league(BetLeague.PREMIER_LEAGUE).build();

        scoreDto.setUsername(username);

        betService.saveBet(scoreDto, BetLeague.PREMIER_LEAGUE, username);

        redirectAttributes.addFlashAttribute("success", "Match has been successfully bet");
        return "redirect:/bet/premierLeague";
    }

    @GetMapping("/spain/live")
    public String spainLive(Model model) {

        var liveMatches = betService.getLiveMatchesForSpain();
        if (liveMatches.isEmpty()) {
            model.addAttribute("noLiveMatches", "There is currently no live matches in La Liga");
        } else {
            model.addAttribute("liveMatches", liveMatches)
                    .addAttribute("matchday", liveMatches.get(0).getMatchday());
        }
        return "bet_game/spain/spain_live";
    }

    @GetMapping("/italy/leaderboard")
    public String italyLeaderBoard(Model model) {

        Map<String, Integer> pointsPerUser = betPointsService.getAllByLeagueGroupedByUser(BetLeague.ITALY);

        Map<String, String> photoUrlByUsers = userService.getPhotoUrlForUsernameIn(pointsPerUser.keySet());

        if (pointsPerUser.isEmpty()) {
            model.addAttribute("noBetsYet", "No results yet for any users");
        } else {

            model
                    .addAttribute("pointsPerUser", pointsPerUser)
                    .addAttribute("photoUrlByUsers", photoUrlByUsers);
        }

        return "bet_game/italy/italy_leaderboard";
    }

    @GetMapping("/spain/leaderboard")
    public String spainLeaderBoard(Model model) {

        Map<String, Integer> pointsPerUser = betPointsService.getAllByLeagueGroupedByUser(BetLeague.SPAIN);

        Map<String, String> photoUrlByUsers = userService.getPhotoUrlForUsernameIn(pointsPerUser.keySet());

        if (pointsPerUser.isEmpty()) {
            model.addAttribute("noBetsYet", "No results yet for any users");
        } else {

            model
                    .addAttribute("pointsPerUser", pointsPerUser)
                    .addAttribute("photoUrlByUsers", photoUrlByUsers);
        }
        return "bet_game/spain/spain_leaderboard";
    }

    @GetMapping("/england/leaderboard")
    public String englandLeaderBoard(Model model) {

        Map<String, Integer> pointsPerUser = betPointsService.getAllByLeagueGroupedByUser(BetLeague.PREMIER_LEAGUE);

        Map<String, String> photoUrlByUsers = userService.getPhotoUrlForUsernameIn(pointsPerUser.keySet());

        if (pointsPerUser.isEmpty()) {
            model.addAttribute("noBetsYet", "No results yet for any users");
        } else {

            model
                    .addAttribute("pointsPerUser", pointsPerUser)
                    .addAttribute("photoUrlByUsers", photoUrlByUsers);
        }
        return "bet_game/england/premier_league_leaderboard";
    }

    @GetMapping("italy/live")
    public String italyLive(Model model) {

        var liveMatches = betService.getLiveMatchesForSerieA();
        if (liveMatches.isEmpty()) {
            model.addAttribute("noLiveMatches", "There is currently no live matches in Serie A ");
        } else {

            model.addAttribute("liveMatches", liveMatches)
                    .addAttribute("matchday", liveMatches.get(0).getMatchday());

        }
        return "bet_game/italy/italy_live";
    }

    @GetMapping("/england/live")
    public String englandLive(Model model) {

        var liveMatches = betService.getLiveMatchesForPremierLeague();
        if (liveMatches.isEmpty()) {
            model.addAttribute("noLiveMatches", "There is no currently live matches in Premier League");
        } else {

            model.addAttribute("liveMatches", liveMatches)
                    .addAttribute("matchday", liveMatches.get(0).getMatchday());
        }
        return "bet_game/england/premier_league_live";

    }

    @GetMapping("/generalLeaderboard")
    public String getGeneralLeaderBoard(Model model) {

        Map<String, Integer> allGroupedByUser = betPointsService.getAllGroupedByUser();

        Map<String, String> photoUrlByUsers = userService.getPhotoUrlForUsernameIn(allGroupedByUser.keySet());

        if (allGroupedByUser.isEmpty()) {
            model.addAttribute("noBetsYet", "No results yet for any users");
        } else {

            model
                    .addAttribute("allGroupedByUser", allGroupedByUser)
                    .addAttribute("photoUrlByUsers", photoUrlByUsers);
        }

        return "bet_game/general_leaderboard";

    }
}

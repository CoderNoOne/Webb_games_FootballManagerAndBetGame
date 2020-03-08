package org.example.bet;

import lombok.RequiredArgsConstructor;
import org.example.bet.entity.BetScore;
import org.example.bet.entity.ScoreEntity;
import org.example.bet.enums.BetLeague;
import org.example.core.UserService;
import org.example.model.bet.BetMatch;
import org.example.model.bet.BetScoreWrapper;
import org.example.model.bet.ScoreDto;

import org.example.model.bet.enums.BetLeagueDto;
import org.example.util.ControllerUtil;
import org.example.validator.spring_validators.BetScoreWrapperValidator;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/bet")
@RequiredArgsConstructor
public class BetController {

    private final BetService betService;
    private final UserService userService;
    private final ControllerUtil controllerUtil;
    private final BetScoreWrapperValidator betScoreWrapperValidator;
    private final BetPointsService betPointsService;

    @GetMapping({"/home", ""})
    public String betGameHome() {
        return "bet_game/bet_home";
    }

    @GetMapping("/italy")
    public String italy(@AuthenticationPrincipal(expression = "username") String username, Model model) {

        List<Integer> allBetByUsername = controllerUtil.getAllMatchesIdBetByUsername(
                betService.findAllBetByUsernameAndLeague(username, BetLeague.ITALY));

        List<BetMatch> scheduledMatchesToBet =
                controllerUtil.getScheduledMatchesNotYetBetByUsername(betService.getScheduledMatchesForItaly(), allBetByUsername);

        if (scheduledMatchesToBet.isEmpty()) {
            model.addAttribute("noMatchesToBet", "There are currently no matches to bet");
            return "bet_game/italy/italy";
        }

        model
                .addAttribute("scheduledMatches", scheduledMatchesToBet)
                .addAttribute("betScoreWrapper", controllerUtil.createBetScoreWrapper(scheduledMatchesToBet))
                .addAttribute("matchday", scheduledMatchesToBet.get(0).getMatchday());

        return "bet_game/italy/italy";
    }

    @PostMapping("/italy")
    public String postItaly(BetScoreWrapper betScoreWrapper, BindingResult bindingResult, @AuthenticationPrincipal(expression = "username") String username, RedirectAttributes redirectAttributes) {

        betScoreWrapperValidator.validate(betScoreWrapper, bindingResult);
        Map<String, String> errors = controllerUtil.bindErrorsSpring(bindingResult);

        if (!errors.isEmpty()) {
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/bet/italy";
        }

        ScoreDto scoreDto = ScoreDto.builder().username(username).betScores(new ArrayList<>(betScoreWrapper.getBetScores().values())).league(BetLeagueDto.ITALY).build();
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

        List<Integer> allBetByUsername = controllerUtil.getAllMatchesIdBetByUsername(
                betService.findAllBetByUsernameAndLeague(username, BetLeague.SPAIN));

        List<BetMatch> scheduledMatchesToBet =
                controllerUtil.getScheduledMatchesNotYetBetByUsername(betService.getScheduledMatchesForSpain(), allBetByUsername);

        if (scheduledMatchesToBet.isEmpty()) {
            model.addAttribute("noMatchesToBet", "There are currently no matches to bet");
            return "bet_game/spain/spain";
        }

        model
                .addAttribute("scheduledMatches", scheduledMatchesToBet)
                .addAttribute("betScoreWrapper", controllerUtil.createBetScoreWrapper(scheduledMatchesToBet))
                .addAttribute("matchday", scheduledMatchesToBet.get(0).getMatchday());

        return "bet_game/spain/spain";
    }

    @PostMapping("/spain")
    public String postSpain(BetScoreWrapper betScoreWrapper, BindingResult bindingResult, @AuthenticationPrincipal(expression = "username") String username, RedirectAttributes redirectAttributes) {

        betScoreWrapperValidator.validate(betScoreWrapper, bindingResult);
        Map<String, String> errors = controllerUtil.bindErrorsSpring(bindingResult);

        if (!errors.isEmpty()) {
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/bet/spain";
        }

        ScoreDto scoreDto = ScoreDto.builder().username(username).betScores(new ArrayList<>(betScoreWrapper.getBetScores().values())).league(BetLeagueDto.SPAIN).build();
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

        List<Integer> allBetByUsername = controllerUtil.getAllMatchesIdBetByUsername(
                betService.findAllBetByUsernameAndLeague(username, BetLeague.PREMIER_LEAGUE));

        List<BetMatch> scheduledMatchesToBet =
                controllerUtil.getScheduledMatchesNotYetBetByUsername(betService.getScheduledMatchesForPremierLeague(), allBetByUsername);

        if (scheduledMatchesToBet.isEmpty()) {
            model.addAttribute("noMatchesToBet", "There are currently no matches to bet");
            return "bet_game/england/premier_league";
        }

        model
                .addAttribute("scheduledMatches", scheduledMatchesToBet)
                .addAttribute("betScoreWrapper", controllerUtil.createBetScoreWrapper(scheduledMatchesToBet))
                .addAttribute("matchday", scheduledMatchesToBet.get(0).getMatchday());

        return "bet_game/england/premier_league";
    }

    @PostMapping("/premierLeague")
    public String postChampionsLeague(BetScoreWrapper betScoreWrapper, BindingResult bindingResult, @AuthenticationPrincipal(expression = "username") String username, RedirectAttributes redirectAttributes) {

        betScoreWrapperValidator.validate(betScoreWrapper, bindingResult);
        Map<String, String> errors = controllerUtil.bindErrorsSpring(bindingResult);

        if (!errors.isEmpty()) {
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/bet/premierLeague";
        }

        ScoreDto scoreDto = ScoreDto.builder().username(username).betScores(new ArrayList<>(betScoreWrapper.getBetScores().values())).league(BetLeagueDto.PREMIER_LEAGUE).build();
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

        Map<String, Integer> pointsPerUser = betPointsService.getAllByLeagueGroupedByUser(BetLeagueDto.ITALY);

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

        Map<String, Integer> pointsPerUser = betPointsService.getAllByLeagueGroupedByUser(BetLeagueDto.SPAIN);
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

        Map<String, Integer> pointsPerUser = betPointsService.getAllByLeagueGroupedByUser(BetLeagueDto.PREMIER_LEAGUE);

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

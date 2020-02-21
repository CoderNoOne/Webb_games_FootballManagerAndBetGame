package com.app.web_app.controller;

import com.app.web_app.model.admin.LeagueBaseDto;
import com.app.web_app.model.admin.repository.Impl.LeagueBaseDtoRepository;
import com.app.web_app.model.admin.repository.Impl.TeamBaseRepository;
import com.app.web_app.model.manager_game.League;
import com.app.web_app.model.manager_game.LeagueType;
import com.app.web_app.model.manager_game.dto.LeagueDto;
import com.app.web_app.model.manager_game.dto.LeagueTeamsDto;
import com.app.web_app.model.manager_game.dto.TeamDto;
import com.app.web_app.model.manager_game.mapper.ManagerMapper;
import com.app.web_app.service.AdminService;
import com.app.web_app.service.BaseLeague;
import com.app.web_app.service.ScheduleService;
import com.app.web_app.utils.ControllerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final LeagueBaseDtoRepository leagueBaseDtoRepository;
    private final TeamBaseRepository teamBaseRepository;
    private final ManagerMapper managerMapper;
    private final ControllerUtil controllerUtil;
    private final ScheduleService scheduleService;

    @GetMapping("")
    public String adminPage(Model model, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {

        int currentPage = page != null ? page : 1;
        int pageSize = size != null ? size : 5;

        Page<League> leaguePage = adminService.getPaginatedGames(PageRequest.of(currentPage - 1, pageSize));

        int totalPages = leaguePage.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());

            model.addAttribute("pageNumbers", pageNumbers);
        }

        Map<String, List<TeamDto>> teamsByLeague = leagueBaseDtoRepository.findAll()
                .stream()
                .collect(Collectors.toMap(
                        LeagueBaseDto::getName,
                        league -> teamBaseRepository.findAllByLeagueName(league.getName()).stream().map(managerMapper::mapTeamBaseToDto).collect(Collectors.toList())
                ));

        LeagueTeamsDto leagueTeams = LeagueTeamsDto.builder()
                .leagueTeams(teamsByLeague)
                .build();

        model
                .addAttribute("leaguePage", leaguePage)
                .addAttribute("baseLeagues", BaseLeague.values())
                .addAttribute("chosenLeague", new LeagueDto())
                .addAttribute("teamsForLeague", leagueTeams);

        return "admin/admin";
    }

    @GetMapping("/deleteGame/{id}")
    public String deleteGameById(@PathVariable Integer id, HttpServletRequest request) {

        adminService.deleteGameById(id);

        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping("/createLeagues")
    public String generateLeagues(@Valid LeagueDto chosenLeague, BindingResult bindingResult, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        Map<String, String> errors = controllerUtil.bindErrorsHibernateFields(bindingResult);
        redirectAttributes.addFlashAttribute("errors", errors);

        if (errors.isEmpty()) {

            String[] values = chosenLeague.getMatchTime().split("[:]");
            int hour = Integer.parseInt(values[0]);
            int minutes = Integer.parseInt(values[1]);

            TeamDto[] teams = Arrays.stream(chosenLeague.getTeams())
                    .map(name -> TeamDto.builder()
                            .name(name)
                            .build())
                    .toArray(TeamDto[]::new);

            var matches = adminService.generateLeague(LeagueType.CUSTOM, chosenLeague.getName(), chosenLeague.getStartDate().atTime(hour, minutes), teams);
            scheduleService.simulateFmMatches(matches);

        }
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("/getUsersForGame/{id}")
    public String getUsers(@PathVariable Integer id, Model model) {

        model.addAttribute("gameId", id);
        return "admin/users_for_game";
    }


}

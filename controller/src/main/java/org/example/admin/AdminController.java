package org.example.admin;

import lombok.RequiredArgsConstructor;
import org.example.model.admin.LeagueBaseDto;
import org.example.model.admin.LeagueTeamsDto;
import org.example.model.admin.enums.BaseLeague;
import org.example.core.ScheduleService;
import org.example.model.fm.LeagueDto;
import org.example.model.fm.TeamDto;
import org.example.entity.fm.entity.League;
import org.example.entity.fm.enums.LeagueType;
import org.example.repository.admin.Impl.LeagueBaseDtoRepository;
import org.example.repository.admin.Impl.TeamBaseRepository;
import org.example.util.ControllerUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    private final ControllerUtil controllerUtil;
    private final ScheduleService scheduleService;

    @GetMapping
    public String adminPage(
            Model model,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        int currentPage = page != null ? page : 1;
        int pageSize = size != null ? size : 5;

        Page<League> leaguePage = adminService.getPaginatedGames(PageRequest.of(currentPage - 1, pageSize));

        int totalPages = leaguePage.getTotalPages();

        if (totalPages > 0) {
            model.addAttribute("pageNumbers", IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList()));
        }

        LeagueTeamsDto leagueTeams = LeagueTeamsDto.builder()
                .leagueTeams(leagueBaseDtoRepository.findAll()
                        .stream()
                        .collect(Collectors.toMap(
                                LeagueBaseDto::getName,
                                league -> teamBaseRepository.findAllByLeagueName(league.getName()).stream().map(AdminMapper::mapTeamBaseToDto).collect(Collectors.toList())
                        )))
                .build();

        model
                .addAttribute("leaguePage", leaguePage)
                .addAttribute("baseLeagues", BaseLeague.values())
                .addAttribute("chosenLeague", new LeagueDto())
                .addAttribute("teamsForLeague", leagueTeams);

        return "admin/admin";
    }

    @GetMapping("/deleteGame/{id}")
    public String deleteGameById(
            @PathVariable Integer id,
            HttpServletRequest request) {

        adminService.deleteGameById(id);
        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping("/createLeagues")
    public String generateLeagues(
            @Valid LeagueDto chosenLeague,
            BindingResult bindingResult,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        Map<String, String> errors = controllerUtil.bindErrorsHibernateFields(bindingResult);
        redirectAttributes.addFlashAttribute("errors", errors);

        if (errors.isEmpty()) {

            String[] values = chosenLeague.getMatchTime().split("[:]");
            int hour = Integer.parseInt(values[0]);
            int minutes = Integer.parseInt(values[1]);

            TeamDto[] teams = controllerUtil.getTeamsForNames(chosenLeague.getTeams());

            var matches = adminService.generateLeague(LeagueType.CUSTOM, chosenLeague.getName(), chosenLeague.getStartDate().atTime(hour, minutes), teams);
            scheduleService.simulateFmMatches(matches);
        }
        return "redirect:" + request.getHeader("Referer");
    }
}

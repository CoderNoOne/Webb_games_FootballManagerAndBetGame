package com.app.web_app.utils;

import com.app.web_app.exceptions.AppException;
import com.app.web_app.model.manager_game.dto.PlayerDto;
import com.app.web_app.model.manager_game.dto.TeamDto;
import com.app.web_app.model.manager_game.dto.TeamStandingsDto;
import com.app.web_app.model.manager_game.service.PlayerService;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        String[] scores = result.split("[:]");

        int homeTeam = isHome ? 0 : 1;
        int awayTeam = isHome ? 1 : 0;

        int score = Integer.parseInt(scores[homeTeam]) - Integer.parseInt(scores[awayTeam]);


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

    public MultipartFile saveFile(File file) {

        try (FileInputStream fileInputStream = new FileInputStream(file)) {

            FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
            IOUtils.copy(fileInputStream, fileItem.getOutputStream());

            return new CommonsMultipartFile(fileItem);
        } catch (IOException exception) {
            log.error(Arrays.toString(exception.getStackTrace()));
            throw new AppException("File conversion error ");
        }

    }

    public void createModelAttributesForGoals(Model model, Map<PlayerDto, Integer> goalsForTeamPlayers) {

        model.addAttribute("goalsForPlayers", goalsForTeamPlayers)
                .addAttribute("teamsForPlayers", playerService.getTeamsForPlayers(goalsForTeamPlayers.keySet()));
    }

    public void createModelAttributesForAssists(Model model, Map<PlayerDto, Integer> assistsForPlayers) {

        model.addAttribute("assistsForPlayers", assistsForPlayers)
                .addAttribute("teamsForPlayers", playerService.getTeamsForPlayers(assistsForPlayers.keySet()));
    }
}

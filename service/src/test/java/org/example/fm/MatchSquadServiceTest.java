package org.example.fm;

import org.example.core.exceptions.AppException;
import org.example.fm.entity.Match;
import org.example.fm.entity.MatchSquad;
import org.example.fm.entity.Player;
import org.example.fm.entity.Team;
import org.example.model.fm.MatchDto;
import org.example.model.fm.MatchSquadDto;
import org.example.model.fm.PlayerDto;
import org.example.model.fm.TeamDto;
import org.example.model.fm.enums.Formation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.security.auth.login.AppConfigurationEntry;
import java.text.MessageFormat;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
class MatchSquadServiceTest {

    @Mock
    private MatchSquadRepository matchSquadRepository;
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private MatchRepository matchRepository;
    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private MatchSquadService matchSquadService;


    @Test
    @DisplayName("save : case MatchSquadDto is null. Exception should be thrown")
    void test1() {

        //given
        String exceptionMessage = "MatchSquadDto is null";
        MatchSquadDto matchSquadDto = null;

        //when
        AppException appException = assertThrows(AppException.class, () -> matchSquadService.save(matchSquadDto));

        //then
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));
        then(matchRepository).shouldHaveNoInteractions();
        then(playerRepository).shouldHaveNoInteractions();
        then(matchSquadRepository).shouldHaveNoInteractions();
        then(teamRepository).shouldHaveNoInteractions();

    }

    @Test
    @DisplayName("save: case 1.MatchSquadDto is not null 2. TeamDto id is null")
    void test2() {

        //given
        MatchSquadDto matchSquadDto = MatchSquadDto.builder()
                .teamDto(TeamDto.builder()
                        .id(null)
                        .build())
                .build();

        String exceptionMessage = "TeamDto or team id is null";

        //when
        AppException appException = assertThrows(AppException.class, () -> matchSquadService.save(matchSquadDto));

        //then
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));
        then(matchRepository).shouldHaveNoInteractions();
        then(playerRepository).shouldHaveNoInteractions();
        then(matchSquadRepository).shouldHaveNoInteractions();
        then(teamRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("save: case MatchId is null")
    void test3() {

        //given
        MatchSquadDto matchSquadDto = MatchSquadDto.builder()
                .teamDto(TeamDto.builder()
                        .id(1)
                        .build())
                .matchId(null)
                .build();

        String exceptionMessage = "Match id is null";

        //when
        AppException appException = assertThrows(AppException.class, () -> matchSquadService.save(matchSquadDto));

        //then
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));
        then(matchRepository).shouldHaveNoInteractions();
        then(playerRepository).shouldHaveNoInteractions();
        then(matchSquadRepository).shouldHaveNoInteractions();
        then(teamRepository).shouldHaveNoInteractions();

    }


    @Test
    @DisplayName("save: formation or formation number is null")
    void test4() {

        //given
        String exceptionMessage = "Formation or formation number is null";

        MatchSquadDto matchSquadDto = MatchSquadDto.builder()
                .matchId(1)
                .teamDto(TeamDto.builder()
                        .id(1)
                        .build())
                .formation(null)
                .build();

        //when
        AppException appException = assertThrows(AppException.class, () -> matchSquadService.save(matchSquadDto));

        //then
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));
        then(matchRepository).shouldHaveNoInteractions();
        then(playerRepository).shouldHaveNoInteractions();
        then(matchSquadRepository).shouldHaveNoInteractions();
        then(teamRepository).shouldHaveNoInteractions();

    }

    @Test
    @DisplayName("save: case players are null")
    void test5() {

        //given
        String exceptionMessage = "Players map or entry key/value is null";
        MatchSquadDto matchSquadDto = MatchSquadDto.builder()
                .matchId(1)
                .teamDto(TeamDto.builder()
                        .id(1)
                        .build())
                .formation(Formation.FOUR_FOUR_TWO)
                .substitutions(new HashMap<>())
                .players(null)
                .build();

        //when
        AppException appException = assertThrows(AppException.class, () -> matchSquadService.save(matchSquadDto));

        //then
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));
        then(matchRepository).shouldHaveNoInteractions();
        then(playerRepository).shouldHaveNoInteractions();
        then(matchSquadRepository).shouldHaveNoInteractions();
        then(teamRepository).shouldHaveNoInteractions();

    }

    @Test
    @DisplayName("save: case players entry key/value is null")
    void test6() {

        //given
        TeamDto teamDto = TeamDto.builder()
                .id(1)
                .build();

        Integer matchId = 1;

        HashMap<String, PlayerDto> players = new HashMap<>();
        players.put(null, null);

        MatchSquadDto matchSquadDto = MatchSquadDto.builder()
                .matchId(matchId)
                .teamDto(teamDto)
                .substitutionsNumberAvailable(3)
                .formation(Formation.FOUR_FOUR_TWO)
                .players(players)
                .substitutions(new HashMap<>())
                .build();

        String exceptionMessage = "Players map or entry key/value is null";

        //when
        AppException appException = assertThrows(AppException.class, () -> matchSquadService.save(matchSquadDto));


        //then
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));
        then(matchSquadRepository).shouldHaveNoInteractions();
        then(matchRepository).shouldHaveNoInteractions();
        then(playerRepository).shouldHaveNoInteractions();
        then(teamRepository).shouldHaveNoInteractions();

    }

    @Test
    @DisplayName("save: case substitutions are null")
    void test7() {

        //given
        String exceptionMessage = "Substitutions map or entry key/value is null";
        MatchSquadDto matchSquadDto = MatchSquadDto.builder()
                .matchId(1)
                .teamDto(TeamDto.builder()
                        .id(1)
                        .build())
                .formation(Formation.FOUR_FOUR_TWO)
                .substitutions(null)
                .players(new HashMap<>())
                .build();

        //when
        AppException appException = assertThrows(AppException.class, () -> matchSquadService.save(matchSquadDto));

        //then
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));
        then(matchRepository).shouldHaveNoInteractions();
        then(playerRepository).shouldHaveNoInteractions();
        then(matchSquadRepository).shouldHaveNoInteractions();
        then(teamRepository).shouldHaveNoInteractions();

    }

    @Test
    @DisplayName("save: case substitutions entry key/value is null")
    void test8() {

        //given
        TeamDto teamDto = TeamDto.builder()
                .id(1)
                .build();

        Integer matchId = 1;

        HashMap<String, PlayerDto> substitutions = new HashMap<>();
        substitutions.put(null, null);

        MatchSquadDto matchSquadDto = MatchSquadDto.builder()
                .matchId(matchId)
                .teamDto(teamDto)
                .substitutionsNumberAvailable(3)
                .formation(Formation.FOUR_FOUR_TWO)
                .players(new HashMap<>())
                .substitutions(substitutions)
                .build();

        String exceptionMessage = "Substitutions map or entry key/value is null";

        //when
        AppException appException = assertThrows(AppException.class, () -> matchSquadService.save(matchSquadDto));


        //then
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));
        then(matchSquadRepository).shouldHaveNoInteractions();
        then(matchRepository).shouldHaveNoInteractions();
        then(playerRepository).shouldHaveNoInteractions();
        then(teamRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("save: case MatchSquad exists in DB")
    void test9() {

        //given
        TeamDto teamDto = TeamDto.builder()
                .id(1)
                .build();

        Integer matchId = 1;

        MatchSquadDto matchSquadDto = MatchSquadDto.builder()
                .matchId(matchId)
                .teamDto(teamDto)
                .substitutionsNumberAvailable(3)
                .formation(Formation.FOUR_FOUR_TWO)
                .players(new HashMap<>())
                .substitutions(new HashMap<>())
                .build();


        Optional<MatchSquadDto> expectedResult = Optional.of(
                MatchSquadDto.builder()
                        .id(1)
                        .substitutionsNumberAvailable(3)
                        .teamDto(TeamDto.builder()
                                .id(teamDto.getId())
                                .players(new HashSet<>())
                                .build())
                        .matchId(matchId)
                        .formation(Formation.FOUR_FOUR_TWO)
                        .players(new HashMap<>())
                        .substitutions(new HashMap<>())
                        .build()
        );

        given(matchSquadRepository.findByTeamIdAndMatchId(teamDto.getId(), matchId))
                .willReturn(Optional.of(
                        MatchSquad.builder()
                                .id(1)
                                .substitutionsAvailable(3)
                                .team(Team.builder()
                                        .id(1)
                                        .build())
                                .match(Match.builder()
                                        .id(1)
                                        .build())
                                .build()
                ));


        //when
        Optional<MatchSquadDto> actual = assertDoesNotThrow(() -> matchSquadService.save(matchSquadDto));

        //then
        assertThat(actual, is(equalTo(expectedResult)));
        then(matchSquadRepository).should(times(1)).findByTeamIdAndMatchId(teamDto.getId(), matchId);
        then(matchSquadRepository).shouldHaveNoMoreInteractions();
        then(matchRepository).shouldHaveNoInteractions();
        then(playerRepository).shouldHaveNoInteractions();
        then(teamRepository).shouldHaveNoInteractions();

    }

    @Test
    @DisplayName("save: case MatchSquadDto doesn't exist in DB yet")
    void test10() {

        //given
        MatchSquadDto matchSquadDto = MatchSquadDto.builder()
                .players(Map.of("LCF", PlayerDto.builder().id(1).build()))
                .substitutions(new HashMap<>())
                .formation(Formation.FOUR_FOUR_TWO)
                .substitutionsNumberAvailable(3)
                .teamDto(TeamDto.builder()
                        .id(1)
                        .build())
                .matchId(1)
                .build();

        given(matchSquadRepository.findByTeamIdAndMatchId(1, 1))
                .willReturn(Optional.empty());

        given(matchRepository.findById(matchSquadDto.getMatchId()))
                .willReturn(Optional.of(
                        Match.builder()
                                .id(matchSquadDto.getMatchId())
                                .build()));

        given(teamRepository.findById(matchSquadDto.getTeamDto().getId()))
                .willReturn(Optional.of(
                        Team.builder()
                                .id(matchSquadDto.getTeamDto().getId())
                                .build()
                ));

        given(playerRepository.findById(1))
                .willReturn(Optional.of(
                        Player.builder()
                                .id(1)
                                .build()
                ));

        given(matchSquadRepository.save(any(MatchSquad.class)))
                .willReturn(
                        MatchSquad.builder()
                                .id(1)
                                .team(Team.builder()
                                        .id(1)
                                        .build())
                                .match(Match.builder()
                                        .id(1)
                                        .build())
                                .substitutionsAvailable(3)
                                .formationType(Formation.FOUR_FOUR_TWO.getNumber())
                                .players(Map.of("LCF", Player.builder().id(1).build()))
                                .build()
                );

        Optional<MatchSquadDto> expectedResult = Optional.of(
                MatchSquadDto.builder()
                        .id(1)
                        .matchId(1)
                        .teamDto(TeamDto.builder()
                                .id(1)
                                .players(new HashSet<>())
                                .build())
                        .substitutionsNumberAvailable(3)
                        .formation(Formation.FOUR_FOUR_TWO)
                        .substitutions(new HashMap<>())
                        .players(Map.of("LCF", PlayerDto.builder().id(1).positions(Collections.emptySet()).build()))
                        .build()

        );

        //when
        Optional<MatchSquadDto> actual = assertDoesNotThrow(() -> matchSquadService.save(matchSquadDto));

        //then
        assertThat(actual, is(equalTo(expectedResult)));

        InOrder inOrder = Mockito.inOrder(matchSquadRepository, matchRepository, teamRepository, playerRepository);
        inOrder.verify(matchSquadRepository).findByTeamIdAndMatchId(1, 1);
        inOrder.verify(matchRepository).findById(1);
        inOrder.verify(teamRepository).findById(1);
        inOrder.verify(playerRepository).findById(1);
        inOrder.verify(matchSquadRepository).save(any());
        inOrder.verifyNoMoreInteractions();

    }

    @Test
    @DisplayName("LoadByTeamIdAndMatchId Case: teamId is null")
    void test11() {

        //given
        Integer teamId = null;
        Integer matchId = 1;
        String exceptionMessage = "Team id is null";

        //when
        AppException appException = assertThrows(
                AppException.class,
                () -> matchSquadService.loadByTeamIdAndMatchId(teamId, matchId)
        );

        //then
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));

        then(matchSquadRepository).shouldHaveNoInteractions();
        then(matchRepository).shouldHaveNoInteractions();
        then(playerRepository).shouldHaveNoInteractions();
        then(teamRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("LoadByTeamIdAndMatchId Case: matchId is null")
    void test12() {

        //given
        Integer teamId = 1;
        Integer matchId = null;
        String exceptionMessage = "Match id is null";

        //when
        AppException appException = assertThrows(
                AppException.class,
                () -> matchSquadService.loadByTeamIdAndMatchId(teamId, matchId)
        );

        //then
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));

        then(matchSquadRepository).shouldHaveNoInteractions();
        then(matchRepository).shouldHaveNoInteractions();
        then(playerRepository).shouldHaveNoInteractions();
        then(teamRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("LoadByTeamIdAndMatchId Case: non null arguments")
    void test13() {

        //given
        Integer teamId = 1;
        Integer matchId = 1;

        Optional<MatchSquadDto> expectedResult = Optional.of(
                MatchSquadDto.builder()
                        .id(1)
                        .players(new HashMap<>())
                        .substitutions(new HashMap<>())
                        .build()
        );

        given(matchSquadRepository.findByTeamIdAndMatchId(teamId, matchId))
                .willReturn(Optional.of(
                        MatchSquad.builder()
                                .id(1)
                                .build()
                ));

        //when
        Optional<MatchSquadDto> actual = assertDoesNotThrow(() -> matchSquadService.loadByTeamIdAndMatchId(teamId, matchId));

        //then
        assertThat(actual, is(equalTo(expectedResult)));
        then(matchSquadRepository).should(times(1)).findByTeamIdAndMatchId(teamId, matchId);
        then(matchSquadRepository).shouldHaveNoMoreInteractions();
        then(matchRepository).shouldHaveNoInteractions();
        then(teamRepository).shouldHaveNoInteractions();
        then(playerRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("Make substitutions. Case matchId is null")
    void test14() {

        //given
        String exceptionMessage = "Match id is null";
        Integer matchId = null;
        Integer teamId = 1;
        Map<String, PlayerDto> subs = new HashMap<>();

        //when
        AppException appException = assertThrows(AppException.class, () -> matchSquadService.makeSubstitutions(matchId, teamId, subs));

        //then
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));

        then(matchSquadRepository).shouldHaveNoInteractions();
        then(matchRepository).shouldHaveNoInteractions();
        then(playerRepository).shouldHaveNoInteractions();
        then(teamRepository).shouldHaveNoInteractions();

    }

    @Test
    @DisplayName("Make substitutions. Case teamId is null")
    void test15() {

        //given
        String exceptionMessage = "Team id is null";
        Integer matchId = 1;
        Integer teamId = null;
        Map<String, PlayerDto> subs = new HashMap<>();

        //when
        AppException appException = assertThrows(AppException.class, () -> matchSquadService.makeSubstitutions(matchId, teamId, subs));

        //then
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));

        then(matchSquadRepository).shouldHaveNoInteractions();
        then(matchRepository).shouldHaveNoInteractions();
        then(playerRepository).shouldHaveNoInteractions();
        then(teamRepository).shouldHaveNoInteractions();

    }

    @Test
    @DisplayName("Make substitutions. Case subs is null")
    void test16() {

        //given
        String exceptionMessage = "Subs map is null";
        Integer matchId = 1;
        Integer teamId = 1;
        Map<String, PlayerDto> subs = null;

        //when
        AppException appException = assertThrows(AppException.class, () -> matchSquadService.makeSubstitutions(matchId, teamId, subs));

        //then
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));

        then(matchSquadRepository).shouldHaveNoInteractions();
        then(matchRepository).shouldHaveNoInteractions();
        then(playerRepository).shouldHaveNoInteractions();
        then(teamRepository).shouldHaveNoInteractions();

    }

    @Test
    @DisplayName("Make substitutions. Case MatchSquad doesn't exist in DB")
    void test17() {

        //given
        Integer matchId = 1;
        Integer teamId = 1;
        Map<String, PlayerDto> subs = new HashMap<>();
        String exceptionMessage = MessageFormat.format("No match squad for (matchId {0}, {1})", matchId, teamId);
        given(matchSquadRepository.findByTeamIdAndMatchId(teamId, matchId)).willReturn(Optional.empty());

        //when
        AppException appException = assertThrows(AppException.class, () -> matchSquadService.makeSubstitutions(matchId, teamId, subs));

        //then
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));

        then(matchSquadRepository).should(times(1)).findByTeamIdAndMatchId(teamId, matchId);
        then(matchSquadRepository).shouldHaveNoMoreInteractions();
        then(matchRepository).shouldHaveNoInteractions();
        then(playerRepository).shouldHaveNoInteractions();
        then(teamRepository).shouldHaveNoInteractions();

    }

    @Test
    @DisplayName("Make substitutions. Case MatchSquad exists in DB. Player doesn't exist")
    void test18() {

        //given
        Integer matchId = 1;
        Integer teamId = 1;

        Integer playerId = 1;
        Map<String, PlayerDto> subs = Map.of(
                "1", PlayerDto.builder()
                        .id(playerId)
                        .build()
        );

        String exceptionMessage = MessageFormat.format("Player with id: {0} doesn't exist", playerId);

        given(matchSquadRepository.findByTeamIdAndMatchId(teamId, matchId))
                .willReturn(Optional.of(
                        MatchSquad.builder()
                                .id(1)
                                .team(Team.builder()
                                        .id(1)
                                        .build())
                                .match(Match.builder()
                                        .id(1)
                                        .build())
                                .build()
                ));

        given(playerRepository.findPlayersByIdIn(List.of(playerId))).willReturn(Collections.emptyList());

        //when
        AppException appException = assertThrows(AppException.class, () -> matchSquadService.makeSubstitutions(matchId, teamId, subs));

        //then

        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));

        InOrder inOrder = Mockito.inOrder(matchSquadRepository, matchRepository, teamRepository, playerRepository);
        inOrder.verify(matchSquadRepository).findByTeamIdAndMatchId(teamId, matchId);
        inOrder.verify(playerRepository).findPlayersByIdIn(List.of(playerId));
        inOrder.verifyNoMoreInteractions();

    }

    @Test
    @DisplayName("Make substitutions. Case MatchSquad exists in DB. Player exists")
    void test19() {

        //given
        Integer matchId = 1;
        Integer teamId = 1;

        Integer playerId = 1;
        Map<String, PlayerDto> subs = Map.of(
                "1", PlayerDto.builder()
                        .id(playerId)
                        .build()
        );

        Integer expectedResult = 1;

        given(matchSquadRepository.findByTeamIdAndMatchId(teamId, matchId))
                .willReturn(Optional.of(
                        MatchSquad.builder()
                                .id(1)
                                .team(Team.builder()
                                        .id(1)
                                        .build())
                                .match(Match.builder()
                                        .id(1)
                                        .build())
                                .players(new HashMap<>())
                                .substitutionsAvailable(3)
                                .build()
                ));

        given(playerRepository.findPlayersByIdIn(List.of(playerId)))
                .willReturn(List.of(
                        Player.builder()
                                .id(1)
                                .build()
                ));
        //when

        Integer actual = assertDoesNotThrow(() -> matchSquadService.makeSubstitutions(matchId, teamId, subs));
        //then

        assertThat(actual, is(equalTo(expectedResult)));

        InOrder inOrder = Mockito.inOrder(matchSquadRepository, matchRepository, teamRepository, playerRepository);
        inOrder.verify(matchSquadRepository).findByTeamIdAndMatchId(teamId, matchId);
        inOrder.verify(playerRepository).findPlayersByIdIn(List.of(playerId));
        inOrder.verify(matchSquadRepository).save(any());
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    @DisplayName("getByTeamIdAndMatchId Case: teamId is null")
    void test20() {

        //given
        String exceptionMessage = "Team id is null";
        Integer teamId = null;
        Integer matchId = 1;

        //when
        AppException appException = assertThrows(AppException.class, () -> matchSquadService.getByTeamIdAndMatchId(teamId, matchId));

        //then
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));

        then(matchSquadRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("getByTeamIdAndMatchId Case: matchId is null")
    void test21() {

        //given
        String exceptionMessage = "Match id is null";
        Integer teamId = 1;
        Integer matchId = null;

        //when
        AppException appException = assertThrows(AppException.class, () -> matchSquadService.getByTeamIdAndMatchId(teamId, matchId));

        //then
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));

        then(matchSquadRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("getByTeamIdAndMatchId Case: teamId and matchId aren't null")
    void test22() {

        //given
        Integer matchId = 1;
        Integer teamId = 1;

        given(matchSquadRepository.findByTeamIdAndMatchId(teamId, matchId))
                .willReturn(Optional.of(MatchSquad.builder()
                        .id(1)
                        .build())
                );

        Optional<MatchSquadDto> expectedResult = Optional.of(
                MatchSquadDto.builder()
                        .id(1)
                        .substitutions(Collections.emptyMap())
                        .players(Collections.emptyMap())
                        .build()
        );

        //when
        Optional<MatchSquadDto> actual = assertDoesNotThrow(
                () -> matchSquadService.getByTeamIdAndMatchId(teamId, matchId)
        );

        //then
        assertThat(actual, is(equalTo(expectedResult)));

        then(matchSquadRepository).should(times(1)).findByTeamIdAndMatchId(teamId, matchId);
        then(matchSquadRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("getOpponentSquadForMatch. Case matchId is null")
    void test23() {

        //given
        String exceptionMessage = "Team id is null";
        Integer teamId = null;
        Integer matchId = 1;

        //when
        AppException appException = assertThrows(AppException.class, () -> matchSquadService.getOpponentSquadForMatch(matchId, teamId));

        //then
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));
        then(matchSquadRepository).shouldHaveNoInteractions();

    }

    @Test
    @DisplayName("getOpponentSquadForMatch. Case teamId is null")
    void test24() {

        //given
        String exceptionMessage = "Match id is null";
        Integer teamId = 1;
        Integer matchId = null;

        //when
        AppException appException = assertThrows(AppException.class, () -> matchSquadService.getOpponentSquadForMatch(matchId, teamId));

        //then
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));
        then(matchSquadRepository).shouldHaveNoInteractions();

    }

    @Test
    @DisplayName("getOpponentSquadForMatch. Case teamId and matchId aren't null")
    void test25() {

        //given
        Integer teamId = 1;
        Integer matchId = 1;
        Optional<MatchSquadDto> expectedResult = Optional.of(
                MatchSquadDto.builder()
                        .id(1)
                        .matchId(1)
                        .teamDto(TeamDto.builder()
                                .id(1)
                                .players(Collections.emptySet())
                                .build())
                        .players(Collections.emptyMap())
                        .substitutions(Collections.emptyMap())
                        .build()
        );

        given(matchSquadRepository.findByMatchIdAndTeamIdNot(matchId, teamId))
                .willReturn(Optional.of(MatchSquad.builder()
                        .id(1)
                        .match(Match.builder()
                                .id(1)
                                .build())
                        .team(Team.builder()
                                .id(1)
                                .build())
                        .build()));

        //when
        Optional<MatchSquadDto> actual = assertDoesNotThrow(() -> matchSquadService.getOpponentSquadForMatch(matchId, teamId));

        //then
        assertThat(actual, is(equalTo(expectedResult)));

        then(matchSquadRepository).should(times(1)).findByMatchIdAndTeamIdNot(matchId, teamId);
        then(matchSquadRepository).shouldHaveNoMoreInteractions();
    }

}

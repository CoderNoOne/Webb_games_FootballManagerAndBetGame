package org.example.fm;

import org.example.core.exceptions.AppException;
import org.example.entity.fm.entity.Match;
import org.example.model.fm.MatchDto;
import org.example.repository.fm.MatchRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private MatchService matchService;


    @Test
    @DisplayName("getFinishedMatchesForLeague by id - id is null. Exception should be thrown")
    void test1() {

        //given
        Integer leagueId = null;
        String exceptionMessage = "League id is null";

        //when
        AppException appException = Assertions.assertThrows(AppException.class, () -> matchService.getFinishedMatchesForLeague(leagueId));

        //then
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));
        then(matchRepository).shouldHaveNoInteractions();

    }

    @Test
    @DisplayName("getFinishedMatchesForLeague by id - id not null")
    void test2() {

        //given
        Integer leagueId = 1;
        List<MatchDto> expectedResult = List.of(
                MatchDto.builder()
                        .id(1)
                        .build(),
                MatchDto.builder()
                        .id(2)
                        .build()
        );

        given(matchRepository.findAllByScoreNotNullAndLeagueId(leagueId))
                .willReturn(List.of(
                        Match.builder()
                                .id(1)
                                .build(),
                        Match.builder()
                                .id(2)
                                .build()
                ));

        //when
        List<MatchDto> actual = Assertions.assertDoesNotThrow(() -> matchService.getFinishedMatchesForLeague(leagueId));

        //then
        assertThat(actual, is(equalTo(expectedResult)));
        then(matchRepository).should(times(1)).findAllByScoreNotNullAndLeagueId(leagueId);
        then(matchRepository).shouldHaveNoMoreInteractions();

    }

    @Test
    @DisplayName("get Scheduled matches: case leagueId is null. Exception should be thrown")
    void test3() {

        //given
        Integer leagueId = null;
        String exceptionMessage = "League id is null";

        //when
        AppException appException = Assertions.assertThrows(AppException.class, () -> matchService.getScheduledMatches(leagueId));

        //then
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));
        then(matchRepository).shouldHaveNoInteractions();

    }

    @Test
    @DisplayName("get Scheduled matches: case leagueId is not null")
    void test4() {

        //given
        Integer leagueId = 1;

        List<MatchDto> expectedResult = List.of(
                MatchDto.builder()
                        .id(1)
                        .build(),
                MatchDto.builder()
                        .id(2)
                        .build()
        );

        given(matchRepository.findAllByScoreNullAndLeagueId(leagueId))
                .willReturn(List.of(
                        Match.builder()
                                .id(1)
                                .build(),
                        Match.builder()
                                .id(2)
                                .build()
                ));

        //when
        List<MatchDto> actual = Assertions.assertDoesNotThrow(() -> matchService.getScheduledMatches(leagueId));

        //then
        assertThat(actual, is(equalTo(expectedResult)));
        then(matchRepository).should(times(1)).findAllByScoreNullAndLeagueId(leagueId);
        then(matchRepository).shouldHaveNoMoreInteractions();

    }

    @Test
    @DisplayName("get all Scheduled Matches By Team id: id is null. Exception should be thrown")
    void test5() {

        //given
        Integer teamId = null;
        Pageable pageable = PageRequest.of(0, 2, Sort.unsorted());
        String exceptionMessage = "Team id is null";

        //when
        AppException appException = Assertions.assertThrows(AppException.class, () -> matchService.getAllScheduledMatchesByTeamId(teamId, pageable));

        //then
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));
        then(matchRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("get all scheduled matches by team id: pageable is null")
    void test6() {

        //given
        String exceptionMessage = "Pageable is null";
        Integer teamId = 1;
        Pageable pageable = null;

        //when
        AppException appException = Assertions.assertThrows(AppException.class, () -> matchService.getAllScheduledMatchesByTeamId(teamId, pageable));

        //then
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));
        then(matchRepository).shouldHaveNoMoreInteractions();

    }

    @Test
    @DisplayName("get all scheduled matches by team id: team id and pageable is not null")
    void test7() {

        //given
        Integer teamId = 1;
        Pageable pageable = Pageable.unpaged();

        List<MatchDto> expectedResult = List.of(
                MatchDto.builder()
                        .id(1)
                        .build(),
                MatchDto.builder()
                        .id(2)
                        .build()
        );

        given(matchRepository.findAllByAwayTeamIdOrHomeTeamId(teamId, pageable))
                .willReturn(List.of(
                        Match.builder()
                                .id(1)
                                .build(),
                        Match.builder()
                                .id(2)
                                .build()
                ));

        //when
        List<MatchDto> actual = Assertions.assertDoesNotThrow(() -> matchService.getAllScheduledMatchesByTeamId(teamId, pageable));

        //then
        assertThat(actual, is(equalTo(expectedResult)));
        then(matchRepository).should(times(1)).findAllByAwayTeamIdOrHomeTeamId(teamId, pageable);
        then(matchRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("getById Case id is null. Exception should be thrown")
    void test8() {

        //given
        String exceptionMessage = "Match id is null";
        Integer matchId = null;

        //when
        AppException appException = Assertions.assertThrows(AppException.class, () -> matchService.getMatchById(matchId));

        //then
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));
        then(matchRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("get By id. Case id is not null.")
    void test9() {

        //given
        Integer matchId = 1;
        Optional<MatchDto> expectedResult = Optional.of(
                MatchDto.builder()
                        .id(matchId)
                        .build()
        );

        given(matchRepository.findById(matchId))
                .willReturn(Optional.of(
                        Match.builder()
                                .id(matchId)
                                .build()
                ));

        //when
        Optional<MatchDto> actual = Assertions.assertDoesNotThrow(() -> matchService.getMatchById(matchId));

        //then
        assertThat(actual, is(equalTo(expectedResult)));
        then(matchRepository).should(times(1)).findById(matchId);
        then(matchRepository).shouldHaveNoMoreInteractions();

    }

    @Test
    @DisplayName("getMatchesGroupedByMatchday. Case - matches is null. Exception should be thrown")
    void test10() {

        //given
        String exceptionMessage = "Matches list is null";
        List<MatchDto> matches = null;

        //when
        AppException appException = Assertions.assertThrows(AppException.class, () -> matchService.getMatchesGroupedByMatchDay(matches));

        //then
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));
        then(matchRepository).shouldHaveNoInteractions();

    }

    @Test
    @DisplayName("getMatchesGroupedByMatchday. Case - matches aren't null")
    void test11() {

        //given
        List<MatchDto> matches = List.of(
                MatchDto.builder()
                        .matchDay(1)
                        .id(1)
                        .build(),
                MatchDto.builder()
                        .matchDay(2)
                        .id(2)
                        .build()
        );

        Map<Integer, List<MatchDto>> expectedResult = Map.of(
                1, List.of(MatchDto.builder().id(1).matchDay(1).build()),
                2, List.of(MatchDto.builder().id(2).matchDay(2).build())
        );

        //when
        Map<Integer, List<MatchDto>> actual = Assertions.assertDoesNotThrow(() -> matchService.getMatchesGroupedByMatchDay(matches));

        //then
        assertThat(actual, is(equalTo(expectedResult)));
        then(matchRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("getStartingTimeForMatchById. Case matchId is null. Exception should be thrown")
    void test12() {

        //given
        Integer matchId = null;
        String exceptionMessage = "Match id is null";

        //when
        AppException appException = Assertions.assertThrows(AppException.class, () -> matchService.getStartingTimeForMatchById(matchId));

        //then
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));
        then(matchRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("getStartingTimeForMatchById. Case match doesn't exist")
    void test13() {

        //given
        Integer matchId = 1;
        String exceptionMessage = "Match with id 1 doesn't exist";
        given(matchRepository.findById(matchId))
                .willReturn(Optional.empty());

        //when
        AppException appException = Assertions.assertThrows(AppException.class, () -> matchService.getStartingTimeForMatchById(matchId));

        //then
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));
        then(matchRepository).should(times(1)).findById(matchId);
        then(matchRepository).shouldHaveNoMoreInteractions();

    }

    @Test
    @DisplayName("getStartingTimeForMatchById. Case match exists")
    void test14() {

        //given
        Integer matchId = 1;
        LocalDateTime expectedResult = LocalDateTime.of(2020, 10, 20, 20,0);
        given(matchRepository.findById(matchId))
                .willReturn(Optional.of(
                        Match.builder()
                                .id(1)
                                .dateTime(LocalDateTime.of(2020, 10, 20, 20, 0))
                                .build()
                ));

        //when
        LocalDateTime actual = Assertions.assertDoesNotThrow(() -> matchService.getStartingTimeForMatchById(matchId));

        //then
        assertThat(actual, is(equalTo(expectedResult)));
        then(matchRepository).should(times(1)).findById(matchId);
        then(matchRepository).shouldHaveNoMoreInteractions();

    }

}

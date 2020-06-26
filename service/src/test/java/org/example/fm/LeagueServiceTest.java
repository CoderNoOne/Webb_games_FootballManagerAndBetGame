package org.example.fm;

import org.example.core.exceptions.AppException;
import org.example.entity.fm.entity.League;
import org.example.model.fm.LeagueDto;
import org.example.repository.fm.LeagueRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
class LeagueServiceTest {

    @Mock
    private LeagueRepository leagueRepository;

    @InjectMocks
    private LeagueService leagueService;

    @Test
    @DisplayName("get Active League by username. Username is null - exception should be thrown")
    void test1() {

        //given
        String exceptionMessage = "Username is null";

        //when
        AppException appException = assertThrows(AppException.class, () -> leagueService.getActiveLeagueByUsername(null));

        //then
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));
        then(leagueRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("get Active league by username. Username is not null")
    void test2() {

        //given
        Optional<LeagueDto> expectedResult = Optional.of(
                LeagueDto.builder()
                        .id(1)
                        .name("league one")
                        .teams(new String[]{})
                        .matches(Collections.emptyList())
                        .build());
        String username = "nickname";
        given(leagueRepository.findByGameActiveAndGameUsersUsername(true, username))
                .willReturn(Optional.of(
                        League.builder()
                                .id(1)
                                .name("league one")
                                .build()
                ));

        //when
        Optional<LeagueDto> result = Assertions.assertDoesNotThrow(() -> leagueService.getActiveLeagueByUsername(username));

        //then
        assertThat(result, is(equalTo(expectedResult)));
        then(leagueRepository).should(times(1)).findByGameActiveAndGameUsersUsername(true, username);
        then(leagueRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("getAllLeaguesByActiveGameWithFetchedTeams")
    void test3() {

        //given
        Set<LeagueDto> expectedResult = Set.of(
                LeagueDto.builder()
                        .id(1)
                        .name("league one")
                        .teams(new String[]{})
                        .matches(Collections.emptyList())
                        .build(),
                LeagueDto.builder()
                        .id(2)
                        .name("league two")
                        .teams(new String[]{})
                        .matches(Collections.emptyList())
                        .build()
        );

        given(leagueRepository.findAllFetchTeams(true))
                .willReturn(Set.of(
                        League.builder()
                                .id(1)
                                .name("league one")
                                .build(),
                        League.builder()
                                .id(2)
                                .name("league two")
                                .build()
                ));

        //when
        Set<LeagueDto> actual = Assertions.assertDoesNotThrow(() -> leagueService.getAllLeaguesByActiveGameWithFetchedTeams());

        //then
        assertThat(actual, is(equalTo(expectedResult)));
        then(leagueRepository).should(times(1)).findAllFetchTeams(true);
        then(leagueRepository).shouldHaveNoMoreInteractions();

    }

}

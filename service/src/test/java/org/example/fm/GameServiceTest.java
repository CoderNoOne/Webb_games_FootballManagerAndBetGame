package org.example.fm;

import org.example.entity.core.entity.User;
import org.example.entity.fm.entity.Game;
import org.example.entity.fm.entity.League;
import org.example.model.core.GameDto;
import org.example.model.fm.LeagueDto;
import org.example.repository.fm.GameRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    @Test
    @DisplayName("findAllActiveGame")
    void test1() {

        //given
        List<GameDto> expectedResult = List.of(
                GameDto.builder()
                        .id(1)
                        .active(true)
                        .startDate(LocalDate.of(2020, 10, 20))
                        .userUsernames(Set.of("nickname"))
                        .leagues(Set.of(LeagueDto.builder()
                                .id(1)
                                .matches(Collections.emptyList())
                                .teams(new String[]{})
                                .build()))
                        .build()
        );

        given(gameRepository.findAllByActive(true))
                .willReturn(List.of(
                        Game.builder()
                                .id(1)
                                .active(true)
                                .startDate(LocalDate.of(2020, 10, 20))
                                .users(Set.of(User.builder().username("nickname").build()))
                                .leagues(Set.of(League.builder().id(1).build()))
                                .build()
                ));
        //when
        List<GameDto> allActive = gameService.findAllActive();

        //then
        assertThat(allActive, is(equalTo(expectedResult)));
        then(gameRepository).should(times(1)).findAllByActive(true);
        then(gameRepository).shouldHaveNoMoreInteractions();

    }

}

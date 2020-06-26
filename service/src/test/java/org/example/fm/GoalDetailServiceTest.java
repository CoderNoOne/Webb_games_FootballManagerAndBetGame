package org.example.fm;

import org.example.core.exceptions.AppException;
import org.example.entity.fm.entity.GoalDetail;
import org.example.entity.fm.entity.Match;
import org.example.entity.fm.entity.Player;
import org.example.entity.fm.entity.Team;
import org.example.model.fm.GoalDetailDto;
import org.example.model.fm.PlayerDto;
import org.example.repository.fm.GoalDetailRepository;
import org.example.repository.fm.MatchRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
class GoalDetailServiceTest {

    @Mock
    private GoalDetailRepository goalDetailRepository;

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private GoalDetailService goalDetailService;

    @Test
    @DisplayName("get Goals Details For Match Id when id is null. Exception should be thrown")
    void test1() {

        //given
        String exceptionMessage = "Match id is null";

        //when
        AppException appException = assertThrows(AppException.class, () -> goalDetailService.getGoalDetailsForMatchId(null));

        //then
        assertThat(appException.getMessage(), equalTo(exceptionMessage));
        then(goalDetailRepository).shouldHaveNoInteractions();
        then(matchRepository).shouldHaveNoInteractions();

    }

    @Test
    @DisplayName("get Goals Details For Match Id when id is not null, but match with id doesn't exist")
    void test2() {

        //given
        Integer matchId = 1;
        String exceptionMessage = "Match with id 1 doesn't exist";
        given(goalDetailRepository.findById(matchId))
                .willReturn(Optional.empty());

        //when
        //then
        AppException appException = assertThrows(AppException.class, () -> goalDetailService.getGoalDetailsForMatchId(matchId));
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));
        then(matchRepository).should(times(1)).findById(matchId);
        then(goalDetailRepository).shouldHaveNoMoreInteractions();
        then(goalDetailRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("get Goal Details For match id when match exists in DB")
    void test3() {

        //given
        Integer matchId = 1;
        given(matchRepository.findById(matchId))
                .willReturn(Optional.of(
                        Match.builder()
                                .id(1)
                                .homeTeam(Team.builder()
                                        .id(1)
                                        .build())
                                .awayTeam(Team.builder()
                                        .id(2)
                                        .build())
                                .build()));

        given(goalDetailRepository.findAllByMatchId(matchId))
                .willReturn(List.of(
                        GoalDetail.builder()
                                .id(1)
                                .minute(20)
                                .assistant(Player.builder()
                                        .id(2)
                                        .team(Team.builder()
                                                .id(1)
                                                .build())
                                        .build())
                                .scorer(Player.builder()
                                        .id(1)
                                        .team(Team.builder()
                                                .id(1)
                                                .build())
                                        .build())
                                .build()
                ));

        List<GoalDetailDto> expected = List.of(
                GoalDetailDto.builder()
                        .id(1)
                        .minute(20)
                        .scorer(PlayerDto.builder()
                                .id(1)
                                .teamId(1)
                                .positions(Collections.emptySet())
                                .build())
                        .assistant(PlayerDto.builder()
                                .id(2)
                                .teamId(1)
                                .positions(Collections.emptySet())
                                .build())
                        .team("Home")
                        .build()
        );

        //when
        //then
        List<GoalDetailDto> result = assertDoesNotThrow(() -> goalDetailService.getGoalDetailsForMatchId(matchId));
        assertThat(result, is(equalTo(expected)));

        InOrder inOrder = Mockito.inOrder(goalDetailRepository, matchRepository);
        inOrder.verify(matchRepository).findById(matchId);
        inOrder.verify(goalDetailRepository).findAllByMatchId(matchId);
        inOrder.verifyNoMoreInteractions();
    }

}

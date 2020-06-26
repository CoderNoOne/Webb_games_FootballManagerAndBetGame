package org.example.repository.fm;

import org.example.annotations.NoJpaRepository;
import org.example.entity.bet.entity.BetPoints;
import org.example.entity.core.entity.User;
import org.example.entity.fm.entity.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
class GoalDetailRepositoryTest {

    @Autowired
    private GoalDetailRepository goalDetailRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Configuration
    @EnableJpaRepositories(basePackageClasses = {GoalDetailRepository.class})
    @EntityScan(basePackageClasses = {User.class, Game.class, BetPoints.class})
    @ComponentScan(excludeFilters = @ComponentScan.Filter(value = NoJpaRepository.class, type = FilterType.ANNOTATION))
    static class GoalDetailRepositoryTestConfiguration {
    }


    @Test
    @DisplayName("findAllByMatchStatisticId")
    void test1() {

        //given
        MatchStatistic persistedMatchStats = testEntityManager.merge(MatchStatistic.builder().build());

        GoalDetail persisted1 = GoalDetail.builder()
                .minute(60)
                .matchStatistic(persistedMatchStats)
                .build();

        GoalDetail persisted2 = GoalDetail.builder()
                .minute(78)
                .matchStatistic(persistedMatchStats)
                .build();

        var expected = List.of(
                testEntityManager.persist(persisted1),
                testEntityManager.persist(persisted2)
        );

        //when
        List<GoalDetail> actual = goalDetailRepository.findAllByMatchStatisticId(1);

        //then
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    @DisplayName("findAllByMatchStatisticMatchLeagueId")
    void test2() {

        //given
        Integer leagueId = 1;
        League persistedLeague = testEntityManager.merge(League.builder().id(leagueId).build());

        MatchStatistic persistedMatchStats = testEntityManager.merge(MatchStatistic
                .builder()
                .match(testEntityManager.merge(Match.builder()
                        .league(persistedLeague)
                        .build()))
                .build());

        GoalDetail persisted1 = GoalDetail.builder()
                .minute(60)
                .matchStatistic(persistedMatchStats)
                .build();

        var expected = List.of(testEntityManager.persist(persisted1));

        //when
        List<GoalDetail> actual = goalDetailRepository.findAllByMatchStatisticMatchLeagueId(leagueId);

        //then
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    @DisplayName("findAllByTeamId")
    @Sql(statements = "insert into teams(id) values (1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into players(id, team_id) values (1,1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into goal_details (id, goal_assistant_id) values (1,1)")
    void test3() {

        //given
        var teamId = 1;

        var expected = List.of(GoalDetail.builder()
                .id(1)
                .assistant(Player.builder().id(1).positions(Collections.emptySet()).build())
                .build());
        //when
        var actual = goalDetailRepository.findAllByTeamId(teamId);

        //then
        assertThat(actual, is(equalTo(expected)));
    }

}

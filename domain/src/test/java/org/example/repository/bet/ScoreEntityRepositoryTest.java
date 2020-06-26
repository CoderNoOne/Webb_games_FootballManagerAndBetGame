package org.example.repository.bet;

import org.example.annotations.NoJpaRepository;
import org.example.entity.bet.entity.ScoreEntity;
import org.example.entity.bet.enums.BetLeague;
import org.example.entity.core.entity.User;
import org.example.entity.fm.entity.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@Sql(scripts = {"/scripts/userScript.sql", "/scripts/scoreEntityScript.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ScoreEntityRepositoryTest {

    @Autowired
    private ScoreEntityRepository scoreEntityRepository;

    @Configuration
    @EnableJpaRepositories(basePackageClasses = {ScoreEntityRepository.class})
    @EntityScan(basePackageClasses = {ScoreEntity.class, User.class, Game.class})
    @ComponentScan(excludeFilters = @ComponentScan.Filter(value = NoJpaRepository.class, type = FilterType.ANNOTATION))
    static class ScoreEntityTestConfiguration { }

    @Test
    @DisplayName("findAllByUsername")
    void test1() {

        //given
        var expectedResult = List.of(
                ScoreEntity.builder()
                        .id(1)
                        .league(BetLeague.ITALY)
                        .user(User.builder()
                                .username("Alaba")
                                .email("alaba@gmail.com")
                                .photoUrl("www.someUrl.com")
                                .build())
                        .betScores(new ArrayList<>())
                        .build()
        );

        //when
        List<ScoreEntity> actual = scoreEntityRepository.findAllByUserName("Alaba");

        //then
        assertThat(actual, is(equalTo(expectedResult)));
    }

    @Test
    @DisplayName("findAllByUsernameAndLeague")
    void test2() {

        //given
        String username = "Alaba";
        BetLeague league = BetLeague.ITALY;

        var expectedResult = List.of(
                ScoreEntity.builder()
                        .id(1)
                        .league(BetLeague.ITALY)
                        .user(User.builder()
                                .username("Alaba")
                                .email("alaba@gmail.com")
                                .photoUrl("www.someUrl.com")
                                .build())
                        .betScores(new ArrayList<>())
                        .build()
        );

        //when
        List<ScoreEntity> actual = scoreEntityRepository.findAllByUserNameAndLeague(username, league);

        //then
        assertThat(actual, is(equalTo(expectedResult)));
    }

    @Test
    @DisplayName("findAllByLeague")
    void test3() {

        //given
        BetLeague league = BetLeague.ITALY;

        var expectedResult = List.of(
                ScoreEntity.builder()
                        .id(1)
                        .league(BetLeague.ITALY)
                        .user(User.builder()
                                .username("Alaba")
                                .email("alaba@gmail.com")
                                .photoUrl("www.someUrl.com")
                                .build())
                        .betScores(new ArrayList<>())
                        .build()
        );

        //when
        List<ScoreEntity> actual = scoreEntityRepository.findAllByLeague(league);

        //then
        assertThat(actual, is(equalTo(expectedResult)));

    }
}


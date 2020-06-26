package org.example.repository.bet;


import org.example.annotations.NoJpaRepository;
import org.example.entity.bet.entity.BetPoints;
import org.example.entity.bet.enums.BetLeague;
import org.example.entity.core.entity.User;
import org.example.entity.fm.entity.Game;
import org.example.repository.core.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
class BetPointsRepositoryTest {

    @Autowired
    private BetPointsRepository betPointsRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Configuration
    @EnableJpaRepositories(basePackageClasses = {BetPointsRepository.class, UserRepository.class})
    @EntityScan(basePackageClasses = {BetPoints.class, User.class, Game.class})
    @ComponentScan(excludeFilters = @ComponentScan.Filter(value = NoJpaRepository.class, type = FilterType.ANNOTATION))
    static class BetPointsTestConfiguration { }

    @Test
    @DisplayName("findAllByLeague")
    @Sql(scripts = "/scripts/userScript.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/betPoints-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void test1() {

        //given
        User user = entityManager.getEntityManager().find(User.class, "Alaba");

        List<BetPoints> expectedResult = List.of(

                BetPoints.builder()
                        .id(1)
                        .league(BetLeague.ITALY)
                        .user(user)
                        .pointsNumber(10)
                        .build());

        //when
        List<BetPoints> actual = betPointsRepository.findAll();

        //then
        assertThat(actual, is(equalTo(expectedResult)));

    }
}

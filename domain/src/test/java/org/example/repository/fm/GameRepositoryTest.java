package org.example.repository.fm;

import org.assertj.core.api.Assertions;
import org.example.annotations.NoJpaRepository;
import org.example.entity.bet.entity.BetPoints;
import org.example.entity.core.entity.User;
import org.example.entity.fm.entity.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.time.LocalDate;
import java.util.List;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Configuration
    @EnableJpaRepositories(basePackageClasses = {GameRepository.class})
    @EntityScan(basePackageClasses = {User.class, Game.class, BetPoints.class})
    @ComponentScan(excludeFilters = @ComponentScan.Filter(value = NoJpaRepository.class, type = FilterType.ANNOTATION))
    static class GameRepositoryTestConfiguration {
    }

    @Test
    @DisplayName("finaAllByActive")
    @Sql(
            statements = {"insert into games (id, active, start_date) values (1, true, '2020-10-20')",
                    "insert into games (id, active, start_date) values (2, false, '2020-07-15')"
            },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void test1() {

        //given
        Boolean isActive = true;
        List<Game> expected = List.of(Game.builder()
                .id(1)
                .active(isActive)
                .startDate(LocalDate.of(2020, 10, 20))
                .build());

        //when
        List<Game> actual = gameRepository.findAllByActive(isActive);

        //then
        assertThat(actual, is(equalTo(expected)));
    }
}

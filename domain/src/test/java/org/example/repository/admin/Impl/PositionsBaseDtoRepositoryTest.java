package org.example.repository.admin.Impl;

import org.example.NoJpaTestConfiguration;
import org.example.model.admin.PlayerBaseDto;
import org.example.model.admin.PositionBaseDto;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = NoJpaTestConfiguration.class)
class PositionsBaseDtoRepositoryTest {

    private final PositionsBaseDtoRepository positionsBaseDtoRepository;

    @Autowired
    public PositionsBaseDtoRepositoryTest(PositionsBaseDtoRepository positionsBaseDtoRepository) {
        this.positionsBaseDtoRepository = positionsBaseDtoRepository;
    }


    @Test
    @DisplayName("findAll")
    @Sql(statements = "insert into team_base (name, country_name) values ('Real Madrid', 'Spain');", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO players_base (id, first_name, last_name, country_name, team_name) VALUES ('1', 'Jan', 'Kowalski', 'England', 'Real Madrid');", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into positions_base values (1, 'LCF'), (1, 'RCF')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "delete from positions_base", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "delete from players_base", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "delete from team_base", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test1() {

        //given
        var expected = List.of(
                PositionBaseDto.builder()
                        .playerId(1)
                        .position("LCF")
                        .build(),
                PositionBaseDto.builder()
                        .playerId(1)
                        .position("RCF")
                        .build()
        );

        //when

        List<PositionBaseDto> actual = positionsBaseDtoRepository.findAll();

//        then
        assertThat(actual, is(equalTo(expected)));

    }

    @Test
    @DisplayName("findAllByPlayerId: case player id is null")
    void test2() {

        //given
        Integer playerId = null;
        var expected = Collections.emptyList();

        //when

        List<PositionBaseDto> actual = positionsBaseDtoRepository.findAllByPLayerId(playerId);

//        then
        assertThat(actual, is(equalTo(expected)));

    }

    @Test
    @DisplayName("findAllByPlayerId: case player id is not null; player doesn't exist")
    void test3() {

        //given
        Integer playerId = 100;
        var expected = Collections.emptyList();

        //when

        List<PositionBaseDto> actual = positionsBaseDtoRepository.findAllByPLayerId(playerId);

//        then
        assertThat(actual, is(equalTo(expected)));

    }

    @Test
    @DisplayName("findAllByPlayerId: case player id is not null; player exists")
    @Sql(statements = "insert into team_base (name, country_name) values ('Real Madrid', 'Spain');", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO players_base (id, first_name, last_name, country_name, team_name) VALUES ('1', 'Jan', 'Kowalski', 'England', 'Real Madrid');", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into positions_base values (1, 'LCF'), (1, 'RCF')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "delete from positions_base", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "delete from players_base", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "delete from team_base", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test4() {

        //given
        Integer playerId = 1;

        var expected = List.of(
                PositionBaseDto.builder()
                        .playerId(1)
                        .position("LCF")
                        .build(),
                PositionBaseDto.builder()
                        .playerId(1)
                        .position("RCF")
                        .build()
        );

        //when

        List<PositionBaseDto> actual = positionsBaseDtoRepository.findAllByPLayerId(playerId);

//        then
        assertThat(actual, is(equalTo(expected)));

    }
}

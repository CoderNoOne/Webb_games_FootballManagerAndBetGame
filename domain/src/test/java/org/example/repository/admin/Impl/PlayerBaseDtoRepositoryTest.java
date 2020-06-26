package org.example.repository.admin.Impl;

import org.example.NoJpaTestConfiguration;
import org.example.model.admin.PlayerAttributesBaseDto;
import org.example.model.admin.PlayerBaseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = NoJpaTestConfiguration.class)
class PlayerBaseDtoRepositoryTest {

    private final PlayerBaseDtoRepository playerBaseDtoRepository;

    @Autowired
    public PlayerBaseDtoRepositoryTest(PlayerBaseDtoRepository playerBaseDtoRepository) {
        this.playerBaseDtoRepository = playerBaseDtoRepository;
    }

    @Test
    @DisplayName("findAll")
    @Sql(statements = "delete from positions_base", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "delete from players_attributes_base", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "delete from players_base", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "delete from team_base", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into team_base (name, country_name) values ('FC NO NAME', 'Spain');", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO players_base (id, first_name, last_name, country_name, team_name) VALUES ('1', 'Jan', 'Kowalski', 'England', 'FC NO NAME');", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void test1() {

        //given
        var expected = List.of(
                PlayerBaseDto.builder()
                        .id(1)
                        .firstName("Jan")
                        .lastName("Kowalski")
                        .teamName("FC NO NAME")
                        .countryName("England")
                        .build()
        );

        //when
        List<PlayerBaseDto> actual = playerBaseDtoRepository.findAll();
//        then
        assertThat(actual, is(equalTo(expected)));

    }

    @Test
    @DisplayName("findAllByTeamName case teamName is null")
    void test2() {

        //given
        String teamName = null;
        var expected = Collections.emptyList();

        //when
        List<PlayerBaseDto> actual = playerBaseDtoRepository.findAllByTeamName(teamName);
//        then
        assertThat(actual, is(equalTo(expected)));

    }

    @Test
    @DisplayName("findAllByTeamName: case teamName is not null; doesn't exist")
    void test3() {

        //given
        String teamName = "Szczakowianka";
        var expected = Collections.emptyList();

        //when
        List<PlayerBaseDto> actual = playerBaseDtoRepository.findAllByTeamName(teamName);
//        then
        assertThat(actual, is(equalTo(expected)));

    }

    @Test
    @DisplayName("findAllByTeamName: case teamName is not null; exists")
    @Sql(statements = "delete from positions_base", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "delete from players_attributes_base", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "delete from players_base", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "delete from team_base", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into team_base (name, country_name) values ('FC NO NAME', 'Spain');", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO players_base (id, first_name, last_name, country_name, team_name) VALUES ('1', 'Jan', 'Kowalski', 'England', 'FC NO NAME');", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void test4() {

        //given
        String teamName = "FC NO NAME";

        var expected = List.of(
                PlayerBaseDto.builder()
                        .id(1)
                        .firstName("Jan")
                        .lastName("Kowalski")
                        .teamName("FC NO NAME")
                        .countryName("England")
                        .build()
        );

        //when
        List<PlayerBaseDto> actual = playerBaseDtoRepository.findAllByTeamName(teamName);
//        then
        assertThat(actual, is(equalTo(expected)));

    }

}

package org.example.repository.admin.Impl;

import org.example.NoJpaTestConfiguration;
import org.example.model.admin.PlayerBaseDto;
import org.example.model.admin.TeamBaseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = NoJpaTestConfiguration.class)
class TeamBaseRepositoryTest {

    private final TeamBaseRepository teamBaseRepository;

    @Autowired
    public TeamBaseRepositoryTest(TeamBaseRepository teamBaseRepository) {
        this.teamBaseRepository = teamBaseRepository;
    }

    @Test
    @DisplayName("findAll")
    @Sql(statements = "insert into team_base (name, country_name) values ('Real Madrid', 'Spain');", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "delete from team_base", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test1() {

        //given
        var expected = List.of(
                TeamBaseDto.builder()
                        .name("Real Madrid")
                        .countryName("Spain")
                        .build()
        );

        //when
        List<TeamBaseDto> actual = teamBaseRepository.findAll();

//        then
        assertThat(actual, is(equalTo(expected)));

    }

    @Test
    @DisplayName("findByName")
    @Sql(
            statements = "insert into team_base (name, country_name, logo_url) values ('Real Madrid', 'Spain', 'logoUrl');",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "delete from team_base", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test2() {

        //given
        String name = "Real Madrid";

        Optional<TeamBaseDto> expected = Optional.of(
                TeamBaseDto.builder()
                        .name("Real Madrid")
                        .countryName("Spain")
                        .logoUrl("logoUrl")
                        .build()
        );

        //when
        Optional<TeamBaseDto> actual = teamBaseRepository.findByName(name);

//        then
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    @DisplayName("findAll")
    @Sql(
            statements = {
                    "insert into league_base (name) values ('BBVA');",
                    "insert into team_base (name, country_name, league_name) values ('Real Madrid', 'Spain', 'BBVA');",
                    "insert into team_base (name, country_name, league_name) values ('FC Barcelona', 'Spain', 'BBVA');"
            }
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "delete from team_base", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "delete from league_base", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test3() {

        //given
        var expected = List.of(
                TeamBaseDto.builder()
                        .name("Real Madrid")
                        .countryName("Spain")
                        .leagueName("BBVA")
                        .build(),
                TeamBaseDto.builder()
                        .name("FC Barcelona")
                        .countryName("Spain")
                        .leagueName("BBVA")
                        .build()
        );

        //when
        List<TeamBaseDto> actual = teamBaseRepository.findAllByLeagueName("BBVA");

//        then
        assertThat(actual, is(equalTo(expected)));

    }

}

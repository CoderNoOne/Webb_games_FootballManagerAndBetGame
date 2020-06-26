package org.example.repository.admin.Impl;

import org.example.NoJpaTestConfiguration;
import org.example.model.admin.LeagueBaseDto;
import org.example.model.admin.PlayerAttributesBaseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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
class PlayerAttributesBaseDtoRepositoryTest {

    private final PlayerAttributesBaseDtoRepository playerAttributesBaseDtoRepository;

    @Autowired
    public PlayerAttributesBaseDtoRepositoryTest(PlayerAttributesBaseDtoRepository playerAttributesBaseDtoRepository) {
        this.playerAttributesBaseDtoRepository = playerAttributesBaseDtoRepository;
    }


    @Test
    @DisplayName("findAll")
    @Sql(statements = "insert into team_base (name, country_name) values ('Real Madrid', 'Spain');",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO players_base (id, first_name, last_name, country_name, team_name) VALUES ('1', 'Jan', 'Kowalski', 'England', 'Real Madrid');", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO players_attributes_base (attacking, defending, heading, dribbling, player_id) VALUES ('70', '82', '80', '85','1')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void test1() {

        //given
        var expected = List.of(
                PlayerAttributesBaseDto.builder()
                        .attacking(70)
                        .defending(82)
                        .heading(80)
                        .dribbling(85)
                        .playerId(1)
                        .build()
        );

        //when
        List<PlayerAttributesBaseDto> actual = playerAttributesBaseDtoRepository.findAll();

//        then
        assertThat(actual, is(equalTo(expected)));

    }
}

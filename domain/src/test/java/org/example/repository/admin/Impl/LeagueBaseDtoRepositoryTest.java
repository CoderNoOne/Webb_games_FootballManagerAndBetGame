package org.example.repository.admin.Impl;

import lombok.RequiredArgsConstructor;
import org.example.NoJpaTestConfiguration;
import org.example.model.admin.CountryBaseDto;
import org.example.model.admin.LeagueBaseDto;
import org.example.model.fm.LeagueDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = NoJpaTestConfiguration.class)
class LeagueBaseDtoRepositoryTest {

    private final LeagueBaseDtoRepository leagueBaseDtoRepository;

    @Autowired
    public LeagueBaseDtoRepositoryTest(LeagueBaseDtoRepository leagueBaseDtoRepository) {
        this.leagueBaseDtoRepository = leagueBaseDtoRepository;
    }

    @Test
    @DisplayName("findAll")
    void test1() {

        //given
        var expected = List.of(LeagueBaseDto.builder()
                .countryName("England")
                .name("Premier League")
                .leagueType("CUSTOM")
                .build());

        //when
        List<LeagueBaseDto> actual = leagueBaseDtoRepository.findAll();

//        then
        assertThat(actual, is(equalTo(expected)));

    }

    @Test
    @DisplayName("findByName case name is null")
    void test2() {

        //given
        String name = null;
        var expected = Optional.empty();

        //when
        Optional<LeagueBaseDto> actual = leagueBaseDtoRepository.findByName(name);

//        then
        assertThat(actual, is(equalTo(expected)));

    }

    @Test
    @DisplayName("findByName case name is notNull; exist")
    void test3() {

        //given
        String name = "Premier League";
        var expected = Optional.of(
                LeagueBaseDto.builder()
                        .name(name)
                        .countryName("England")
                        .leagueType("CUSTOM")
                        .build()
        );

        //when
        Optional<LeagueBaseDto> actual = leagueBaseDtoRepository.findByName(name);

//        then
        assertThat(actual, is(equalTo(expected)));

    }

    @Test
    @DisplayName("findByName case: name is notNull; doesn't exist ")
    void test4() {

        //given
        String name = "not exist ";
        var expected = Optional.empty();

        //when
        Optional<LeagueBaseDto> actual = leagueBaseDtoRepository.findByName(name);

//        then
        assertThat(actual, is(equalTo(expected)));

    }

    @Test
    @DisplayName("findByType case: type is null")
    void test5() {

        //given
        String leagueType = null;
        var expected = Optional.empty();

        //when
        Optional<LeagueBaseDto> actual = leagueBaseDtoRepository.findByType(leagueType);

//        then
        assertThat(actual, is(equalTo(expected)));

    }

    @Test
    @DisplayName("findByType case: type is not null; exists")
    void test6() {

        //given
        String leagueType = "CUSTOM";
        var expected = Optional.of(
                LeagueBaseDto.builder()
                        .countryName("England")
                        .leagueType(leagueType)
                        .name("Premier League")
                        .build()
        );

        //when
        Optional<LeagueBaseDto> actual = leagueBaseDtoRepository.findByType(leagueType);

//        then
        assertThat(actual, is(equalTo(expected)));

    }

    @Test
    @DisplayName("findByType case: type is not null; doesn't exist")
    void test7() {

        //given
        String leagueType = "serie D";
        var expected = Optional.empty();

        //when
        Optional<LeagueBaseDto> actual = leagueBaseDtoRepository.findByType(leagueType);

//        then
        assertThat(actual, is(equalTo(expected)));

    }
}

package org.example.repository.admin.Impl;

import org.example.NoJpaTestConfiguration;
import org.example.model.admin.CountryBaseDto;
import org.example.repository.bet.BetPointsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = NoJpaTestConfiguration.class)
class CountryBaseDtoRepositoryTest {

    private final CountryBaseDtoRepository countryBaseDtoRepository;

    @Autowired
    public CountryBaseDtoRepositoryTest(CountryBaseDtoRepository countryBaseDtoRepository) {
        this.countryBaseDtoRepository = countryBaseDtoRepository;
    }

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("findAll")
    void test1() {

        //given
        var spain =
                CountryBaseDto.builder()
                        .name("Spain")
                        .build();

        System.out.println(entityManager.getProperties());
        //when
        List<CountryBaseDto> actual = countryBaseDtoRepository.findAll();

//        then
        assertThat(actual, hasSize(4));
        assertThat(actual, hasItem(spain));

    }

    @Test
    @DisplayName("findByName")
    @Sql(statements = "insert into country_base (name) values ('ASDASD')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void test2() {

        //given
        var expected = Optional.of(
                CountryBaseDto.builder()
                        .name("ASDASD")
                        .build()
        );

        //when
        var actual = countryBaseDtoRepository.findByName("ASDASD");

//        then
        assertThat(actual, is(equalTo(expected)));

    }

}

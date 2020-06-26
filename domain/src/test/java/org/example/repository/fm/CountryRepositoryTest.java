package org.example.repository.fm;

import org.example.annotations.NoJpaRepository;
import org.example.entity.bet.entity.BetPoints;
import org.example.entity.core.entity.User;
import org.example.entity.fm.entity.Country;
import org.example.entity.fm.entity.Game;
import org.example.repository.admin.Impl.CountryBaseDtoRepository;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(loader= AnnotationConfigContextLoader.class)
class CountryRepositoryTest {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Configuration
    @EnableJpaRepositories(basePackageClasses = {CountryRepository.class})
    @EntityScan(basePackageClasses = {User.class, Game.class, BetPoints.class})
    @ComponentScan(excludeFilters = @ComponentScan.Filter(value = NoJpaRepository.class, type = FilterType.ANNOTATION))
    static class CountryRepositoryTestConfiguration { }

    @Test
    @DisplayName("findByName case doesn't exist")
    void test1(){
        //given
        String name = "Spain";
        var expected = Optional.empty();

        //when
        var actual = countryRepository.findByName(name);

        //then
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    @DisplayName("findByName case exists")
    void test2(){

        //given
        String name = "Spain";

        testEntityManager.persist(Country.builder()
                .name(name)
                .build());

        var expected = Optional.of(Country.builder()
                .name(name)
                .build());

        //when
        var actual = countryRepository.findByName(name);

        //then
        assertThat(actual, is(equalTo(expected)));
    }
}

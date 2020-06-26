package org.example.repository.core;

import org.example.annotations.NoJpaRepository;
import org.example.entity.bet.entity.BetPoints;
import org.example.entity.bet.entity.ScoreEntity;
import org.example.entity.core.entity.User;
import org.example.entity.fm.entity.Game;
import org.example.repository.bet.ScoreEntityRepository;
import org.example.repository.core.projection.UserMail;
import org.example.repository.core.projection.UserPhotoUrl;
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

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(loader= AnnotationConfigContextLoader.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Configuration
    @EnableJpaRepositories(basePackageClasses = {UserRepository.class})
    @EntityScan(basePackageClasses = {User.class, Game.class, BetPoints.class})
    @ComponentScan(excludeFilters = @ComponentScan.Filter(value = NoJpaRepository.class, type = FilterType.ANNOTATION))
    static class UserRepositoryTestConfiguration { }


    @Test
    @DisplayName("findByEmail")
    @Sql(scripts = "/scripts/userScript.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void test1() {

        //given
        String mail = "alaba@gmail.com";
        Optional<User> expectedResult = Optional.of(
                User.builder()
                        .username("Alaba")
                        .email("alaba@gmail.com")
                        .photoUrl("www.someUrl.com")
                        .build()
        );

        //when
        Optional<User> actual = userRepository.findByEmail(mail);

        //then
        assertThat(actual, is(equalTo(expectedResult)));
    }

    @Test
    @DisplayName("findByUsername")
    @Sql(scripts = "/scripts/userScript.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void test2() {

        //given
        String username = "Alaba";
        Optional<User> expectedResult = Optional.of(
                User.builder()
                        .username("Alaba")
                        .email("alaba@gmail.com")
                        .photoUrl("www.someUrl.com")
                        .build()
        );

        //when
        Optional<User> actual = userRepository.findByUsername(username);

        //then
        assertThat(actual, is(equalTo(expectedResult)));
    }

    @Test
    @DisplayName("getPhotoUrlByUsername")
    @Sql(scripts = "/scripts/userScript.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void test3() {

        //given
        String username = "Alaba";
        String expectedResult = "www.someUrl.com";


        //when
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        String actual = userRepository.findOptionalByUsername(username).get().getPhotoUrl();


//        then
        assertThat(actual, is(equalTo(expectedResult)));
    }

    @Test
    @DisplayName("findEmailByUsername")
    @Sql(scripts = "/scripts/userScript.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void test4() {

        //given
        String username = "Alaba";
        String expectedResult = "alaba@gmail.com";


        //when
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        String actual = userRepository.findEmailByUsername(username).get().getEmail();


//        then
        assertThat(actual, is(equalTo(expectedResult)));
    }

    @Test
    @DisplayName("getUserEmails")
    @Sql(scripts = "/scripts/userScript.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void test5() {

        //given
        String username = "Alaba";
        var expectedResult = List.of("alaba@gmail.com");


        //when
        List<String> actual = userRepository.getUserEmails().stream().map(UserMail::getEmail).collect(Collectors.toList());

//        then
        assertThat(actual, is(equalTo(expectedResult)));
    }

    @Test
    @DisplayName("findPhotoUrlByUsernameIn")
    @Sql(scripts = "/scripts/userScript.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void test6() {

        //given
        String username = "Alaba";
        var expectedResult = List.of("www.someUrl.com");


        //when
        List<String> actual = userRepository
                .findPhotoUrlByUsernameIn(Set.of(username))
                .stream()
                .map(UserPhotoUrl::getPhotoUrl)
                .collect(Collectors.toList());

//        then
        assertThat(actual, is(equalTo(expectedResult)));
    }

}

package org.example.repository.core;

import org.example.annotations.NoJpaRepository;
import org.example.entity.core.entity.User;
import org.example.entity.core.entity.VerificationToken;
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

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
class VerificationTokenRepositoryTest {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Configuration
    @EnableJpaRepositories(basePackageClasses = {VerificationTokenRepository.class})
    @EntityScan(basePackageClasses = {VerificationToken.class})
    @ComponentScan(excludeFilters = @ComponentScan.Filter(value = NoJpaRepository.class, type = FilterType.ANNOTATION))
    static class VerificationTokenRepositoryTestConfiguration {
    }


    @Test
    @DisplayName("getTokenForUser: token exists")
    @Sql(scripts = "/scripts/userScript.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into verification_tokens (token, user_username) values ('tokenVal', 'Alaba')")
    void test1() {

        //given
        String username = "Alaba";
        Optional<String> expected = Optional.of(
                "tokenVal"
        );

        //when
        Optional<String> actualValue = verificationTokenRepository.getTokenForUser(username);

        //then
        assertThat(actualValue, is(equalTo(expected)));
    }

    @Test
    @DisplayName("getTokenForUser: token doesn't exist")
    void test2() {

        //given
        String username = "Alaba";
        Optional<String> expected = Optional.empty();

        //when
        Optional<String> actualValue = verificationTokenRepository.getTokenForUser(username);

        //then
        assertThat(actualValue, is(equalTo(expected)));
    }

    @Test
    @DisplayName("getUserWithToken: null token")
    void test3() {

        //given
        String token = null;
        Optional<String> expected = Optional.empty();

        //when
        Optional<User> actualValue = verificationTokenRepository.getUserWithToken(token);

        //then
        assertThat(actualValue, is(equalTo(expected)));
    }

    @Test
    @DisplayName("getUserWithToken: nonNull token")
    @Sql(scripts = "/scripts/userScript.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into verification_tokens (token, user_username) values ('tokenVal', 'Alaba')")
    void test4() {

        //given
        String token = "tokenVal";
        Optional<User> expected = Optional.of(User.builder()
                .username("Alaba")
                .email("alaba@gmail.com")
                .photoUrl("www.someUrl.com")
                .build());

        //when
        Optional<User> actualValue = verificationTokenRepository.getUserWithToken(token);

        //then
        assertThat(actualValue, is(equalTo(expected)));
    }

    @Test
    @DisplayName("findByUserUsername case no token for user")
    void test5() {

        //given
        String username = "Alaba";
        Optional<VerificationToken> expected = Optional.empty();

        //when
        Optional<VerificationToken> actual = verificationTokenRepository.findByUserUsername(username);

        //then
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    @DisplayName("findByUserUsername case token for user exists")
    @Sql(scripts = "/scripts/userScript.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into verification_tokens (id,token, user_username) values (1, 'tokenVal', 'Alaba')")
    void test6() {

        //given
        String username = "Alaba";
        Optional<VerificationToken> expected = Optional.of(
                VerificationToken.builder()
                        .id(1)
                        .token("tokenVal")
                        .user(User.builder()
                                .email("alaba@gmail.com")
                                .photoUrl("www.someUrl.com")
                                .username("Alaba")
                                .build())
                        .build()
        );

        //when
        Optional<VerificationToken> actual = verificationTokenRepository.findByUserUsername(username);

        //then
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    @DisplayName("findByToken case tokenVal not assigned to any VerificationToken object")
    void test7() {

        //given
        String token = "@#$%^";
        Optional<VerificationToken> expected = Optional.empty();

        //when
        Optional<VerificationToken> actual = verificationTokenRepository.findByToken(token);

        //then
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    @DisplayName("findByToken case tokenVal assigned to some VerificationToken object")
    @Sql(scripts = "/scripts/userScript.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into verification_tokens (id, token, user_username) values (1, 'tokenVal', 'Alaba')")
    void test8() {

        //given
        String token = "tokenVal";
        Optional<VerificationToken> expected = Optional.of(
                VerificationToken.builder()
                        .id(1)
                        .token("tokenVal")
                        .user(User.builder()
                                .email("alaba@gmail.com")
                                .photoUrl("www.someUrl.com")
                                .username("Alaba")
                                .build())
                        .build());

        //when
        Optional<VerificationToken> actual = verificationTokenRepository.findByToken(token);

        //then
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    @DisplayName("getExpirationDateForTokenString case token value is null")
    void test9() {

        //given
        String token = null;
        Optional<LocalDateTime> expected = Optional.empty();

        //when
        Optional<LocalDateTime> actual = verificationTokenRepository.getExpirationDateForTokenString(token);
        //then
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    @DisplayName("getExpirationDateForTokenString case token value is null")
    @Sql(scripts = "/scripts/userScript.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into verification_tokens (token, user_username, expiration_date_time) values ('tokenVal', 'Alaba', '2020-10-20 11:00:00')")
    void test10() {

        //given
        String token = "tokenVal";
        Optional<LocalDateTime> expected = Optional.of(
                LocalDateTime.of(
                        2020, 10, 20,
                        11, 0
                )
        );

        //when
        Optional<LocalDateTime> actual = verificationTokenRepository.getExpirationDateForTokenString(token);
        //then
        assertThat(actual, is(equalTo(expected)));
    }

}

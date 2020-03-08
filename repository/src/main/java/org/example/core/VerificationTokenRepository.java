package org.example.core;

import org.example.core.entity.User;
import org.example.core.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {

    @Query(value = "select vt.token from verification_tokens vt where vt.user.username =:username")
    Optional<String> getTokenForUser(String username);

    default Optional<User> getUserWithToken(String token) {

        return findByToken(token)
                .map(VerificationToken::getUser);

    }

    Optional<VerificationToken> findByUserUsername(String username);

    Optional<VerificationToken> findByToken(String token);

    @Query("select v_t from verification_tokens  as v_t where v_t.token =:token")
    Optional<VerificationToken> getVerificationTokenWithTokenString(@Param("token") String token);

    default LocalDateTime getExpirationDateForTokenString(String token) {
        getVerificationTokenWithTokenString(token);
        return LocalDateTime.now();
    }
}

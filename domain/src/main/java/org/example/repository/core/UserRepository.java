package org.example.repository.core;

import org.example.entity.core.entity.User;
import org.example.repository.core.projection.UserMail;
import org.example.repository.core.projection.UserPhotoUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<UserPhotoUrl> findOptionalByUsername(String username);

    Optional<UserMail> findEmailByUsername(String username);

    @Query(value = "select u.email from users u", nativeQuery = true)
    List<UserMail> getUserEmails();

    List<UserPhotoUrl> findPhotoUrlByUsernameIn(Set<String> userNames);

    @Query(value = "select g.users from Game g where g.id = :id")
    Set<User> findAllByGameId(Integer id);
}

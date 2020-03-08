package org.example.core;

import org.example.core.entity.User;
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

    @Query(value = "select u.photoUrl from user u where u.username = :username")
    Object getPhotoUrlByUsername(String username);

    @Query(value = "select u.email from user u")
    List<String> getUserEmails();

    @Query(value = "select u.photoUrl from user u where u.username in :userNames")
    List<Object> getPhotoUrlByUsernameIn(Set<String> userNames);
}

package org.example.fm;

import org.example.fm.entity.Country;
import org.example.fm.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    Optional<Country> findByName(String name);

    @Transactional
    @Query(value = "select c.players from Country c where c.name =:name")
     Set<Player> getPlayersForCountry(String name);
}

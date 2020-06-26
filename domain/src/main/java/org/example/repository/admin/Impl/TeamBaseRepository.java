package org.example.repository.admin.Impl;

import org.example.annotations.NoJpaRepository;
import org.example.model.admin.TeamBaseDto;
import org.example.repository.admin.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoJpaRepository
public class TeamBaseRepository implements GenericRepository<TeamBaseDto> {

    private final EntityManager entityManager;

    @Autowired
    public TeamBaseRepository(@Qualifier(value = "hbn") EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<TeamBaseDto> findAll() {
        List<Object[]> records = entityManager.createNativeQuery("select * from team_base").getResultList();

        return records
                .stream()
                .map(record -> TeamBaseDto.builder()
                        .name((String) record[0])
                        .countryName((String) record[1])
                        .leagueName((String) record[2])
                        .backgroundUrl((String) record[3])
                        .logoUrl((String) record[4])
                        .shirtColors((String) record[5])
                        .build())
                .collect(Collectors.toList());
    }

    public List<TeamBaseDto> findAllByLeagueName(String name) {

        List<Object[]> records = entityManager.createNativeQuery("select * from team_base where league_name = ?1")
                .setParameter(1, name)
                .getResultList();

        return records
                .stream()
                .map(record -> TeamBaseDto.builder()
                        .name((String) record[0])
                        .countryName((String) record[1])
                        .leagueName((String) record[2])
                        .backgroundUrl((String) record[3])
                        .logoUrl((String) record[4])
                        .shirtColors((String) record[5])
                        .build())
                .collect(Collectors.toList());
    }

    public Optional<TeamBaseDto> findByName(String teamName) {

        List<Object[]> records = entityManager.createNativeQuery("select * from team_base where name = ?1")
                .setParameter(1, teamName)
                .getResultList();

        return records
                .stream()
                .map(record -> TeamBaseDto.builder()
                        .name((String) record[0])
                        .countryName((String) record[1])
                        .leagueName((String) record[2])
                        .backgroundUrl((String) record[3])
                        .logoUrl((String) record[4])
                        .shirtColors((String) record[5])
                        .build())
                .findFirst();
    }

}

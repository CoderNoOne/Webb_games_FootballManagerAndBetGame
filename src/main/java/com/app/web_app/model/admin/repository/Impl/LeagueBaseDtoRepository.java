package com.app.web_app.model.admin.repository.Impl;

import com.app.web_app.model.admin.LeagueBaseDto;
import com.app.web_app.model.admin.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class LeagueBaseDtoRepository implements GenericRepository<LeagueBaseDto> {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<LeagueBaseDto> findAll() {

        List<Object[]> records = entityManager.createNativeQuery("select * from league_base").getResultList();
        return records.stream()
                .map(record -> LeagueBaseDto
                        .builder()
                        .name((String) record[0])
                        .leagueType((String) record[1])
                        .countryName((String) record[2])
                        .build())
                .collect(Collectors.toList());
    }

    public Optional<LeagueBaseDto> findByName(String leagueName) {

        List<Object[]> records = entityManager.createNativeQuery("select * from league_base").getResultList();

        return records.stream()
                .map(record -> LeagueBaseDto
                        .builder()
                        .countryName((String) record[0])
                        .leagueType(record[1] != null ? (String) record[1] : null)
                        .countryName((String) record[2])
                        .build())
                .findFirst();
    }

    public Optional<LeagueBaseDto> findByType(String leagueType){

        List<Object[]> records = entityManager.createNativeQuery("select * from league_base where league_type = ?1")
                .setParameter(1,leagueType)
                .getResultList();

        return records.stream()
                .map(record -> LeagueBaseDto
                        .builder()
                        .countryName((String) record[0])
                        .leagueType(record[1] != null ? (String) record[1] : null)
                        .countryName((String) record[2])
                        .build())
                .findFirst();

    }
}

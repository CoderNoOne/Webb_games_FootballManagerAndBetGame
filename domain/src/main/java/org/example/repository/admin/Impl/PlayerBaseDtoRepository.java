package org.example.repository.admin.Impl;

import org.example.annotations.NoJpaRepository;
import org.example.model.admin.PlayerBaseDto;
import org.example.repository.admin.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@NoJpaRepository
public class PlayerBaseDtoRepository implements GenericRepository<PlayerBaseDto> {

    private final EntityManager entityManager;

    @Autowired
    public PlayerBaseDtoRepository(@Qualifier(value = "hbn") EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<PlayerBaseDto> findAll() {

        List<Object[]> records = entityManager.createNativeQuery("select * from players_base").getResultList();

        return records.stream()
                .map(record -> PlayerBaseDto.builder()
                        .id((Integer) record[0])
                        .firstName((String) record[1])
                        .lastName((String) record[2])
                        .countryName((String) record[3])
                        .teamName((String) record[4])
                        .imageUrl((String) record[5])
                        .build())
                .collect(Collectors.toList());
    }

    public List<PlayerBaseDto> findAllByTeamName(String teamName) {

        List<Object[]> records = entityManager.createNativeQuery("select * from players_base where team_name = ?1")
                .setParameter(1, teamName).getResultList();

        return records.stream()
                .map(record -> PlayerBaseDto.builder()
                        .id((Integer) record[0])
                        .firstName((String) record[1])
                        .lastName((String) record[2])
                        .countryName((String) record[3])
                        .teamName((String) record[4])
                        .imageUrl((String) record[5])
                        .build())
                .collect(Collectors.toList());

    }

}

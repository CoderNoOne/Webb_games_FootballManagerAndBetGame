package org.example.admin.Impl;

import lombok.RequiredArgsConstructor;
import org.example.admin.GenericRepository;
import org.example.model.admin.PlayerAttributesBaseDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PlayerAttributesBaseDtoRepository implements GenericRepository<PlayerAttributesBaseDto> {


    private final EntityManager entityManager;

    @Override
    public List<PlayerAttributesBaseDto> findAll() {

        List<Object[]> records = entityManager.createNativeQuery("select * from players_attributes_base").getResultList();

        return records.stream()
                .map(record -> PlayerAttributesBaseDto.builder()
                        .attacking((Integer) record[0])
                        .defending((Integer) record[1])
                        .heading((Integer) record[2])
                        .dribbling((Integer) record[3])
                        .passing((Integer) record[4])
                        .technique((Integer) record[5])
                        .teamWork((Integer) record[6])
                        .playerId((Integer) record[7])
                        .speed((Integer) record[8])
                        .aggression((Integer) record[9])
                        .oneOnOnes((Integer) record[10])
                        .penaltySaving((Integer) record[11])
                        .penaltyScoring((Integer) record[12])
                        .reflexes((Integer) record[13])
                        .build())
                .collect(Collectors.toList());


    }
}

package org.example.admin.Impl;

import lombok.RequiredArgsConstructor;
import org.example.admin.GenericRepository;
import org.example.model.admin.PositionBaseDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PositionsBaseDtoRepository implements GenericRepository<PositionBaseDto> {

    private final EntityManager entityManager;

    @Override
    public List<PositionBaseDto> findAll() {

        final List<Object[]> records = entityManager.createNativeQuery("select * from positions_base").getResultList();

        return records
                .stream()
                .map(record -> PositionBaseDto
                        .builder()
                        .playerId((Integer) record[0])
                        .position((String) record[1])
                        .build())
                .collect(Collectors.toList());
    }

    public List<PositionBaseDto> findAllByPLayerId(Integer id) {

        List <Object[]> records = entityManager.createNativeQuery("select * from positions_base where player_id = 1?")
                .setParameter(1, id).getResultList();

        return records
                .stream()
                .map(record -> PositionBaseDto
                        .builder()
                        .playerId((Integer) record[0])
                        .position((String) record[1])
                        .build())
                .collect(Collectors.toList());
    }
}

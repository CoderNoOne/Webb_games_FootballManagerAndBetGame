package org.example.repository.admin.Impl;

import org.example.annotations.NoJpaRepository;
import org.example.model.admin.PositionBaseDto;
import org.example.repository.admin.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@NoJpaRepository
public class PositionsBaseDtoRepository implements GenericRepository<PositionBaseDto> {

    private final EntityManager entityManager;

    @Autowired
    public PositionsBaseDtoRepository(@Qualifier(value = "hbn") EntityManager entityManager) {
        this.entityManager = entityManager;
    }

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

        List<Object[]> records = entityManager.createNativeQuery("select * from positions_base where player_id = ?1")
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

package com.app.web_app.model.admin.repository.Impl;

import com.app.web_app.model.admin.PositionBaseDto;
import com.app.web_app.model.admin.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PositionsBaseDtoRepository implements GenericRepository<PositionBaseDto> {

    @Autowired
    private EntityManager entityManager;

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

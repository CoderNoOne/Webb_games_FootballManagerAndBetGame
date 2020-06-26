package org.example.repository.admin.Impl;

import org.example.annotations.NoJpaRepository;
import org.example.model.admin.CountryBaseDto;
import org.example.repository.admin.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoJpaRepository
public class CountryBaseDtoRepository implements GenericRepository<CountryBaseDto> {

    private final EntityManager entityManager;

    @Autowired
    public CountryBaseDtoRepository(@Qualifier(value = "hbn") EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<CountryBaseDto> findAll() {

        List<Object> results = entityManager.createNativeQuery("select * from country_base")
                .getResultList();

        return results
                .stream()
                .map(record -> (String) record)
                .map(CountryBaseDto::new)
                .collect(Collectors.toList());

    }

    public Optional<CountryBaseDto> findByName(String countryName) {

        final Optional<Object> result = entityManager.createNativeQuery("select * from country_base where name = ?1 ")
                .setParameter(1, countryName).getResultList().stream().findFirst();

        return result.isPresent() ? result
                .map(innerValue -> CountryBaseDto
                        .builder()
                        .name((String) innerValue)
                        .build())
                : Optional.empty();
    }


}

package com.app.web_app.model.admin.repository.Impl;

import com.app.web_app.model.admin.CountryBaseDto;
import com.app.web_app.model.admin.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CountryBaseDtoRepository implements GenericRepository<CountryBaseDto> {

    @Autowired
    private EntityManager entityManager;

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

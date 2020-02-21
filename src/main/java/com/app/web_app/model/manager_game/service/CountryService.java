package com.app.web_app.model.manager_game.service;

import com.app.web_app.model.manager_game.Country;
import com.app.web_app.model.manager_game.dto.CountryDto;
import com.app.web_app.model.manager_game.mapper.ManagerMapper;
import com.app.web_app.model.manager_game.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ManagerMapper managerMapper;

    public Integer saveAllCountries(CountryDto... countries) {

        return countryRepository.saveAll(Arrays.stream(countries)
                .map(countryDto -> Country.builder()
                        .name(countryDto.getCountryName())
                        .build())
                .collect(Collectors.toList())).size();

    }

    public Optional<Country> getCountryByName(String countryName) {
        return Optional.empty();
    }
}

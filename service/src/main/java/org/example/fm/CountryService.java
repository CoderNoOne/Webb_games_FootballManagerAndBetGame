package org.example.fm;

import lombok.RequiredArgsConstructor;
import org.example.fm.entity.Country;
import org.example.model.fm.CountryDto;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;


    public Integer saveAllCountries(CountryDto... countries) {

        return countryRepository.saveAll(Arrays.stream(countries)
                .map(countryDto -> Country.builder()
                        .name(countryDto.getName())
                        .build())
                .collect(Collectors.toList())).size();

    }

    public Optional<Country> getCountryByName(String countryName) {
        return Optional.empty();
    }
}

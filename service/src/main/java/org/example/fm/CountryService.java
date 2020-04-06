package org.example.fm;

import lombok.RequiredArgsConstructor;
import org.example.core.exceptions.AppException;
import org.example.fm.entity.Country;
import org.example.model.fm.CountryDto;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    public Integer saveAllCountries(CountryDto... countries) {

        if (countries == null) {
            throw new AppException("Countries is null");
        }

        return countryRepository.saveAll(Arrays.stream(countries)
                .map(countryDto -> Country.builder()
                        .name(countryDto.getName())
                        .build())
                .collect(Collectors.toList()))
                .size();

    }

}

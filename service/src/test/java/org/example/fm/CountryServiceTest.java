package org.example.fm;

import org.example.core.exceptions.AppException;
import org.example.fm.entity.Country;
import org.example.model.fm.CountryDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
class CountryServiceTest {

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryService countryService;

    @Test
    @DisplayName("Save all countries - countries is null")
    void test1() {

        //given
        CountryDto[] countries = null;
        String exceptionMessage = "Countries is null";

        //when
        //then

        AppException appException = assertThrows(AppException.class, () -> countryService.saveAllCountries(countries));
        assertThat(appException.getMessage(), is(equalTo(exceptionMessage)));

        then(countryRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("Save all countries - countries is not null")
    void test2() {

        //given
        CountryDto[] countries = new CountryDto[]{
                CountryDto.builder().name("Poland").build()
        };

        ArgumentCaptor<List<Country>> captorForCountryListToSave = ArgumentCaptor.forClass(List.class);

        List<Country> expected = Collections.singletonList(Country.builder().name("Poland").build());

        //when
        given(countryRepository.saveAll(captorForCountryListToSave.capture()))
                .willReturn(expected);

        //then
        assertThat(countryService.saveAllCountries(countries), is(1));
        then(countryRepository).should(times(1)).saveAll(any());
        then(countryRepository).shouldHaveNoMoreInteractions();
        assertThat(captorForCountryListToSave.getValue(), is(List.of(Country.builder().name("Poland").build())));
    }
}

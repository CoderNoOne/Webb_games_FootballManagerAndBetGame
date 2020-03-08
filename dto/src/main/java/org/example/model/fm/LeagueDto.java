package org.example.model.fm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.validator.hibernate_validators.annotations.HourFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeagueDto {

    private Integer id;

    @NotBlank(message = "Specify a league name")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "start date must be a future date")
    @NotNull(message = "Specify a league start date")
    private LocalDate startDate;

    private Integer gameId;

    @HourFormat
    @NotBlank(message = "Specify a matchTime")
    private String matchTime;

    @Size(min = 2, message = "Minimum number of teams is: {min}")
    private String[] teams;

    private List<MatchDto> matches;

    private CountryDto countryDto;
}

package org.example.model.core;

import lombok.*;
import org.example.model.fm.LeagueDto;

import java.time.LocalDate;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GameDto {

    private Integer id;
    private Boolean active;
    private Set<String> userUsernames;
    private Set<LeagueDto> leagues;
    private LocalDate startDate;
}

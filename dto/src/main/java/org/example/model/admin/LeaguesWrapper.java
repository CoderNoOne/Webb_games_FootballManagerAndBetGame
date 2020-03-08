package org.example.model.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.fm.enums.LeagueTypeDto;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaguesWrapper {

    private List<LeagueTypeDto> leagues;
}

package org.example.model.fm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FMChooseTeamDto {

    Map<Integer, Integer> leagueIdTeamId;
}

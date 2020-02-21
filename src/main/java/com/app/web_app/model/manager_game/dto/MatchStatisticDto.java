package com.app.web_app.model.manager_game.dto;

import com.app.web_app.model.manager_game.FmMatchStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchStatisticDto {

    private Integer id;

    private MatchDto matchDto;

    private String score;

    private Integer minute;

    private FmMatchStatus status;
}

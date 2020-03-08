package org.example.model.fm;

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

    private String status;
}

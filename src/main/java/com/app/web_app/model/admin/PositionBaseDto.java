package com.app.web_app.model.admin;

import lombok.*;

@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
public class PositionBaseDto {

    private Integer playerId;
    private String position;

}

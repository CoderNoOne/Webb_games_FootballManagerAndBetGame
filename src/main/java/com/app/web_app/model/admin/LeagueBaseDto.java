package com.app.web_app.model.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LeagueBaseDto {

    private String name;
    private String countryName;
    private String leagueType;

}

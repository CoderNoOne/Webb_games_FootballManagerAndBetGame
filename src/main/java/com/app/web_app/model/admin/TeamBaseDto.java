package com.app.web_app.model.admin;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TeamBaseDto {

    private String name;
    private String leagueName;
    private String countryName;
    private String backgroundUrl;
    private String logoUrl;
    private String shirtColors;

}

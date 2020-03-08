package org.example.model.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

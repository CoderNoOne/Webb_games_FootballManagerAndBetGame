package com.app.web_app.model.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PlayerBaseDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private String countryName;
    private String teamName;
    private String imageUrl;
}

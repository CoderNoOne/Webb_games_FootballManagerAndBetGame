package org.example.model.fm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlayerDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private Integer number;
    private String imageUrl;
    private Set<String> positions;
    private PlayerAttributesDto playerAttributes;
    private Integer teamId;
    private CountryDto countryDto;
}

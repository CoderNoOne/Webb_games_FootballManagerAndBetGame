package org.example.model.fm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.validator.hibernate_validators.annotations.ValidNumbers;

import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidNumbers
public class PlayersNumberDto {

    private Map<Integer, Integer> playersNumber;
}

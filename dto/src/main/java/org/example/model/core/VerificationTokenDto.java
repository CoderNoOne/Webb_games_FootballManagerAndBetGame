package org.example.model.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerificationTokenDto {

    private Integer id;
    private String token;
    private LocalDateTime expirationDateTime;
    private UserDto userDto;

}

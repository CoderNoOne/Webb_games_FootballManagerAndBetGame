package org.example.model.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.validator.hibernate_validators.annotations.ValidPassword;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ValidPassword(useDetailedMessage = true)
public class PasswordDto {

    private String password;
    private String confirmationPassword;

}

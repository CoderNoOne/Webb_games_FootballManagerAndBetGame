package com.app.web_app.model.user;

import com.app.web_app.validators.hibernate_validators.annotations.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ValidPassword(useDetailedMessage = true)
public class PasswordDto {

    private String password;
    private String confirmationPassword;

}

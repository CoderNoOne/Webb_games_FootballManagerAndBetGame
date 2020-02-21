package com.app.web_app.model.dto;

import com.app.web_app.model.user.PasswordDto;
import com.app.web_app.model.user.enums.Authority;
import com.app.web_app.model.user.enums.Gender;
import com.app.web_app.validators.hibernate_validators.annotations.AdultUser;
import com.app.web_app.validators.hibernate_validators.annotations.ValidPassword;
import com.app.web_app.validators.hibernate_validators.annotations.ValidPhotoType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserDto {

    @Size(min = 2, max = 15, message = "username should contain 2-15 characters")
    @NotBlank(message = "username should not be blank")
    private String username;

    @Pattern(regexp = "[A-Z][a-z]+", message = "first name should start with a capital letter Abc")
    @Size(min = 2, max = 15, message = "first name should contain 2-15 letters")
    private String firstName;

    @Pattern(regexp = "[A-Z][a-z]+", message = "last name should start with a capital letter Abc")
    @Size(min = 2, max = 15, message = "last name should contain 2-15 letters")
    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @AdultUser
    private LocalDate birthDate;

    private String favoriteClub;

    @Email(message = "not valid email address structure")
    @NotBlank(message = "email cannot be blank")
    private String email;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Gender not chosen. Select one")
    private Gender gender;

    @ValidPassword(
            useDetailedMessage = true
            , useCustomMessage = false)
    private PasswordDto password;

    @ValidPhotoType
    private MultipartFile file;

    private Boolean enabled;

    @Enumerated(EnumType.STRING)
    private Set<Authority> authorities;

}

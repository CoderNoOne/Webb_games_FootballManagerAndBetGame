package org.example.core;

import org.example.entity.core.entity.User;
import org.example.entity.core.entity.VerificationToken;
import org.example.entity.core.enums.Authority;
import org.example.entity.core.enums.Gender;
import org.example.model.core.PasswordDto;
import org.example.model.core.UserDto;
import org.example.model.core.VerificationTokenDto;
import org.example.model.core.enums.AuthorityDto;
import org.example.model.core.enums.GenderDto;

import java.util.HashSet;
import java.util.stream.Collectors;

public interface CoreMapper {

    static User mapUserDtoToUser(UserDto userDto) {

        if (userDto != null) {

            return
                    User.builder()
                            .firstName(userDto.getFirstName())
                            .lastName(userDto.getLastName())
                            .username(userDto.getUsername())
                            .enabled(userDto.getEnabled())
                            .birthDate(userDto.getBirthDate())
                            .gender(CoreMapper.mapGenderDtoToGender(userDto.getGender()))
                            .email(userDto.getEmail())
                            .authorities(userDto.getAuthorities() != null ?
                                    userDto.getAuthorities().stream().map(CoreMapper::mapAuthorityDtoToAuthority).collect(Collectors.toSet()) : new HashSet<>())
                            .password(userDto.getPassword().getPassword())
                            .build();

        }

        return null;
    }

    static Authority mapAuthorityDtoToAuthority(AuthorityDto authorityDto) {
        return authorityDto != null ?

                Authority.fromString(authorityDto.name())
                : null;
    }

    static Gender mapGenderDtoToGender(GenderDto genderDto) {

        return genderDto != null ?
                Gender.fromString(genderDto.name()) : null;
    }

    static GenderDto mapGenderToGenderDto(Gender gender) {
        return gender != null ?
                GenderDto.fromString(gender.name())
                : null;
    }

    static UserDto mapUserToUserDto(User user) {

        return user != null ?
                UserDto.builder()
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .username(user.getUsername())
                        .enabled(user.getEnabled())
                        .birthDate(user.getBirthDate())
                        .email(user.getEmail())
                        .gender(CoreMapper.mapGenderToGenderDto(user.getGender()))
                        .password(PasswordDto.builder()
                                .password(user.getPassword())
                                .confirmationPassword(user.getPassword())
                                .build())
                        .build()

                : null;
    }


    static VerificationTokenDto mapVerificationTokenToDto(VerificationToken verificationToken) {

        return verificationToken != null ?

                VerificationTokenDto.builder()
                        .id(verificationToken.getId())
                        .token(verificationToken.getToken())
                        .expirationDateTime(verificationToken.getExpirationDateTime())
                        .userDto(CoreMapper.mapUserToUserDto(verificationToken.getUser()))
                        .build()

                : null;
    }

    static VerificationToken mapVerificationTokenDtoToVerificationToken(VerificationTokenDto verificationTokenDto) {

        return verificationTokenDto != null ?

                VerificationToken.builder()
                        .id(verificationTokenDto.getId())
                        .token(verificationTokenDto.getToken())
                        .expirationDateTime(verificationTokenDto.getExpirationDateTime())
                        .user(CoreMapper.mapUserDtoToUser(verificationTokenDto.getUserDto()))
                        .build()

                : null;
    }
}

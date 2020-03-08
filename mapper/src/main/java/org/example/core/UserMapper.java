package org.example.core;

import org.example.core.entity.User;
import org.example.core.entity.VerificationToken;
import org.example.core.enums.Authority;
import org.example.core.enums.Gender;
import org.example.model.core.UserDto;
import org.example.model.core.VerificationTokenDto;
import org.example.model.core.enums.AuthorityDto;
import org.example.model.core.enums.GenderDto;

import java.util.HashSet;
import java.util.stream.Collectors;

public interface UserMapper {

    static User mapUserDtoToUser(UserDto userDto) {

        if (userDto != null) {

            return
                    User.builder()
                            .firstName(userDto.getFirstName())
                            .lastName(userDto.getLastName())
                            .username(userDto.getUsername())
                            .enabled(userDto.getEnabled())
                            .birthDate(userDto.getBirthDate())
                            .gender(UserMapper.mapGenderDtoToGender(userDto.getGender()))
                            .email(userDto.getEmail())
                            .authorities(userDto.getAuthorities() != null ?
                                    userDto.getAuthorities().stream().map(UserMapper::mapAuthorityDtoToAuthority).collect(Collectors.toSet()) : new HashSet<>())
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
                        .gender(UserMapper.mapGenderToGenderDto(user.getGender()))
                        .build()

                : null;
    }


    static VerificationTokenDto mapVerificationTokenToDto(VerificationToken verificationToken) {

        return verificationToken != null ?

                VerificationTokenDto.builder()
                        .id(verificationToken.getId())
                        .token(verificationToken.getToken())
                        .expirationDateTime(verificationToken.getExpirationDateTime())
                        .userDto(UserMapper.mapUserToUserDto(verificationToken.getUser()))
                        .build()

                : null;
    }

    static VerificationToken mapVerificationTokenDtoToVerificationToken(VerificationTokenDto verificationTokenDto) {

        return verificationTokenDto != null ?

                VerificationToken.builder()
                        .id(verificationTokenDto.getId())
                        .token(verificationTokenDto.getToken())
                        .expirationDateTime(verificationTokenDto.getExpirationDateTime())
                        .user(UserMapper.mapUserDtoToUser(verificationTokenDto.getUserDto()))
                        .build()

                : null;
    }

}

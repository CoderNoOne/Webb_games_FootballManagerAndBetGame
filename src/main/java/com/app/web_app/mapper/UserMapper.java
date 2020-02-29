package com.app.web_app.mapper;

import com.app.web_app.model.dto.UserDto;
import com.app.web_app.model.user.User;
import com.app.web_app.model.user.enums.Gender;
import com.app.web_app.service.ImgurService;
import com.app.web_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ImgurService imgurService;

    public User mapUserDtoToUser(UserDto userDto) {

        if (userDto != null) {
            boolean customPhoto = userDto.getFile().getSize() != 0;

            return
                    User.builder()
                            .firstName(userDto.getFirstName())
                            .lastName(userDto.getLastName())
                            .username(userDto.getUsername())
                            .enabled(userDto.getEnabled())
                            .birthDate(userDto.getBirthDate())
                            .gender(userDto.getGender())
                            .email(userDto.getEmail())
                            .authorities(userDto.getAuthorities())
                            .password(userDto.getPassword().getPassword())
                            .photoUrl(customPhoto ? imgurService.upload(userDto.getFile())
                                    : userDto.getGender().equals(Gender.FEMALE) ?
                                    UserService.DEFAULT_FEMALE_AVATAR_URL : UserService.DEFAULT_MALE_AVATAR_URL)
                            .build();

        }

        return null;
    }

    public UserDto mapUserToUserDto(User user) {

        return user != null ?
                UserDto.builder()
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .username(user.getUsername())
                        .enabled(user.getEnabled())
                        .birthDate(user.getBirthDate())
                        .email(user.getEmail())
                        .gender(user.getGender())
                        .build()

                : null;
    }

}

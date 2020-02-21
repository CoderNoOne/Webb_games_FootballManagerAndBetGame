package com.app.web_app.mapper;

import com.app.web_app.model.dto.VerificationTokenDto;
import com.app.web_app.model.user.VerificationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VerificationTokenMapper {

    private final UserMapper userMapper;

    public VerificationTokenDto mapVerificationTokenToDto(VerificationToken verificationToken) {

        return verificationToken != null ?

                VerificationTokenDto.builder()
                        .id(verificationToken.getId())
                        .token(verificationToken.getToken())
                        .expirationDateTime(verificationToken.getExpirationDateTime())
                        .userDto(userMapper.mapUserToUserDto(verificationToken.getUser()))
                        .build()

                : null;
    }

    public VerificationToken mapVerificationTokenDtoToVerificationToken(VerificationTokenDto verificationTokenDto) {

        return verificationTokenDto != null ?

                VerificationToken.builder()
                        .id(verificationTokenDto.getId())
                        .token(verificationTokenDto.getToken())
                        .expirationDateTime(verificationTokenDto.getExpirationDateTime())
                        .user(userMapper.mapUserDtoToUser(verificationTokenDto.getUserDto()))
                        .build()

                : null;
    }

}

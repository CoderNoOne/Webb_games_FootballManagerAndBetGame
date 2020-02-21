package com.app.web_app.service;

import com.app.web_app.mapper.UserMapper;
import com.app.web_app.mapper.VerificationTokenMapper;
import com.app.web_app.model.dto.VerificationTokenDto;
import com.app.web_app.model.user.User;
import com.app.web_app.model.user.VerificationToken;
import com.app.web_app.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class VerificationTokenService {

    private static Integer TOKEN_EXPIRATION_TIME_IN_MINUTES = 5;

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserMapper userMapper;
    private final VerificationTokenMapper verificationTokenMapper;

    public VerificationTokenDto saveOrUpdateToken(VerificationTokenDto verificationTokenDto) {


        verificationTokenRepository.findByUserUsername(verificationTokenDto.getUserDto().getUsername())
                .ifPresentOrElse(verificationToken -> {
                            verificationToken.setToken(verificationTokenDto.getToken());
                            verificationToken.setExpirationDateTime(LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_TIME_IN_MINUTES));
                        },
                        () -> {
                            verificationTokenDto.setExpirationDateTime(LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_TIME_IN_MINUTES));
                            verificationTokenRepository.save(verificationTokenMapper.mapVerificationTokenDtoToVerificationToken(verificationTokenDto));
                        });

        return verificationTokenDto;
    }

    public Optional<String> getTokenForUser(String username) {

        return verificationTokenRepository.getTokenForUser(username);

    }

    public Optional<User> getUserAssociatedWithToken(String token) {

        return verificationTokenRepository.getUserWithToken(token);
    }

    public Optional<VerificationToken> getVerificationTokenByToken(String token) {

        return verificationTokenRepository.findByToken(token);
    }

    public LocalDateTime getExpirationDateForToken(String token) {
        return verificationTokenRepository.getExpirationDateForTokenString(token);
    }
}

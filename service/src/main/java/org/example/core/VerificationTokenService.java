package org.example.core;

import lombok.RequiredArgsConstructor;
import org.example.core.exceptions.AppException;
import org.example.entity.core.entity.User;
import org.example.entity.core.entity.VerificationToken;
import org.example.model.core.VerificationTokenDto;
import org.example.repository.core.VerificationTokenRepository;
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

    public VerificationTokenDto saveOrUpdateToken(VerificationTokenDto verificationTokenDto) {


        verificationTokenRepository.findByUserUsername(verificationTokenDto.getUserDto().getUsername())
                .ifPresentOrElse(verificationToken -> {
                            verificationToken.setToken(verificationTokenDto.getToken());
                            verificationToken.setExpirationDateTime(LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_TIME_IN_MINUTES));
                        },
                        () -> {
                            verificationTokenDto.setExpirationDateTime(LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_TIME_IN_MINUTES));
                            verificationTokenRepository.save(CoreMapper.mapVerificationTokenDtoToVerificationToken(verificationTokenDto));
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
        return verificationTokenRepository.getExpirationDateForTokenString(token)
                .orElseThrow(() -> new AppException("No expiration date set for token"));
    }
}

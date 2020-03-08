package org.example.core;

import lombok.RequiredArgsConstructor;
import org.example.core.entity.User;
import org.example.exceptions.AppException;
import org.example.model.core.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    public static final String DEFAULT_MALE_AVATAR_URL = "https://i.imgur.com/ku045EO.jpg";
    public static final String DEFAULT_FEMALE_AVATAR_URL = "https://i.imgur.com/fi9hWjW.jpg";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User save(UserDto userDto) {

        if (userDto == null) {
            throw new AppException("UserDto is null");
        }

        User user = UserMapper.mapUserDtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    public User update(String username, User userDetails) {

        return userRepository.findByUsername(username)
                .map(userFromDb -> {
                    userFromDb.setEnabled(userDetails.getEnabled() != null ? userDetails.getEnabled() : userFromDb.getEnabled());
                    return userFromDb;
                })
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with username: %s doesn't exist", username)));
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean doPasswordMatches(String pass1, String pass2) {
        return passwordEncoder.matches(pass1, pass2);
    }

    public String getPhotoUrlForUsername(String username) {
        return ((String) userRepository.getPhotoUrlByUsername(username));
    }

    public List<String> getUserEmails() {
        return userRepository.getUserEmails();
    }

    public Optional<UserDto> getUserDtoByEmail(String email) {

        return userRepository.findByEmail(email)
                .map(UserMapper::mapUserToUserDto);
    }

    public boolean changePassword(String username, String password) {

        AtomicBoolean isPasswordChanged = new AtomicBoolean(false);

        userRepository.findByUsername(username)
                .ifPresent(
                        user -> {
                            user.setPassword(passwordEncoder.encode(password));
                            isPasswordChanged.set(true);
                        }
                );

        return isPasswordChanged.get();
    }

    public Map<String, String> getPhotoUrlForUsernameIn(Set<String> userNames) {

        return userRepository.findAllById(userNames)
                .stream()
                .collect(Collectors.toMap(
                        User::getUsername,
                        User::getPhotoUrl
                ));

    }

    public String getEmailForUsername(String username) {

        if (username == null) {
            throw new AppException("Username is null");
        }

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("User with username: " + username))
                .getEmail();
    }
}

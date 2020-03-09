package org.example.security;

import lombok.RequiredArgsConstructor;
import org.example.core.UserRepository;
import org.example.core.entity.User;
import org.example.core.enums.Authority;
import org.example.core.exceptions.AppException;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Qualifier("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private String username;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new AppException("Username not found"));

        this.username = username;
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(), true, true, true,
                getAuthorities(user.getAuthorities())
        );
    }

    private List<GrantedAuthority> getAuthorities(Set<Authority> authorities) {
        return authorities
                .stream()
                .map(auth -> new SimpleGrantedAuthority(auth.name()))
                .collect(Collectors.toList());
    }
}

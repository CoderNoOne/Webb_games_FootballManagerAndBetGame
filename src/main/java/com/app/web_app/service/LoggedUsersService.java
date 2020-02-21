package com.app.web_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoggedUsersService {

    private final SessionRegistry sessionRegistry;

    @Cacheable(value = "loggedUsers")
    public Set<String> getLoggedUsers() {

        return sessionRegistry.getAllPrincipals()
                .stream()
                .filter(principal -> principal instanceof UserDetails)
                .map(UserDetails.class::cast)
                .map(UserDetails::getUsername)
                .collect(Collectors.toSet());
    }

    @CachePut(value = "loggedUsers")
    public Set<String> updateLoggedUsers() {

        return sessionRegistry.getAllPrincipals()
                .stream()
                .filter(principal -> principal instanceof UserDetails)
                .map(UserDetails.class::cast)
                .map(UserDetails::getUsername)
                .collect(Collectors.toSet());
    }
}

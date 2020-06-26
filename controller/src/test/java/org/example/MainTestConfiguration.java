package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.core.UserService;
import org.example.entity.core.entity.User;
import org.example.repository.core.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ComponentScan
@Configuration
@EnableAutoConfiguration
//@Slf4j
public class MainTestConfiguration extends MainConfiguration {

//    @Autowired
//    private UserRepository userRepository;

//    @Bean
//    @Qualifier("userDetailsServiceImpl")
//    public UserDetailsService userDetailsServiceImpl() {
//
//        List<User> all = userRepository.findAll();
//        log.info("users: " + all);
//
//        List<UserDetails> usersFromDb = all.stream().map(user -> (UserDetails) new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                user.getEnabled(), true, true, true,
//                user.getAuthorities()
//                        .stream()
//                        .map(auth -> new SimpleGrantedAuthority(auth.name()))
//                        .collect(Collectors.toList())
//        )).collect(Collectors.toList());
//
//        return new InMemoryUserDetailsManager(usersFromDb);
//
//
//    }
}

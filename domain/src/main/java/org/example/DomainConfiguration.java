package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@Configuration
@ComponentScan
public class DomainConfiguration {

    @Bean
    @Profile("prod")
    public EntityManager hbn() {
        return Persistence.createEntityManagerFactory("HBN").createEntityManager();
    }
}

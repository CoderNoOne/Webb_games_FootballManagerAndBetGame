package org.example;

import org.example.repository.admin.Impl.CountryBaseDtoRepository;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.sql.DataSource;


@Configuration
@ComponentScan(basePackageClasses = CountryBaseDtoRepository.class)
public class NoJpaTestConfiguration {

    /*for executing sql statements before test methods (@Sql annotations) of hibernate repositories */
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .username("sa")
                .password("")
                .url("jdbc:h2:mem:test;MODE=MySQL")
                .build();
    }

    @Bean
    public EntityManager hbn() {
        return Persistence.createEntityManagerFactory("HBN_TEST").createEntityManager();
    }
}

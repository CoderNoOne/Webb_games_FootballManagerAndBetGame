package org.example;

import org.example.repository.admin.Impl.CountryBaseDtoRepository;
import org.example.repository.core.UserRepository;
import org.example.repository.fm.GameRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableCaching
@EntityScan(basePackages = {"org.example.entity"})
@EnableJpaRepositories(basePackages = {"org.example.repository"})
@PropertySources({
        @PropertySource(value = "classpath:/db-${spring.profiles.active}.properties", ignoreResourceNotFound = true),
        @PropertySource(value = "classpath:/mail_service.properties")
}
)
public class MainConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(MainConfiguration.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerCustomizer() {
        return factory -> {
            factory.addErrorPages(
                    new ErrorPage(HttpStatus.NOT_FOUND, "/notFound"),
                    new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/internalServerError")
            );

        };
    }
}

package com.app.web_app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableCaching
public class DemoApplication {

    public static void main(String[] args) {
         SpringApplication.run(DemoApplication.class, args);
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

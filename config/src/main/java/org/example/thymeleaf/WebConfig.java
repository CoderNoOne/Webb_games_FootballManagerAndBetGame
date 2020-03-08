package org.example.thymeleaf;

import lombok.RequiredArgsConstructor;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@RequiredArgsConstructor
public class WebConfig extends WebMvcConfigurationSupport {

    private final PlayerDtoFormatter playerDtoFormatter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(playerDtoFormatter);
    }
}

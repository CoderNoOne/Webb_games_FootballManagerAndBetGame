package com.app.web_app.config.thymeleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

public class WebConfig extends WebMvcConfigurationSupport {

    @Autowired
    private PlayerDtoFormatter playerDtoFormatter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(playerDtoFormatter);
    }
}

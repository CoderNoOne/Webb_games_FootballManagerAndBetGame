package org.example.thymeleaf;

import lombok.RequiredArgsConstructor;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@RequiredArgsConstructor
//@Configuration
//@EnableWebMvc
public class WebConfig extends WebMvcConfigurationSupport {

    private final PlayerDtoFormatter playerDtoFormatter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(playerDtoFormatter);
    }

//    @Bean
//    public InternalResourceViewResolver getInternalResourceViewResolver() {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix("/resources/templates/");
//        resolver.setSuffix(".html");
//
//        return resolver;
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//        registry.addResourceHandler("/resources/**")
//                .addResourceLocations("/resources/");
//
////        registry.addResourceHandler("/js/**")
////                .addResourceLocations("/WEB-INF/js/");
//    }
}

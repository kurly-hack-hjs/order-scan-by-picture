package com.hackaton.kurly.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebCorsConfig implements WebMvcConfigurer {
    @Value("${front-url}")
    private String frontUrl;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000/","http://127.0.0.1:3000/","http://localhost:5000/","http://127.0.0.1:5000/", frontUrl )
                .allowCredentials(true); //프론트 테스트용
    }
}
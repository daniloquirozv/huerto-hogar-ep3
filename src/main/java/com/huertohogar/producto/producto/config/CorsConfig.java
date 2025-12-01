package com.huertohogar.producto.producto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class CorsConfig {
    
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        
        // Permitir credenciales
        config.setAllowCredentials(false);
        
        // Orígenes permitidos
        config.setAllowedOrigins(Arrays.asList(
            "http://test-app-react-huerto-hogar-ep3.s3-website-us-east-1.amazonaws.com",
            "http://huertohogar-jwt-s3.s3-website-us-east-1.amazonaws.com",
            "http://localhost:3000"
        ));
        
        // Métodos permitidos
        config.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
        ));
        
        // Headers permitidos
        config.setAllowedHeaders(Collections.singletonList("*"));
        
        // Headers expuestos
        config.setExposedHeaders(Arrays.asList(
            "Authorization",
            "Content-Type"
        ));
        
        // Tiempo de caché para preflight
        config.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        
        return new CorsFilter(source);
    }
}
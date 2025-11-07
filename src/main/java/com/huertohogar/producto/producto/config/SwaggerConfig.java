package com.huertohogar.producto.producto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                    .info(new Info()
                    .title("Api productos")
                    .version("0.1.3")
                .description("API para la gestión de productos en HuertoHogar"));
            }       
    

}

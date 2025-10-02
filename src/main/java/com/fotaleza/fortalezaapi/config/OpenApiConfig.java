package com.fotaleza.fortalezaapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Fortaleza API")
                        .version("1.0")
                        .description("API para POS, CRM e Inventario de la tienda La Fortaleza"));
    }
}

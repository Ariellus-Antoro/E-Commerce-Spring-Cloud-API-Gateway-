package com.ecommerce.review.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI reviewOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Review Service API")
                        .description("Microservice REST API for Product Customer Reviews")
                        .version("1.0.0"));
    }
}
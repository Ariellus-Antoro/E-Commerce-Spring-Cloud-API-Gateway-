package com.ecommerce.shipment.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI shipmentOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Shipment Service API")
                        .description("Microservice REST API for Managing Logistics & Shipments")
                        .version("1.0.0"));
    }
}
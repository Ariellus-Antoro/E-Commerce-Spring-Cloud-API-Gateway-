package com.ecommerce.review.config;

import com.netflix.discovery.shared.transport.jersey3.Jersey3TransportClientFactories;
import com.netflix.discovery.shared.transport.jersey.TransportClientFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EurekaConfig {

    @Bean
    public TransportClientFactories<?> jersey3TransportClientFactories() {
        return new Jersey3TransportClientFactories();
    }
}
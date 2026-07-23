package com.tr.gateway2.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class LoggingWebFilter implements  WebFilter, Ordered{
    private static final Logger log = LoggerFactory.getLogger(LoggingWebFilter.class);
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        long startTime = System.currentTimeMillis();
        String path = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getMethod().name();

        log.info("Incoming Request: [{}] {}", method, path);

        // Menggunakan doFinally agar log tetap dicetak meskipun request diblokir/error di tengah jalan
        return chain.filter(exchange).doFinally(signalType -> {
            long executeTime = System.currentTimeMillis() - startTime;
            int statusCode = exchange.getResponse().getStatusCode() != null ?
                    exchange.getResponse().getStatusCode().value() : 0;

            log.info("Request Completed: [{}] {} | Status: {} | Duration: {} ms",
                    method, path, statusCode, executeTime);
        });
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}

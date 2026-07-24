package com.tr.gateway2.filter;
import com.tr.gateway2.utils.JwtUtil;

import io.jsonwebtoken.Claims;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthFilter implements WebFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain){
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // Bypass endpoint Auth dan Swagger
        if (path.contains("/api/auth") || path.contains("/v3/api-docs") || path.contains("/swagger-ui")) {
            return chain.filter(exchange);
        }

        // Cek Header
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return this.onError(exchange, "Header Authorization kosong atau format salah");
        }

        // Validasi
        String token = authHeader.substring(7);
        if (jwtUtil.isInvalid(token)) {
            return this.onError(exchange, "Token JWT tidak valid atau sudah kedaluwarsa");
        }

        try {
            Claims claims = jwtUtil.getAllClaimsFromToken(token);
            String username = claims.getSubject();
            
            // userId di payload
            String userId = claims.get("userId") != null ? String.valueOf(claims.get("userId")) : "";

            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                    .header("X-User-Id", userId)
                    .header("X-Username", username)
                    .build();

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, null);

            return chain.filter(exchange.mutate().request(modifiedRequest).build())
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));

        } catch (Exception e) {
            return this.onError(exchange, "Gagal memproses Token JWT");
        }
    }

    private Mono<Void> onError(ServerWebExchange exchange, String errorMessage) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String jsonResponse = String.format("{\"status\": 401, \"error\": \"Unauthorized\", \"message\": \"%s\"}", errorMessage);
        byte[] bytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);

        return response.writeWith(Mono.just(buffer));
    }


}

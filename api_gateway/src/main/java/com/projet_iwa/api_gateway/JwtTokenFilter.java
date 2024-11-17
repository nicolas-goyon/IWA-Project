package com.projet_iwa.api_gateway;

import com.projet_iwa.api_gateway.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtTokenFilter implements GatewayFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            // Si le token n'est pas présent ou mal formaté
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        token = token.substring(7);  // Retirer le "Bearer " du token

        if (!jwtTokenUtil.validateToken(token)) {
            // Si le token est invalide
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Si le token est valide, continuer avec la chaîne de filtres
        return chain.filter(exchange);
    }
}

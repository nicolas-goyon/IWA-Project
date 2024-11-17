package com.projet_iwa.api_gateway;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import com.projet_iwa.api_gateway.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtAuthentificationFilter implements GatewayFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthentificationFilter.class);

    // Constructeur qui prend la clé secrète
    public JwtAuthentificationFilter() {
        this.jwtTokenUtil = new JwtTokenUtil();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Récupérer le JWT depuis l'en-tête Authorization
        String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            logger.warn("Authorization header is missing or invalid");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authorizationHeader.substring(7);
        try {
            // Log du token reçu pour vérifier qu'il est bien extrait
            logger.info("Received token: {}", token);

            // Valider le token JWT
            if (!jwtTokenUtil.validateToken(token)) {
                logger.warn("Invalid token: {}", token);
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // Extraire l'ID utilisateur du token JWT
            String iduserString = jwtTokenUtil.getUserIdFromToken(token);
            Long iduser = Long.parseLong(iduserString);

            // Ajouter l'ID utilisateur dans l'en-tête X-User-Id
            exchange = exchange.mutate()
                    .request(r -> r.header("X-User-Id", iduser.toString())) // Ajouter l'ID utilisateur
                    .build();

        } catch (Exception e) {
            logger.error("Error while processing the token", e);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Continuer le passage de la requête avec les en-têtes modifiés
        return chain.filter(exchange);
    }
}


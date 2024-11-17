package com.projet_iwa.api_gateway;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class); // Logger

    
    private String secretKey="f8d4c08b94562f7e7c2c6a62960cfd12cd7fc3a9060d035fb7baf1e8bc96bc90";

    // Méthode pour valider le token JWT
    public boolean validateToken(String token) {
        try {
            // Log du token reçu
            logger.info("Validating token: {}", token);
            logger.info("secretKey: {}", secretKey);


            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)  // Utilisation de la clé secrète
                    .parseClaimsJws(token)  // Décoder le token
                    .getBody();

            // Log des informations du token après décodage
            logger.info("Token decoded successfully. Subject: {}", claims.getSubject());
            logger.info("Token expiration: {}", claims.getExpiration());

            // Récupérer l'ID utilisateur, qui est stocké en tant que String
            String iduserString = claims.getSubject();
            Long iduser = Long.parseLong(iduserString);

            // Si aucune exception n'est levée, le token est valide
            logger.info("Token is valid.");
            return true;
        } catch (ExpiredJwtException e) {
            // Log pour token expiré
            logger.error("Token is expired: {}", token);
            return false;
        } catch (Exception e) {
            // Log pour autres erreurs (signature invalide, format incorrect, etc.)
            logger.error("Invalid token: {}", token, e);
            return false;
        }
    }

    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // Le subject contient l'ID utilisateur sous forme de String
    }


}


package com.projet_iwa.api_gateway;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    // Méthode pour valider le token JWT
    public boolean validateToken(String token) {
        try {
            String username = getUsernameFromToken(token);
            return (username != null);
        } catch (Exception e) {
            return false;
        }
    }

    // Extraire le nom d'utilisateur à partir du token JWT
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }


}

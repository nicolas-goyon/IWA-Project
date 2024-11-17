package com.projet_iwa.ms_user.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class JwtTokenService {

    private final String secretKey = "f8d4c08b94562f7e7c2c6a62960cfd12cd7fc3a9060d035fb7baf1e8bc96bc90";

    // Durée de validité du token (ici 1h)
    private final long expirationTime = 3600000;

    public String generateToken(Long iduser) {
        return Jwts.builder()
                .setSubject(iduser.toString()) // Remplacer username par iduser
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Expiration dans 1 heure
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}

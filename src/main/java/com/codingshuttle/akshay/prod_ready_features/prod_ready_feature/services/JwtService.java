package com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.services;

import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

@Service
public class JwtService {
//    below we are getting the secret key value from the application.properties file
    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }
    public String generateAccessToken(User user){
            return     Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("email",user.getEmail())
                .claim("roles", Set.of("ADMIN","USER"))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*20))
                .signWith(getSecretKey())
                .compact();
    }

    public String generateRefreshToken(User user){
        return     Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("email",user.getEmail())

                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60))
                .signWith(getSecretKey())
                .compact();
    }

    public Long getUserIdFromToken(String token){


        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())  // Verifies the token with the secret key
                .build()
                .parseClaimsJws(token)  // Parses the signed JWT token
                .getBody();  // Extracts the claims (payload)

        return Long.valueOf(claims.getSubject());

    }
}

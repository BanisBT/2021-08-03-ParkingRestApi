package com.tbarauskas.parkingrestapi.service;

import com.tbarauskas.parkingrestapi.entity.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.secret")
    private byte[] secret;

    @Value("${security.jwt.validity-min}")
    private long validity;

    public String createToken(User user) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setIssuer("parking-api")
                .setAudience("parking-ui")
                .setSubject(user.getUsername())
                .setExpiration(new Date(now.getTime() + validity * 60000))
                .setIssuedAt(now)
                .claim("username", user.getUsername())
                .signWith(Keys.hmacShaKeyFor(secret), SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication parseToken(String jwt) {
        return null;
    }
}

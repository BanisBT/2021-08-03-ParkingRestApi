package com.tbarauskas.parkingrestapi.service;

import com.tbarauskas.parkingrestapi.entity.user.User;
import com.tbarauskas.parkingrestapi.entity.user.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${security.jwt.secret}")
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
                .claim("roles", user.getRoles().stream().map(UserRole::getAuthority).collect(Collectors.toSet()))
//                .claim("userId", user.getId())
                .signWith(Keys.hmacShaKeyFor(secret), SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication parseToken(String jwt) {
        Claims parsedJwtBody;

        try {
            parsedJwtBody = Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            return null;

        }

        List<GrantedAuthority> roles = ((List<String>) parsedJwtBody.get("roles")).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(parsedJwtBody.getSubject(), null, roles);
    }
}

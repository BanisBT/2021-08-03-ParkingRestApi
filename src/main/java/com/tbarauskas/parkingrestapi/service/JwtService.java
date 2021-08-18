package com.tbarauskas.parkingrestapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbarauskas.parkingrestapi.dto.user.TokenUserDTO;
import com.tbarauskas.parkingrestapi.entity.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private byte[] secret;

    @Value("${security.jwt.validity-min}")
    private long validity;

    private final ObjectMapper objectMapper;

    public JwtService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String createToken(User user) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setIssuer("parking-api")
                .setAudience("parking-ui")
                .setSubject(user.getUsername())
                .setExpiration(new Date(now.getTime() + validity * 60000))
                .setIssuedAt(now)
                .claim("user", new TokenUserDTO(user))
//                .claim("roles", user.getRoles().stream().map(UserRole::getAuthority).collect(Collectors.toSet()))
//                .claim("userId", user.getId())
                .signWith(Keys.hmacShaKeyFor(secret), SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication parseToken(String jwt) throws JsonProcessingException {
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

        TokenUserDTO userDTO = objectMapper.readValue(objectMapper.writeValueAsString(parsedJwtBody.get("user")),
                TokenUserDTO.class);
        User user = new User(userDTO);

//        List<GrantedAuthority> roles = ((List<String>) parsedJwtBody.get("roles")).stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(user, null, user.getRoles());
    }
}

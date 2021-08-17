package com.tbarauskas.parkingrestapi.security;

import com.tbarauskas.parkingrestapi.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtService jwtService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")
                || request.getRequestURI().equals("/login")) {
            chain.doFilter(request, response);
            return;
        }

        String jwt = authorizationHeader.replace("Bearer ", "");
        SecurityContextHolder.getContext().setAuthentication(jwtService.parseToken(jwt));

        chain.doFilter(request, response);
    }
}

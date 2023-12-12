package com.example.backendlab.security;

import io.jsonwebtoken.JwtException;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * This filter checks if the token is valid
 */
@Order(1)
@Component
public class JwtRequestTokenVerifier extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Autowired
    public JwtRequestTokenVerifier(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // execute a "replace" method to remove a "Bearer " word
            final String jwtToken = authorizationHeader.replace("Bearer ", "");

            try {
                String userEmail = jwtProvider.extractUserEmail(jwtToken);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userEmail,
                        null,
                        null
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException e) {
                throw new IllegalStateException(String.format("Token %s cannot be trusted", jwtToken));
            }
        }
        filterChain.doFilter(request, response);
    }
}
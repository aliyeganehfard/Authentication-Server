package com.oauth.commonutils.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.oauth.commonutils.service.JWTVerificationProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.oauth.commonutils.service.JWTVerificationProvider.CLAIM_ROLES;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTVerificationProvider JWTVerificationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/auth/signIn") || request.getServletPath().equals("/auth/signUp") ||
                request.getServletPath().equals("/auth/token/refresh")) {
           filterChain.doFilter(request,response);
           return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = authHeader.substring("Bearer ".length());
        DecodedJWT decodedJWT = JWTVerificationProvider.getDecodedJWT(token);
        var username = decodedJWT.getSubject();
        var roles = decodedJWT.getClaim(CLAIM_ROLES).asArray(String.class);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Arrays.stream(roles).forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role))
        );
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}

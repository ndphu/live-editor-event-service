package com.example.eventservice.filter;

import com.example.eventservice.exception.InvalidJwtAuthenticationException;
import com.example.eventservice.security.JwtAuthentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public JwtAuthenticationFilter(String matcher, AuthenticationManager authenticationManager) {
        super(matcher, authenticationManager);
    }

    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String authorization = request.getHeader(HEADER);
        if (StringUtils.hasText(authorization)) {
            // TODO: check if user added Authorization header but value is not in format "Bearer <token>"
            String token = authorization.substring(PREFIX.length());
            Authentication jwtAuth = new JwtAuthentication(token);
            return getAuthenticationManager().authenticate(jwtAuth);
        } else {
            throw new InvalidJwtAuthenticationException("Missing authorization header");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        if (authResult.isAuthenticated()) {
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authResult);
            chain.doFilter(request, response);
        } else {
            throw new InvalidJwtAuthenticationException("Fail to authenticate request");
        }
    }
}

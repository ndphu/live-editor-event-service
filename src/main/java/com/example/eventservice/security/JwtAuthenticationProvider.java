package com.example.eventservice.security;

import com.example.eventservice.model.UserInfo;
import com.example.eventservice.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

    private final TokenService tokenService;

    @Autowired
    public JwtAuthenticationProvider(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            return authentication;
        }

        JwtAuthentication auth = (JwtAuthentication) authentication;

        String token = (String) auth.getCredentials();

        // TODO: validate token here!!!
        UserInfo userInfo = null;
        userInfo = tokenService.parseToken(token);
        auth.setUserInfo(userInfo);
        auth.setAuthenticated(true);

        return auth;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return JwtAuthentication.class.isAssignableFrom(aClass);
    }
}

package com.example.eventservice.service;

import com.example.eventservice.model.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;


@Service
public class TokenServiceImpl implements TokenService {
    static final String SECRET = "mySecretKeyDontLetYouKnowHaHaHa123456789aaaaaaa";
    static Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public TokenServiceImpl() {
    }

    @Override
    public UserInfo parseToken(String token) {
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        UserInfo info = new UserInfo();
        info.setUsername(claims.getBody().get("username", String.class));
        info.setRoles(claims.getBody().get("roles", List.class));
        info.setUserId(claims.getBody().get("userId", String.class));
        info.setDisplayName(claims.getBody().get("displayName", String.class));
        return info;
    }
}

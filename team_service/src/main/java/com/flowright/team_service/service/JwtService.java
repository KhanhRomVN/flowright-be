package com.flowright.team_service.service;

import java.security.Key;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public void validateToken(String token) {
        Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token);
    }

    public Long extractUserId(String token) {
        return extractAllClaims(token).get("user_id", Long.class);
    }

    public Long extractMemberId(String token) {
        return extractAllClaims(token).get("member_id", Long.class);
    }

    public Long extractWorkspaceId(String token) {
        return extractAllClaims(token).get("workspace_id", Long.class);
    }

    public Long extractRoleId(String token) {
        return extractAllClaims(token).get("role_id", Long.class);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

package com.flowright.member_service.service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

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

    public UUID extractUserId(String token) {
        return extractAllClaims(token).get("user_id", UUID.class);
    }

    public UUID extractMemberId(String token) {
        return extractAllClaims(token).get("member_id", UUID.class);
    }

    public UUID extractWorkspaceId(String token) {
        return extractAllClaims(token).get("workspace_id", UUID.class);
    }

    public UUID extractRoleId(String token) {
        return extractAllClaims(token).get("role_id", UUID.class);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(UUID userId, UUID memberId, UUID workspaceId, UUID roleId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", userId);
        claims.put("member_id", memberId);
        claims.put("workspace_id", workspaceId);
        if (roleId != null) {
            claims.put("role_id", roleId);
        }

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}

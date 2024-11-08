package com.flowright.member_service.service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.flowright.member_service.exception.MemberException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    @Value("${jwt.expiration}")
    private long jwtExpiration;

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
        if (extractAllClaims(token).get("user_id") == null) {
            throw new MemberException("Invalid token", HttpStatus.BAD_REQUEST);
        }
        Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token);
    }

    public UUID extractUserId(String token) {
        if (extractAllClaims(token).get("user_id") == null) {
            throw new MemberException("Invalid token", HttpStatus.BAD_REQUEST);
        }
        return UUID.fromString(extractAllClaims(token).get("user_id", String.class));
    }

    public UUID extractMemberId(String token) {
        if (extractAllClaims(token).get("member_id") == null) {
            throw new MemberException("Invalid token", HttpStatus.BAD_REQUEST);
        }
        return UUID.fromString(extractAllClaims(token).get("member_id", String.class));
    }

    public UUID extractWorkspaceId(String token) {
        if (extractAllClaims(token).get("workspace_id") == null) {
            throw new MemberException("Invalid token", HttpStatus.BAD_REQUEST);
        }
        return UUID.fromString(extractAllClaims(token).get("workspace_id", String.class));
    }

    public UUID extractRoleId(String token) {
        if (extractAllClaims(token).get("role_id") == null) {
            throw new MemberException("Invalid token", HttpStatus.BAD_REQUEST);
        }
        return UUID.fromString(extractAllClaims(token).get("role_id", String.class));
    }

    private Key getSignInKey() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(String userId, String memberId, String workspaceId, String roleId) {
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

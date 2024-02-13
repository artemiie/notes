package com.notes.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtProperties jwtProperties;
    private SecretKey key;

    @PostConstruct
    protected void init() {
        key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String generateAccessToken(String username) {
        return generate(
                TokenParameters.builder(username,
                                jwtProperties.getAccess())
                        .type(TokenType.ACCESS)
                        .build());
    }

    public String generateRefreshToken(String username) {
        return generate(
                TokenParameters.builder(username,
                                jwtProperties.getRefresh())
                        .type(TokenType.REFRESH)
                        .build());
    }

    @Override
    public String generateRestoreToken(String username) {
        return generate(
                TokenParameters.builder(username,
                                jwtProperties.getRestore())
                        .type(TokenType.RESTORE)
                        .build());
    }


    @Override
    public String generate(final TokenParameters params) {
        Claims claims = Jwts.claims().subject(params.getSubject()).add(params.getFields()).build();
        return Jwts.builder()
                .claims(claims)
                .issuedAt(params.getIssuedAt())
                .expiration(params.getExpiredAt())
                .signWith(key)
                .compact();
    }

    @Override
    public boolean isValid(final String token, final TokenType tokenType) {
        try {
            Jws<Claims> claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            TokenType type = TokenType.valueOf((String) claims.getPayload().get("type"));
            return claims.getPayload().getExpiration().after(new Date()) && tokenType.equals(type);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public HashMap<String, Object> fields(final String token) {
        Jws<Claims> claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
        return new HashMap<>(claims.getPayload());
    }

    @Override
    public String field(String token, String by) {
        Map<String, Object> fields = fields(token);
        return (String) fields.get("subject");
    }
}

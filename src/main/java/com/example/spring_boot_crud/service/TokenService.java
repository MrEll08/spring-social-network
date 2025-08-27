package com.example.spring_boot_crud.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {
    public final SecretKey key;
    public final String issuer;
    private final long accessTtlSec;

    public TokenService(@Value("${app.jwt.secret}") String secret, @Value("${app.jwt.issuer}") String issuer,
            @Value("${app.jwt.access-min}") long accessMin) {
        byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
        if (bytes.length < 32)
            throw new IllegalStateException("JWT secret must be ≥ 32 bytes");
        this.key = Keys.hmacShaKeyFor(bytes);
        this.issuer = issuer;
        this.accessTtlSec = accessMin * 60;
    }

    public String generateToken(UUID userId, String username) {
        Instant now = Instant.now();
        return Jwts.builder().issuer(issuer).subject(userId.toString()).claim("username", username)
                .issuedAt(Date.from(now)).expiration(Date.from(now.plusSeconds(accessTtlSec)))
                .signWith(key, Jwts.SIG.HS256).compact();
    }

    public Jws<Claims> parse(String jwt) throws JwtException {
        return Jwts.parser().requireIssuer(issuer).verifyWith(key).build().parseSignedClaims(jwt);
    }

    public UUID subjectUserId(String jwt) {
        return UUID.fromString(parse(jwt).getPayload().getSubject());
    }
}

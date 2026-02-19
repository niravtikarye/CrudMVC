package com.web.crudmvc.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    // TODO: move secret to secure config/env
    private static final String SECRET = "ReplaceThisWithASecureRandom32ByteOrLongerSecretKey!";
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
    private static final long EXPIRATION_MS = 1000L * 60 * 60 * 24; // 24 hours

    public static String generateToken(int userId, String email, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("email", email);
        claims.put("role", role);

        long now = System.currentTimeMillis();

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(String.valueOf(userId))
            .setIssuedAt(new Date(now))
            .setExpiration(new Date(now + EXPIRATION_MS))
            .signWith(KEY, SignatureAlgorithm.HS256)
            .compact();
    }

    public static Jws<Claims> validateToken(String token) {
        return Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token);
    }

}
